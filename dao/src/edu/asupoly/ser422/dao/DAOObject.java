package edu.asupoly.ser422.dao;

public interface DAOObject {
  public ValueObject findByPrimaryKey(String pPrimaryKey) throws
                                                     DataAccessException,
                                                     NoDataFoundException;
  public void insert(ValueObject pValueObject) throws DataAccessException;
  public void update(ValueObject pValueObject) throws DataAccessException;
  public void delete(ValueObject pValueObject) throws DataAccessException;
  public ValueObject createValueObject() throws DataAccessException;
}
