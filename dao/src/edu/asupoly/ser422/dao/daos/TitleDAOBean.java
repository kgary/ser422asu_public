package edu.asupoly.ser422.dao.daos;

import edu.asupoly.ser422.dao.*;
import edu.asupoly.ser422.dao.valueobject.EditorVO;
import edu.asupoly.ser422.dao.valueobject.TitleVO;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


public class TitleDAOBean implements TitleDAO {
    /*
     * There are plenty of abstract Factory patterns out there. Here we just
     * have a public constructor to adhere to a contract that allows the
     * Factory to use reflection to create the actual DAOimpl. Typically
     * you would do something more sophisticated, perhaps pooling here.
     */
    public TitleDAOBean() {}

    /*
     * The findByPrimaryKey() method will retrieve a single title record from the
     * subscription database.  It returns a TitleVO that contains all
     * of the title data.
     *
     */
    public ValueObject findByPrimaryKey(String pPrimaryKey)
    throws DataAccessException, NoDataFoundException {

        Connection conn = ConnectionFactory.getConnection(this);
        ResultSet  rs   = null;

        String sql = "SELECT * FROM Title WHERE title_id=?";
        long titleId = 0L;

        // need single-arg constructor for VOs coming from DB
        TitleVO titleVO = null;

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, Long.parseLong(pPrimaryKey));
            rs = preparedStatement.executeQuery();

            /*
             * Populating a the value object with the data retrieved from the
             * resultset.
             *
             */
            if (rs.next()){
                titleId = rs.getLong("title_id");
                titleVO = new TitleVO(titleId);
                titleVO.setTitleDescr(rs.getString("title_descr"));
                titleVO.setTitleCost(rs.getFloat("title_cost"));
            }
            else{
                throw new NoDataFoundException("Record id: " + pPrimaryKey +
                " is not found in the title table.");
            }

