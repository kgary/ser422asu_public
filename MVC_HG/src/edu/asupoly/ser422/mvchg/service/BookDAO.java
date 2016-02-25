package edu.asupoly.ser422.mvchg.service;

import java.util.*;

import edu.asupoly.ser422.mvchg.model.Book;

public class BookDAO  {

	Map<String, Book> books = new HashMap<String, Book>();

	public BookDAO() {
		books.put("Java101", 	new Book("Java101",   "Java Programming 101", 		"12.95"));
		books.put("tomsawyer", 	new Book("tomsawyer", "Adventures of Tom Sawyer", 	"10.50"));
		books.put("catcher", 	new Book("catcher",   "Catcher in the Rye", 		"22.95"));
		books.put("odyssey",	new Book("odyssey",   "The Odyssey",				"32.00"));
		books.put("crusoe",		new Book("crusoe", 	  "Robinson Crusoe",			"11.99"));
	}
	
	public Map<String, Book> getAllBooks() {
		return books;
	}
	
	/* need both get and set to use with JSTL
	 */
	public void setAllBooks(Map<String, Book> books) {
		this.books = books;
	}
	
	public List<String> getBookIds() {
		List<String> result = new ArrayList<String>();
		result.addAll(books.keySet());
		return result;
	}
	
	public void setBookIds(List<String> bookIds) {
		throw new UnsupportedOperationException("Call setBooks instead");
	}
	
	public Book getBook(String id) {
		return books.get(id);
	}
	
	public String getBookPrice(String id) {
		return books.get(id).getPrice();
	}
	
}
