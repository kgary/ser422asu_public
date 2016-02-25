package edu.asupoly.ser422.mvchg.service;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Map;

import edu.asupoly.ser422.mvchg.model.Book;

public class UserDAO {
	// Vectors and Hashtables are synchronized.
	private Map<String, Vector<Book>> users = null;
	private static UserDAO me = null;
	
	public static UserDAO getUserDAO() {
		if (me == null) {
			me = new UserDAO();
		}
		return me;
	}
	
	public boolean addPurchase(String username, Book book) {
		boolean rval = false;
		Vector<Book> bList = (Vector<Book>)users.get(username);
		
		if (bList == null) {
			bList = new Vector<Book>();
			users.put(username, bList);
		}
		if (!bList.contains(book)) {
			bList.add(book);
			rval = true;
		}
		return rval;
	}
	
	public boolean sell(String username, Book book) {
		boolean rval = false;
		Vector<Book> bList = (Vector<Book>)users.get(username);
		
		if (bList != null && bList.contains(book)) {
			bList.remove(book);
			rval = true;
		}
		return rval;
	}
	
	private UserDAO() {
		users = new Hashtable<String, Vector<Book>>();
	}
}