            /*Reset the flags so that we know the object is in pristine state.*/
            titleVO.resetFlags();
            return titleVO;
        }
        catch(SQLException e) {
            /*Aborting the transaction*/
        		e.printStackTrace();
            DataAccessException dae = new DataAccessException("Error in TitleDAOBean.findByPrimaryKey()",e);
            try {
                conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
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
                "or statement in TitleDAOBean.findByPrimaryKey");
            }
        }
    }

    /*
     * The update() method will update a single record in the title table
     * with the valueobject passed in as a parameter.
     *
     */
    public void update(ValueObject pValueObject) throws DataAccessException  {
        /*Getting my SQL Code for updating all of the titles in my application.*/
        String sql = "UPDATE Title SET title_descr=?, title_cost=? WHERE title_id=?";
        TitleVO titleVO = (TitleVO) pValueObject;

        Connection conn = ConnectionFactory.getConnection(this);
        PreparedStatement preparedStatement = null;

        try{
            /*Populating the prepared statement's parameters*/
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, titleVO.getTitleDescr());
            preparedStatement.setFloat( 2, titleVO.getTitleCost());
            preparedStatement.setLong(  3, titleVO.getTitleId());

            /*
             * Checking to see if we were successful in updating the record.
             * If the queryResult does not equal 1, then we know we have run
             * into an optimistic lock situation.
             *
             */
            int queryResults = preparedStatement.executeUpdate();
            if (queryResults != 1){
                throw new DataAccessException("Stale data for title record: "
                        + titleVO.getTitleId());
            }

            /*
             * Cycling through all of the editors and seeing if an editor
             * needs to be updated.
             */
            Collection<EditorVO> col = titleVO.getEditors().values();
            Iterator<EditorVO> iterator = col.iterator();

            EditorDAO eDAO = null;
            try {
                eDAO = (EditorDAO)ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.EditorDAO");
            } catch (Exception exc) {
                exc.printStackTrace();
                // An example transaction demarcation problem - we already wrote the Title!
                System.out.println("Demarcation problem!!!");
            }
            while (iterator.hasNext()){
                EditorVO edVO = (EditorVO) iterator.next();

                /*
                 * If the updateFlag has been set to true then update the editor vo
                 * record by invoking update on the editorDAO.
                 */
                if (edVO.getUpdateFlag()){
                    // updated 4/4 by Dr. Gary
                    eDAO.update(edVO);
                }
            }
        }
        catch(SQLException e){
            /*Aborting the transaction*/
            DataAccessException exc = new DataAccessException("Error in TitleDAO.update()",e);
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
                System.out.println("Unable to close a resultset, statement, or connection TitleDAO.update().");
            }
        }
    }

    /*
     * The delete() record will delete a single record from the title
     * table in the subscription database.  It does this based on the
     * id of the value object passed into the table.
     *
     */
    @SuppressWarnings("resource")
	public void delete(ValueObject pValueObject)
    throws DataAccessException {

        /* Building my SQL Code for deleting a record from the title table.*/
        String sqlTitle = "DELETE FROM Title WHERE title_id=?";
        String sqlTitleEditor = "DELETE FROM Title_Editor WHERE title_id=?";

        TitleVO titleVO = (TitleVO) pValueObject;

        Connection conn = ConnectionFactory.getConnection(this);
        PreparedStatement preparedStatement = null;

        try {
            /*Deleting the row from the title table*/
            preparedStatement = conn.prepareStatement(sqlTitleEditor);
            preparedStatement.setLong(1,titleVO.getTitleId());
            preparedStatement.executeUpdate();

            /*Deleting all rows from the title_editor table that match the title_id*/
            preparedStatement = conn.prepareStatement(sqlTitle);
            preparedStatement.setLong(1,titleVO.getTitleId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            /*Aborting the transaction*/
            DataAccessException exc = new DataAccessException("Error in TitleDAO.update()",e);
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
                if (conn!=null)       conn.close();
            }
            catch(SQLException e){}
            System.out.println("Unable to close a resultset, statement, or connection. TitleDAO.delete()");
        }
    }

    /*
     * The insert() method will insert a single record into the title table.
     * The data it inserts will be pulled from the value object passed into
     * the method.
     *
     */
    @SuppressWarnings("resource")
	public void insert(ValueObject pValueObject)
    throws DataAccessException {

        /*Getting all of my SQL code for inserting a title*/
        String  sqlTitle        = "INSERT INTO Title VALUES (?,?,?)";
        String  sqlTitleEditor  = "INSERT INTO Title_Editor VALUES (?,?)";

        /*Building my SQL Code for inserting a record into the title table.*/
        TitleVO titleVO = (TitleVO) pValueObject;
        Connection conn = ConnectionFactory.getConnection(this);

        PreparedStatement preparedStatement = null;

        try {
            /*Populating the prepared statement with data from the value object*/
            preparedStatement = conn.prepareStatement(sqlTitle);
            preparedStatement.setLong(1, titleVO.getTitleId());
            preparedStatement.setString(2, titleVO.getTitleDescr());
            preparedStatement.setFloat(3, titleVO.getTitleCost());

            preparedStatement.execute();

            Map<Long, EditorVO>	editorHash     = titleVO.getEditors();
            Collection<EditorVO>	editorCol      = editorHash.values();
            Iterator<EditorVO>	editorIterator = editorCol.iterator();

            // Note the one-roundtrip-per-row
            while (editorIterator.hasNext()) {
                EditorVO editorVO = (EditorVO) editorIterator.next();

                preparedStatement.clearParameters();  // just for safety
                preparedStatement = conn.prepareStatement(sqlTitleEditor);
                preparedStatement.setLong(1,titleVO.getTitleId());
                preparedStatement.setLong(2,editorVO.getEditorId());
                preparedStatement.execute();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            /*Aborting the transaction*/
            DataAccessException exc = new DataAccessException("Error in TitleDAO.update()",e);
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
                System.out.println("Unable to close a resultset, statement, or connection in TitleDAO.Insert().");
            }
        }
    }

    /*
     * The createValueObject() method will create an empty value object pre-
     * populated with any mandatory data.  An example of this mandatory data
     * might be a primary key that for a particular record..
     *
     */
    public ValueObject createValueObject() throws DataAccessException {

        TitleVO titleVO = new TitleVO();

        // ID is set on creation of the VO and is immutable

        /* Populating the rest of the value object with data */
        titleVO.setTitleDescr("");
        titleVO.setTitleCost(0);
        titleVO.resetFlags();

        return titleVO;
    }
}
