package edu.asupoly.ser422.dao;

import java.util.Hashtable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.lang.reflect.Constructor;

/**
 * This factory returns connections based on which source is responsible
 * for a given object (table).
 */
public class ConnectionFactory {
    // This holds Datasource URLs
  private static Hashtable<String, String> __sources = new Hashtable<String, String>();

  // This really belongs in its own Factory class - lazy!
  private static Hashtable<String, String> __impls = new Hashtable<String, String>();

  static {
      try {
          Class.forName("org.postgresql.Driver");
      } catch (Throwable t) {
          t.printStackTrace();
      }
      // read in the properties file with the mappings
      __sources.put("edu.asupoly.ser422.dao.daos.EditorDAOBean", "jdbc:postgresql://localhost/dao");
      __sources.put("edu.asupoly.ser422.dao.daos.TitleDAOBean", "jdbc:postgresql://localhost/dao");

      // Should be in a property file too
      __impls.put("edu.asupoly.ser422.dao.daos.EditorDAO", "edu.asupoly.ser422.dao.daos.EditorDAOBean");
      __impls.put("edu.asupoly.ser422.dao.daos.TitleDAO", "edu.asupoly.ser422.dao.daos.TitleDAOBean");
  }

  public static final DAOObject getDAO(String DAOname) throws Exception {
      String daoImplClassName = (String)__impls.get(DAOname);
      if (daoImplClassName == null) {
          throw new Exception("No DAO implementation registered for " + DAOname);
      }
      Class<?> daoImpl = null;
      try {
          // do a little reflection to get the no-args Constructor
          daoImpl = Class.forName(daoImplClassName);
          Constructor<?> daoImplConstructor = daoImpl.getDeclaredConstructor();
          if (daoImplConstructor == null) {
              throw new Exception("No DAO implementation default constructor for " + DAOname);
          }
          return (DAOObject)daoImplConstructor.newInstance(new Object[]{});
      }
      catch (Throwable t) {
          t.printStackTrace();
          throw new Exception("Exception trying to create DAO", t);
      }
  }

  // We can and should replace with a Connection pool
  public static final Connection getConnection(Object obj)
  	throws DataAccessException {

      // Look up the actual Datasource. Note we could have (and probably
      // should have) done this via JNDI.
      String connInfo = (String)__sources.get(obj.getClass().getName());
      if (connInfo == null || connInfo.length() == 0) {
          throw new DataAccessException("No connection info for " + obj.getClass().getName());
      }
      try {
          return DriverManager.getConnection(connInfo, "kgary", "blah");
      }
      catch (Throwable t) {
          throw new DataAccessException("Could not get connection for " + connInfo);
      }
  }
}
