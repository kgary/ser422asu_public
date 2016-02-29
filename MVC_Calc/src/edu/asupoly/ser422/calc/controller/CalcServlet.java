/**
 * 
 */
package edu.asupoly.ser422.calc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import edu.asupoly.ser422.calc.model.BizLogic;


/**
 * @author kevinagary
 *
 */
@SuppressWarnings("serial")
public class CalcServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException	    {
		res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		try {
			out.println("<HTML><HEAD><TITLE>SER422 Simple Calculator Result</TITLE></HEAD><BODY>");
			out.println("GET not supported");
			out.println("</BODY></HTML>");
		} finally {
			out.close();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException	    {
		String view = "ShowResult";
		String action = req.getParameter("action");  // from HTML form
		if (action == null) {
			// invalid action parameter, inform the user and send them home
			// generateInvalidActionResponse(action, res);
			req.setAttribute("msg", "action cannot be null");
			view = "InvalidActionResponse";
		} else {
			String var1 = "";
			try {
				// We have Add or Subtract. In either case check the parameters
				var1 = req.getParameter("v1");
				int value = Integer.valueOf(var1);
				int result = 0;
				
				if (action.equals("Add")) {
					result = BizLogic.add(value);
					req.setAttribute("value", value);
					req.setAttribute("result", result);
				} else if (action.equals("Subtract")) {
					result = BizLogic.subtract(value);
					req.setAttribute("value", value);
					req.setAttribute("result", result);
				} else if (action.equals("Set")) {
					result = BizLogic.set(value);
					req.setAttribute("value", value);
					req.setAttribute("result", result);
				} else {
					view = "InvalidAction";
				}
			} catch (NumberFormatException nfe) {
				req.setAttribute("value", var1);
				view = "InvalidValue";
			}
		}
		req.getRequestDispatcher(view).forward(req, res);
	}
}
