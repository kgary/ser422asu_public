package edu.asupoly.ser422.restexample.services.impl;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.model.Subject;
import edu.asupoly.ser422.restexample.services.BooktownService;

import java.util.Properties;

//A simple impl of interface BooktownService
public class RDBMBooktownServiceImpl implements BooktownService {
	private static Properties __dbProperties;
	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;

	private Connection getConnection() throws Exception {
		try {
			Class.forName(__jdbcDriver);
			return DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
		} catch (Exception exc) {
			throw exc;
		}
	}
	
	// Only instantiated by factory within package scope
	public RDBMBooktownServiceImpl() {
	}

	public List<Author> getAuthors() {
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		List<Author> rval = new ArrayList<Author>();
		try {
			conn = getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(__dbProperties.getProperty("sql.getAuthors"));
			while (rs.next()) {
				rval.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	public int createAuthor(String lname, String fname) {
		if (lname == null || fname == null || lname.length() == 0 || fname.length() == 0) {
			return -1;
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createAuthor"));
			stmt.setString(1, lname);
			stmt.setString(2, fname);
			return stmt.executeUpdate();
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return -1;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	public boolean deleteAuthor(int authorId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.deleteAuthor"));
			stmt.setInt(1, authorId);
			stmt.executeUpdate();
			return true;
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	@Override
	public Author getAuthor(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Author author = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getAuthor"));
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				author = new Author(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return author;
	}

	@Override
	public boolean updateAuthor(Author author) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateAuthor"));
			stmt.setString(1, author.getLastName());
			stmt.setString(2, author.getFirstName());
			stmt.setInt(3, author.getAuthorId());
			return (stmt.executeUpdate() > 0);
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	@Override
	public List<Book> getBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getBook(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createBook(String title, int aid, int sid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Author findAuthorOfBook(int bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject getSubject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> findBooksBySubject(int subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

	// This class is going to look for a file named rdbm.properties in the classpath
	// to get its initial settings
	static {
		try {
			__dbProperties = new Properties();
			__dbProperties.load(RDBMBooktownServiceImpl.class.getClassLoader().getResourceAsStream("rdbm.properties"));
			__jdbcUrl    = __dbProperties.getProperty("jdbcUrl");
			__jdbcUser   = __dbProperties.getProperty("jdbcUser");
			__jdbcPasswd = __dbProperties.getProperty("jdbcPasswd");
			__jdbcDriver = __dbProperties.getProperty("jdbcDriver");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}	
}	
