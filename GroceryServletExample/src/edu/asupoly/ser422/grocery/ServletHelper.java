package edu.asupoly.ser422.grocery;

import java.lang.NullPointerException;
import java.net.MalformedURLException;
import org.json.JSONException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ServletHelper{

	/**
		Get the URL for the 'resource' paramter using the Servlet Context.
		@param resource. First parameter. String that represents the resource.
		@param servletContext. Second parameter. ServletContext object.
		@return String. Return the URL for the resource.
	*/
	public static String getResourcePath(String resource, ServletContext servletContext) {
		String refererURL = null;
		try {
			String contextPath = servletContext.getContextPath();
			String path = servletContext.getResource(resource).getPath();
			String[] pathList = path.split("/");
			String host = pathList[0];
			String[] resourceList = path.split(contextPath);
			String resourceName = resourceList[resourceList.length - 1];
			refererURL = host + contextPath + resourceName;
		} catch (MalformedURLException ex) {
			refererURL = null;
		}
		return refererURL;
	}

	/**
		Set content type of the respone based on the requested type.
		@param request. First parameter. The HTTP request.
		@return String. String representation of the content type.
	*/
	public static String getContentType(HttpServletRequest request){
		final String headerString = "content-type";
		Boolean hasRequired = false;
		Enumeration e = request.getHeaderNames();
			while (e.hasMoreElements()) {
			String name = (String)e.nextElement();
			//log.info("Header: " + name);
			if(name.toLowerCase().equals(headerString)){
				hasRequired = true;
				break;
			}
		}
		// default content
		String requestedHeader = Constants.CONTENT_HTML;
		String responseType = Constants.CONTENT_HTML;
		if(hasRequired){
			requestedHeader = request.getHeader(headerString).toLowerCase();
			//log.info("Requested Header: " + requestedHeader);
			if(requestedHeader.startsWith(Constants.CONTENT_TEXT))  // text/html;charset=ISO-8859-1
				responseType = Constants.CONTENT_TEXT;
			else if(requestedHeader.startsWith(Constants.CONTENT_JSON))
				responseType = Constants.CONTENT_JSON;
		}
		return responseType;
	}

		/**
		Load the grocery list from the JSON file.
		@param is. First Parameter. The input file stream to load the data from.
		@return Pair<Pair<Boolean, String>, GroceryList>. Status of the load operation and error messgae string, if any along
		witht the newly created GroceryList object.
	*/
	public static Pair<Pair<Boolean, String>, GroceryList> loadBootstrapFile(InputStream is){
		String errorMessage = "";
		Boolean hasErrored = false;
		GroceryList groceryListObj = new GroceryList();
		try {
	    	groceryListObj.loadFromFile(is);
		} catch (JSONException | IOException | NegativeValueException | UnknownKeyException | NumberFormatException | NullPointerException ex) {
			errorMessage = "The server encountered the following error.\n" + ex.getMessage();
			hasErrored = true;
		}
		Pair<Boolean, String> loadStatus = new Pair<Boolean, String> (hasErrored, errorMessage);
		return new Pair<Pair<Boolean, String>, GroceryList> (loadStatus, groceryListObj);
	}
}
