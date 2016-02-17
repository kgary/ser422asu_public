package edu.asupoly.ser422.t6mvc2.controller;

import edu.asupoly.ser422.t6mvc2.handler.ActionHandler;
import edu.asupoly.ser422.t6mvc2.handler.LoginHandler;
import edu.asupoly.ser422.t6mvc2.model.LoginCredentials;
import edu.asupoly.ser422.t6mvc2.model.User;
import edu.asupoly.ser422.t6mvc2.service.LoginService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Log in a user based on passed credentials (username, password).
 */
public class ControllerServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(
            "edu.asupoly.ser422.t6mvc2.controller.ControllerServlet");
    private static String errorPage = "error.jsp";
    //private static String successPage = "success.jsp";
    private static String successPage = "/success";
    private static Map<String, ActionHandler> handlers = new HashMap<String, ActionHandler>();
    private static Map<String, String> pageViews = new HashMap<String, String>();
    
    public void init(ServletConfig config) {
    		// normally I might read the action mapping from a config file
    		handlers.put("login", new LoginHandler());
    		handlers.put("list", new ActionHandler() {
    			public String handleIt(Map<String, String[]> params, HttpSession s) {
    				return "list";  // there isn't anything for the BL to do here
    			}
    		});
    		handlers.put("purchase", new ActionHandler() {
    			public String handleIt(Map<String, String[]> params, HttpSession s) {
    				return "purchase";  // there isn't anything for the BL to do here
    			}
    		});
    		handlers.put("confirm", new ActionHandler() {
    			public String handleIt(Map<String, String[]> params, HttpSession s) {
    				return "confirm";  // there isn't anything for the BL to do here
    			}
    		});

    		// same goes for the page views too
    		pageViews.put("loginsuccess", successPage);
    		pageViews.put("loginfailure", "/login.html");
    		pageViews.put("list", "/orderbooks.jsp");
    		pageViews.put("purchase", "/purchase.jsp");
    		pageViews.put("confirm", "/confirm.jsp");
    }
    
    private void doAction(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
    		HttpSession session = request.getSession();
    		String forwardPage = errorPage;
    		Map<String, String[]> params = request.getParameterMap();
    		String action = request.getParameter("action");
    		if (action != null && action.length() > 0) {
    			// Forward to web application to page indicated by action
    			ActionHandler handler = handlers.get(action);
    			if (handler != null) {
    				String result = handler.handleIt(params, session);
    				if (result != null && result.length() > 0) {
    					forwardPage = pageViews.get(result);
    				}
    				if (forwardPage == null || forwardPage.length() == 0) {
    					forwardPage = errorPage;
    				}
    			}
    		}
    		request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    /**
     * Handle forms
     *
     * @param request HTTP Request object
     * @param response HTTP Response object
     *
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    										throws ServletException, IOException {
    	// A more intelligent framework would do something more intelligent!
    	doAction(request, response);
    }

    /**
     *
     * @param request HTTP Request object
     * @param response HTTP Response object
     *
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	// A more intelligent framework would do something more intelligent!
    	doAction(request, response);
    }
}
