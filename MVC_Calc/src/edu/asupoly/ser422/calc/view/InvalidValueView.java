package edu.asupoly.ser422.calc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class InvalidValueView extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html");

			out = response.getWriter();
			out.write("<html>\r\n");
			out.write("<head>\r\n");
			out.write("<title>Calc App</title>\r\n");
			out.write("</head>\r\n");	      
			out.write("<body>\r\n");
			out.println("Error processing input: " + request.getAttribute("value") + "\n</BODY></HTML>");
		} finally {
				out.close();
		}
	}
}
