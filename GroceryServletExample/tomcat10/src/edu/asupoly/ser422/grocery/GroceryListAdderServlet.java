package edu.asupoly.ser422.grocery;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;
import java.lang.NullPointerException;


/** 
	Servlet for handling CRUD operations on the "/grocery" resource.
*/
@SuppressWarnings("serial")
public class GroceryListAdderServlet extends HttpServlet {
	private static String _filename = null;
	private static Logger log = Logger.getLogger(GroceryListAdderServlet.class.getName());

	/**
		Method to initialize servelet behaviour, environment and config.
		@param config. First parameter. Servlet config.
		@return void.
	*/
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		_filename = config.getInitParameter(Constants.INIT_FILENAME_PROP);
		//System.out.println(_filename);
		if (_filename == null || _filename.length() == 0) {
			throw new ServletException();
		}

	}

	/**
		This method handles the 'GET' HTTP requests to the '/grocery' URL.
		Method displays the 
		@param request. First parameter, represents the HTTP request to get the resource.
		@param response. Seond parameter, represents the server's response.
		@return void.
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException	{
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "GET not supported by this servlet");
	}

	/**
		This method handles the 'POST' HTTP requests to the '/grocery' URL.
		
		@param request. First parameter, represents the HTTP request to get the resource.
		@param response. Seond parameter, represents the server's response.
		@return void.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {

		response.setContentType(ServletHelper.getContentType(request));
		PrintWriter out= response.getWriter();
		writeHTMLBeg(out);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(_filename);
		Pair<Pair<Boolean, String>, GroceryList> loadBundle = ServletHelper.loadBootstrapFile(is);
		Pair<Boolean, String> loadStatus = loadBundle.getKey();
		GroceryList groceryListObj = loadBundle.getValue();
		Boolean hasErrored = loadStatus.getKey();

		// process form data
		if(!hasErrored){
			Enumeration parameters = request.getParameterNames();
			Boolean failed = false;
			String failureReason = "Unable to find failure reason";
			String outFileName = getServletContext().getRealPath("/WEB-INF/classes/" + _filename);
			log.info("OutFile: " + outFileName);
			Map<String, String> blueprint = new Hashtable<String, String>();
			while(parameters.hasMoreElements()){
				String itemName = (String)parameters.nextElement();
				String itemValue = request.getParameter(itemName);
				log.info("blueprint: " + "ItemName: " + itemName + " ItemValue: "+ itemValue);
				blueprint.put(itemName, itemValue);
			}

			GroceryItem groceryItem = null;
			try {
				groceryItem = GroceryItem.getGroceryItemObjFromBlueprint(blueprint);				
			}catch (NegativeValueException | UnknownKeyException | NumberFormatException ex) {
				failed = true;
				failureReason = ex.getMessage();
			}

			if(failed){
				out.println("Failed to add becasue: " + failureReason + "!</br>");
			} else {
				groceryListObj.addToGroceryList(groceryItem.getPname(), groceryItem, outFileName);
				out.println("Successfully added: " + groceryItem.getPname() + "!</br>");
			}
			int currentItemCount = groceryListObj.getTotalItems();
			out.println("Total items in grocery list: " + currentItemCount);	
		} else {
			renderErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, out, loadStatus.getValue());
		}
		
		String refererURL = ServletHelper.getResourcePath(Constants.LANDING_PAGE, getServletContext());
		insertHyperLinkToLanding(out, refererURL);
		writeHTMLEnd(out);
	}

	/**
		Writes out to the end of the HTML response to a request.
		@param out. First parameter. A PrintWriter object to write to the response page.
		@return void.
	*/
	public void writeHTMLBeg(PrintWriter out){
		out.println("<HTML><HEAD><TITLE>Grocery List</TITLE></HEAD><BODY>");
	}

	/**
		Writes out to the start of the HTML response to a request.
		@param out. First parameter. A PrintWriter object to write to the response page.
		@return void.
	*/
	public void writeHTMLEnd(PrintWriter out){
		out.println("</BODY></HTML>");
	}

	/**
		Generate the HTML required to insert a hyperlink in the response.
		@param out. First parameter. A PrintWriter object to write to the response page.
		@param url. Second parameter. URL to be added to the response 
		@return void.
	*/
	public void insertHyperLinkToLanding(PrintWriter out, String url){
		if(url != null && !url.isEmpty()){
			out.println("<a href=\""+url+"\">Add More</a></br>");
		}else{
			out.println("<p>Something went wrong! Referer URL not found</p></br>");
		}
	}

	/**
		Render HTML error response.
		@param respone. First parameter. The HTTP response object.
		@param status. Second parameter. The HTTP status code.
		@param out. Third parameter. The PrintWriter object to write to the response.
		@param message. Fourth parameter. The message string that needs to be rendered/written to the response object.
	*/
	public void renderErrorResponse(HttpServletResponse response, int statusCode, PrintWriter out, String message){
		response.setStatus(statusCode);
		out.println("<p>The server encountered the following error.</p>");
		out.println("<p>" + message + "</p>");
	}

}
