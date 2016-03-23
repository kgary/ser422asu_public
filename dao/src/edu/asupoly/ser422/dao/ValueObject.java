/*
 * This example taken from J2EE Design Patterns Applied and modified to include
 * "weak-typing" of returned VOs.
 */

package edu.asupoly.ser422.dao;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/*
 * The ValueObject is the abstract class that all ValueObjects in our
 * data persistence framework descend off of.  It provides a number of methods,
 * including get/set methods for the VO status flags and the rowVersion method.
 *
 */
@SuppressWarnings("serial")
public abstract class ValueObject implements Serializable{
    // A hack for generating ids
    private static long nextId = System.currentTimeMillis();

  /*Below are the VO status flags*/
  private boolean insertFlag = false;
  private boolean updateFlag = false;
  private boolean deleteFlag = false;

  // Use a Hashtable as it is synchronized
  private Hashtable<String, Object> _attributes = new Hashtable<String, Object>();

  protected Long getNextId() {
      return new Long(ValueObject.nextId++);
  }

  public String toString() {
      StringBuffer sb = new StringBuffer("Value Object: " + hashCode());

      if (insertFlag) sb.append("\n\tInsert flag is on");
      else sb.append("\n\tInsert flag is off");
      if (insertFlag) sb.append("\n\tUpdate flag is on");
      else sb.append("\n\tUpdate flag is off");
      if (insertFlag) sb.append("\n\tDelete flag is on");
      else sb.append("\n\tDelete flag is off");

      // get Object state as the set of attributes
      Set<String> s = _attributes.keySet();
      Object value = null;
      Object key = null;
      if (s != null) {
          for (Iterator<String> iter = s.iterator(); iter.hasNext();) {
              key = iter.next();
              value = _attributes.get(key);
              sb.append("\n\t" + key + " ==> " + value);
          }
      }
      return sb.toString();
  }

  /*
   * Return any attribute this VO has as an Object
   */
  public Object getAttribute(String aName) {
      return _attributes.get(aName);
  }

  /*
   * Set any attribute. We do not set any insert/update flags as we assume
   * this is the responsibility of the caller or a VO that extends this one.
   * Returns the previous object, if any, mapped under this name.
   */
  protected Object setAttribute(String aName, Object obj) {
      return _attributes.put(aName, obj);
  }

  /*
   * Retrieves the insertFlag.  The insertFlag is used to tell the DAO that data in the
   * the ValueObject should be inserted into the database.
   *
   */
  public boolean getInsertFlag(){
    return insertFlag;
  }

  /*
   * Retrieves the deleteFlag.  The deleteFlag is used to tell the DAO that the data
   * in the ValueObject should be deleted from the database.
   *
   */
  public boolean getDeleteFlag(){
    return deleteFlag;
  }

  /*
   * Retrieves the updateFlag.  The updateFlag is to tell the DAO that the data in the
   * ValueObject should be deleted from the database.
   *
   */
  public boolean getUpdateFlag(){
    return updateFlag;
  }

  /*Sets the insertFlag and sets all other status flags to false*/
  public void setInsertFlag(boolean pFlag){
    insertFlag = pFlag;
    deleteFlag = false;
    updateFlag = false;
  }

  /*Sets the updateFlag and sets all other status flags to false*/
  public void setUpdateFlag(boolean pFlag){
    insertFlag = false;
    deleteFlag = false;
    updateFlag = pFlag;

  }

  /*Sets the deleteFlag and sets all other status flags to false*/
  public void setDeleteFlag(boolean pFlag){
    insertFlag = false;
    deleteFlag = pFlag;
    updateFlag = false;
  }

  /*Resets all VO status flags to false*/
  public void resetFlags(){
    insertFlag = false;
    deleteFlag = false;
    updateFlag = false;
  }

}
