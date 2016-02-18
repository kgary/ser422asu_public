package edu.asupoly.ser422.services;

import java.util.List;

import edu.asupoly.ser422.model.Author;

// we'll build on this later
public interface BooktownService {
    public List<Author> getAuthors();
    public boolean deleteAuthor(int authorId);
    public int createAuthor(String lname, String fname);
}
