/*
 * Taken from J2EE Design Patterns Applied. Modified slightly.
 */
package edu.asupoly.ser422.dao.valueobject;

import java.util.HashMap;
import java.util.Map;

import edu.asupoly.ser422.dao.ValueObject;

/*
 * The TitleVO class represents data retrieved from the ValueObject table.
 * It is a ValueObject that extends the ValueObject class.
 *
 */
@SuppressWarnings("serial")
public class TitleVO extends ValueObject {
  private static final String TITLEID="titleid";
  private static final String DESCR="titledescr";
  private static final String COST="titlecost";
  private static final String EDITORS="editors";

  /*Constructor:  Initializes the properties of the class to be empty*/
  public TitleVO() {
    super.setAttribute(TITLEID, super.getNextId());
    initme();
  }

  // Used by DAOs when retrieving from DB.
  public TitleVO(long id) {
      super.setAttribute(TITLEID, new Long(id));
      initme();
  }
  private void initme() {
      super.setAttribute(DESCR, "");
      super.setAttribute(COST, new Float(0.0F));
      super.setAttribute(EDITORS, new HashMap<String,Long>());
  }

  /*Retrieves the title id*/
  public long getTitleId(){
    return ((Long)super.getAttribute(TITLEID)).longValue();
  }

  /*Retrieves the title descr*/
  public String getTitleDescr(){
    return (String)super.getAttribute(DESCR);
  }

  /*Retrieves the title cost*/
  public float getTitleCost(){
    // Assumes you are never null
    return ((Float)super.getAttribute(COST)).floatValue();
  }

  /*Returns all the editors associated with a title*/
  @SuppressWarnings("unchecked")
public Map<Long, EditorVO> getEditors(){
    return (Map<Long, EditorVO>)super.getAttribute(EDITORS);
  }

  /*Sets the title descr*/
  public void setTitleDescr(String pTitleDescr){
    setUpdateFlag(true);
    super.setAttribute(DESCR, pTitleDescr);
  }

  /*Sets the title cost*/
  public void setTitleCost(float pTitleCost){
    setUpdateFlag(true);
    super.setAttribute(COST, new Float(pTitleCost));
  }

  /*Sets a hashmap with all of the editors*/
  public void setEditors(Map<Long, EditorVO> pEditors){
    setUpdateFlag(true);
    super.setAttribute(EDITORS, pEditors);
  }
}
