/*
 * Taken from J2EE Design Patterns Applied. Modified slightly.
 */
package edu.asupoly.ser422.dao.valueobject;

import edu.asupoly.ser422.dao.ValueObject;

public class EditorVO extends ValueObject {
  private static final String EDITORID="editorid";
  private static final String FIRSTNAME="firstname";
  private static final String LASTNAME="lastname";
  private static final String MIDDLENAME="middlename";

  public EditorVO(){
      super.setAttribute(EDITORID, super.getNextId());
  }
  // Used by DAOs when retrieving from DB.
  public EditorVO(long id) {
      super.setAttribute(EDITORID, new Long(id));
  }

  public long getEditorId(){
    return ((Long)super.getAttribute(EDITORID)).longValue();
  }

  public String getFirstName(){
    return (String)super.getAttribute(FIRSTNAME);
  }

  public String getMiddleName(){
    return (String)super.getAttribute(MIDDLENAME);
  }

  public String getLastName(){
    return (String)super.getAttribute(LASTNAME);
  }

  public void setFirstName(String pFirstName){
    setUpdateFlag(true);
    super.setAttribute(FIRSTNAME, pFirstName);
  }

  public void setMiddleName(String pMiddleName){
    setUpdateFlag(true);
    super.setAttribute(MIDDLENAME, pMiddleName);
  }

  public void setLastName(String pLastName){
    setUpdateFlag(true);
    super.setAttribute(LASTNAME, pLastName);
  }
}
