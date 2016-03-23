/*
 * Taken from J2EE Design Patterns Applied. Modified slightly.
 */

package edu.asupoly.ser422.dao;

@SuppressWarnings("serial")
public class DataAccessException extends Exception{
  Throwable exceptionCause = null;

  public DataAccessException(String pExceptionMsg){
    super(pExceptionMsg);
  }

  public DataAccessException(String pExceptionMsg, Throwable pException){
    super(pExceptionMsg);
    this.exceptionCause=pException;
  }
}
