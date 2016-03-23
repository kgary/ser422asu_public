/*
 * Taken from J2EE Design Patterns Applied. Modified slightly.
 */

package edu.asupoly.ser422.dao.daos;

import edu.asupoly.ser422.dao.DAOObject;
import edu.asupoly.ser422.dao.DataAccessException;
import edu.asupoly.ser422.dao.valueobject.EditorVO;

import java.util.Map;

public interface EditorDAO extends DAOObject{
  public Map<Long,EditorVO> findEditorByTitle(String pTitleId) throws
                                                     DataAccessException	;
}
