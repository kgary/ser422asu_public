package edu.asupoly.ser422.grocery;


/**
	Custom Exception class. To be used when a negative or 0 integer value is supplied as a string.
*/
public class NegativeValueException extends Exception { 
    public NegativeValueException(String errorMessage) {
        super(errorMessage);
    }
}