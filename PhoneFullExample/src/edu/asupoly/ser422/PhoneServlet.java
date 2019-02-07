package edu.asupoly.ser422;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class PhoneServlet extends HttpServlet {
    private static PhoneBook _pbook = null;
    private static Logger log = Logger.getLogger(PhoneServlet.class.getName());

    public void init(ServletConfig config) throws ServletException {
	// if you forget this your getServletContext() will get a NPE! 
	super.init(config);
	String filename = config.getInitParameter("phonebook");
	if (filename == null || filename.length() == 0) {
	    throw new ServletException();
	}
	// now get the phonebook file as an input stream
	log.info("Opening file " + filename);
	InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
	try {
	    _pbook = new PhoneBook(is);
	} catch (IOException exc) {
	    exc.printStackTrace();
	    throw new ServletException(exc);
	}
	System.out.println("Loaded init param phonebook with value " + filename);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException	{

	res.setContentType("text/html");
	PrintWriter out= res.getWriter();
	out.println("<HTML><HEAD><TITLE>Lab 1 Solution</TITLE></HEAD><BODY>");

	String action = req.getParameter("Action");
	if (action == null || action.length() == 0) {
	    out.println("No Action provided");
	    out.println("</BODY></HTML>"); 
	    return;
	}

	try {
	    if (action != null) {
		if (action.equals("Add")) {
		    PhoneEntry pentry = new PhoneEntry(req.getParameter("firstname"),
						       req.getParameter("lastname"), req.getParameter("phone"));

		    _pbook.addEntry(req.getParameter("phone"), pentry);
		    _pbook.savePhoneBook(getServletContext().getRealPath("/WEB-INF/classes/" + 
									 PhoneBook.DEFAULT_FILENAME));
		    out.println("Entry added to phonebook");
		} else if (action.equals("List")) {
		    String[] entries = _pbook.listEntries();
		    for (int i = 0; i < entries.length; i++)
			out.println("<b>" + i + ":</b> " + entries[i] + "<br>");
		} else if (action.equals("Remove")) {
		    PhoneEntry pentry = _pbook.removeEntry(req.getParameter("phone"));
		    if (pentry == null) {
			out.println("No entry with phone number " + req.getParameter("phone"));
		    } else {
			out.println("Removed entry " + pentry);
			_pbook.savePhoneBook(getServletContext().getRealPath("/WEB-INF/classes/" + 
									     PhoneBook.DEFAULT_FILENAME));
		    }
		}
	    } else {
		out.println("<em>No valid Action provided in the parameters</em>");
	    }
	}
	catch (Exception exc)
	    {
		out.println("<p>Java exception satisfying request</p>");
		exc.printStackTrace();
	    }
	out.println("</BODY></HTML>");
    }
	
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException	{
	res.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "GET not supported by this servlet");
    }
}
