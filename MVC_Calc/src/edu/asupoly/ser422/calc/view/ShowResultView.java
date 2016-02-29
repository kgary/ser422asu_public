package edu.asupoly.ser422.calc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowResultView extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = null;
		try {
			res.setStatus(HttpServletResponse.SC_OK);
			res.setContentType("text/html");
			out = res.getWriter();

			out.println("<HTML><HEAD><TITLE>SER422 Simple Calculator Result</TITLE></HEAD><BODY>");
			out.print("The result of the " + req.getParameter("action"));
			out.println(" operation with " + req.getAttribute("value") + " is " + req.getAttribute("result"));
			out.println("</BODY></HTML>");
		} finally {
			out.close();
		}
	}
}
