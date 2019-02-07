package edu.asupoly.ser422.dao;

import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.lang.reflect.Constructor;

/**
 * This factory returns connections based on which source is responsible
 * for a given object (table).
 */
public class ConnectionFactory {
    // This holds properties
    private static Properties dbProperties = new Properties();

    static {
	try {
	    dbProperties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("rdbm.properties"));
	    Class.forName(dbProperties.getProperty("EditorDAODriver"));
	    Class.forName(dbProperties.getProperty("TitleDAODriver"));
	} catch (Throwable t) {
	    t.printStackTrace();
	} finally {
	}
    }

  public static final DAOObject getDAO(String DAOname) throws Exception {
      String daoImplClassName = dbProperties.getProperty(DAOname); // (String)__props.get(DAOname);
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

      String objName = obj.getClass().getName();
      // Look up the actual Datasource. Note we could have (and probably
      // should have) done this via JNDI.
      String connInfo = dbProperties.getProperty(objName);
      if (connInfo == null || connInfo.length() == 0) {
          throw new DataAccessException("No connection info for " + objName);
      }
      try {
          return DriverManager.getConnection(connInfo, dbProperties.getProperty(objName+"_jdbcUser"),
					               dbProperties.getProperty(objName+"_jdbcPwd"));
      }
      catch (Throwable t) {
          throw new DataAccessException("Could not get connection for " + connInfo + ", " + dbProperties.getProperty(objName+"_jdbcUser") + ", " + dbProperties.getProperty(objName+"_jdbcPwd"));
      }
  }
}
