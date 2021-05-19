package edu.asupoly.ser422.grocery;


/**
	Custom Exception class. To be used when an unkown key is provided in the JSON file on disk.
*/
public class UnknownKeyException extends Exception { 
    public UnknownKeyException(String errorMessage) {
        super(errorMessage);
    }
}