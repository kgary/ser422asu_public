/*
 * Taken from J2EE Design Patterns Applied. Modified slightly.
 */
package edu.asupoly.ser422.dao.daos;

import edu.asupoly.ser422.dao.*;
import edu.asupoly.ser422.dao.valueobject.EditorVO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class EditorDAOBean implements EditorDAO {

    /*
     * There are plenty of abstract Factory patterns out there. Here we just
     * have a public constructor to adhere to a contract that allows the
     * Factory to use reflection to create the actual DAOimpl. Typically
     * you would do something more sophisticated, perhaps pooling here.
     */
    public EditorDAOBean() {}

  public ValueObject findByPrimaryKey(String pPrimaryKey)
    throws DataAccessException, NoDataFoundException {

    Connection conn = ConnectionFactory.getConnection(this);
    ResultSet  rs   = null;

    String sql = "SELECT * FROM Editor WHERE editor_id=?";

    EditorVO editorVO = null;

    try{
      PreparedStatement preparedStatement = conn.prepareStatement(sql);

      preparedStatement.setLong(1, new Long(pPrimaryKey).longValue());
      rs = preparedStatement.executeQuery();

      /*
       * Populating a the value object with the data retrieved from the
       * resultset.
       *
       */
      if (rs.next()){
        editorVO = new EditorVO(rs.getLong("editor_id"));
        editorVO.setFirstName(rs.getString("first_name"));
        editorVO.setMiddleName(rs.getString("middle_name"));
        editorVO.setLastName(rs.getString("last_name"));
      }
      else{
	throw new NoDataFoundException("Record id: " + pPrimaryKey +
			              " is not found in the editor table.");
      }

      /*Reset the flags so that we know the object is in pristine state.*/
      editorVO.resetFlags();
      return editorVO;
    }
    catch(SQLException e){
      /*Aborting the transaction*/
        DataAccessException dae = new DataAccessException("Error in EditoDAO.findByPrimaryKey()",e);
        try {
          conn.rollback();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        throw dae;
    }
    finally{
      try{
        if (rs!=null)   rs.close();
	if (conn!=null) conn.close();
      }
      catch(SQLException e){
	System.out.println("Unable to close resultset, database connection " +
			   "or statement in EditorDAOBean.findByPrimaryKey");
      }
    }
  }

  public Map<Long,EditorVO> findEditorByTitle(String pTitleId)
    throws DataAccessException {

    Connection conn = ConnectionFactory.getConnection(this);
    ResultSet  rs   = null;

    String sql = "SELECT Editor.editor_id, Editor.first_name, Editor.middle_name, Editor.last_name" +
    		" FROM (Editor JOIN Title_Editor ON (Editor.editor_id=Title_Editor.editor_id))" +
    		" WHERE Title_Editor.title_id=?";

    Map<Long, EditorVO> editorList = new HashMap<Long,EditorVO>();

    try{
      PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());

      preparedStatement.setLong(1, new Long(pTitleId).longValue());
      rs = preparedStatement.executeQuery();

      while (rs.next()){
        EditorVO editorVO = new EditorVO(rs.getLong("editor_id"));
        editorVO.setFirstName(rs.getString("first_name"));
        editorVO.setMiddleName(rs.getString("middle_name"));
        editorVO.setLastName(rs.getString("last_name"));

        /*Reset the flags so that we know the object is in pristine state.*/
        editorVO.resetFlags();

	    editorList.put(new Long(editorVO.getEditorId()), editorVO);
      }
	  return editorList;
    }
    catch(SQLException e){
      /*Aborting the transaction*/
        e.printStackTrace();
        DataAccessException dae = new DataAccessException("Error in EditorDAO.findByEditorTitle()",e);
        try {
            conn.rollback();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
      throw dae;
    }
    finally{
      try{
        if (rs!=null)   rs.close();
	    if (conn!=null) conn.close();
      }
      catch(SQLException e){
	     System.out.println("Unable to close resultset, database connection " +
			   "or statement in EditorDAO.findByEditorTitle()");
      }
    }
  }

  public void update(ValueObject pValueObject) throws DataAccessException {
    String sql = "UPDATE Editor SET first_name=?,middle_name=?,last_name=? " +
    				"WHERE editor_id=?";

    EditorVO editorVO = (EditorVO) pValueObject;
    Connection conn = ConnectionFactory.getConnection(this);
    PreparedStatement preparedStatement = null;

    try{
      /*Populating the prepared statement's parameters*/
      preparedStatement = conn.prepareStatement(sql);
      preparedStatement.setString(1, editorVO.getFirstName());
      preparedStatement.setString(2, editorVO.getMiddleName());
      preparedStatement.setString(3, editorVO.getLastName());
      preparedStatement.setLong(4, editorVO.getEditorId());

      /*
       * Checking to see if we were successful in updating the record.
       * If the queryResult does not equal 1, then we no we have run
       * into an optimistic lock situation.
       *
       */
      int queryResults = preparedStatement.executeUpdate();
      if (queryResults != 1) {
	      throw new DataAccessException("Stale data for editor record: "
			                  + editorVO.getEditorId());
      }
    }
    catch(SQLException e){
      /*Aborting the transaction*/
        DataAccessException exc = new DataAccessException("Error in EditorDAO.update()",e);
        try {
            conn.rollback();
        }
        catch (SQLException e2) {
            throw new DataAccessException("Error rolling back during recovery, nested exception has original error", exc);
        }
        throw exc;
    }
    finally {
      try{
	if (preparedStatement!=null) preparedStatement.close();
	if (conn!=null) conn.close();
      }
      catch(SQLException e){
	     System.out.println("Unable to close resultset, database connection " +
			   "or statement in EditorDAO.update()");
      }
    }

  }

  public void delete(ValueObject pValueObject)
     throws DataAccessException{

    String sql = "DELETE FROM Editor WHERE editor_id=?";
    String sqlEditor = "DELETE FROM Title_Editor WHERE editor_id=?";

    EditorVO editorVO = (EditorVO) pValueObject;

    Connection conn = ConnectionFactory.getConnection(this);
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    try{
      /*Deleting the record from the editor table*/
      preparedStatement = conn.prepareStatement(sqlEditor);
      preparedStatement.setLong(1,editorVO.getEditorId());
      preparedStatement.executeUpdate();

      /*Deleting the record from the title_editor table*/
      preparedStatement2 = conn.prepareStatement(sql);
      preparedStatement2.setLong(1,editorVO.getEditorId());
      preparedStatement2.executeUpdate();
    }
    catch(SQLException e){
      /*Aborting the transaction*/
        e.printStackTrace();
      DataAccessException exc = new DataAccessException("Error in EditorDAO.delete()",e);
      try {
          conn.rollback();
      }
      catch (SQLException e2) {
          throw new DataAccessException("Error rolling back during recovery, nested exception has original error", exc);
      }
      throw exc;
    }
    finally{
      try{
          if (preparedStatement != null) preparedStatement.close();
          if (preparedStatement2 != null) preparedStatement2.close();
          if (conn!=null)       conn.close();
      }
      catch(SQLException e){
          System.out.println("Unable to close resultset, database connection " +
                             "or statement in EditorDAO.delete()");
      }
    }
  }

  public void insert(ValueObject pValueObject)
    throws DataAccessException{

    String  sql     = "INSERT INTO Editor VALUES (?,?,?,?)";

    EditorVO editorVO = (EditorVO) pValueObject;
    Connection conn = ConnectionFactory.getConnection(this);

    PreparedStatement preparedStatement = null;

    try{
      /*Populating the prepared statement with data from the value object*/
      preparedStatement = conn.prepareStatement(sql.toString());

      preparedStatement.setLong(1, editorVO.getEditorId());
      preparedStatement.setString(2, editorVO.getFirstName());
      preparedStatement.setString(3, editorVO.getMiddleName());
      preparedStatement.setString(4, editorVO.getLastName());

      preparedStatement.execute();
    }
    catch(SQLException e){
      /*Aborting the transaction*/
        e.printStackTrace();
        DataAccessException exc = new DataAccessException("Error in EditorDAO.insert()",e);
        try {
            conn.rollback();
        }
        catch (SQLException e2) {
            throw new DataAccessException("Error rolling back during recovery, nested exception has original error", exc);
        }
        throw exc;
    }
    finally{
      try{
	if (preparedStatement!=null) preparedStatement.close();
	if (conn!=null) conn.close();
      }
      catch(SQLException e){
	     System.out.println("Unable to close resultset, database connection " +
			   "or statement in EditorDAO.insert()");
      }
    }
  }

  public ValueObject createValueObject()
    throws DataAccessException{

    EditorVO editorVO = new EditorVO();

    // The ID is generated by the object itself on creation and is immutable

    /* Populating the rest of the value object with data */
    editorVO.setFirstName("");
    editorVO.setMiddleName("");
    editorVO.setLastName("");
    editorVO.resetFlags();

    return editorVO;
  }
}
