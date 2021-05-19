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
public class GroceryListViewerServlet extends HttpServlet {

	private static String _filename = null;
	private static Logger log = Logger.getLogger(GroceryListViewerServlet.class.getName());

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
		PrintWriter out = response.getWriter();
		JSONObject responseJSON = new JSONObject();

		// URL of the landing page for the application
		String refererURL = ServletHelper.getResourcePath(Constants.LANDING_PAGE, getServletContext());
		
		Enumeration parameters = request.getParameterNames();
		
		// load the grocery list from the JSON file
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(_filename);
		Pair<Pair<Boolean, String>, GroceryList> loadBundle = ServletHelper.loadBootstrapFile(is);
		Pair<Boolean, String> loadStatus = loadBundle.getKey();
		GroceryList groceryListObj = loadBundle.getValue();

		// if no error occured during the load
		Boolean hasErrored = loadStatus.getKey();
		if(!hasErrored){
			// default message to be displayed
			String message = "No filters applied on grocery list.";
			
			List<String> filterMessages = new ArrayList<String>();
			Map<String, GroceryItem> groceryList = groceryListObj.getGroceryList();
			
			// filter grocery list
			while(parameters.hasMoreElements()){
				String itemName = (String)parameters.nextElement();
				String itemValue = request.getParameter(itemName);
				if(!itemValue.equals("")){
					Pair <String, Map<String, GroceryItem> > pairObj = filterGroceryList(groceryList, itemName, itemValue);
					filterMessages.add(pairObj.getKey());
					groceryList = pairObj.getValue();	
				}
			}

			if(filterMessages.isEmpty()){
				filterMessages.add(message);
			} 
			for(String filterMessage: filterMessages) {
				responseJSON.append(Constants.JR_FILTER_MESSAGES, filterMessage);
			}
			responseJSON.put(Constants.JR_GROCERY_LIST, getGroceryListJSON(groceryList));
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		responseJSON.put(Constants.JR_ERROR, hasErrored);
		responseJSON.put(Constants.JR_ERROR_MSG, loadStatus.getValue());
		responseJSON.put(Constants.JR_HOME, refererURL);

		// render content based on the requested content type
		String contentType = ServletHelper.getContentType(request);
		response.setContentType(contentType);
		if(contentType.equals(Constants.CONTENT_JSON)){
			out.print(responseJSON.toString());
		} else if(contentType.equals(Constants.CONTENT_TEXT)){
			out.print(jsonToText(responseJSON));
		} else{
			out.print(jsonToHTML(responseJSON));
		}
	}

	/**
		This method handles the 'POST' HTTP requests to the '/grocery' URL.
		
		@param request. First parameter, represents the HTTP request to get the resource.
		@param response. Seond parameter, represents the server's response.
		@return void.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "POST not supported by this servlet");
	}

	/**
		Filter the grocery list based on the filters provided.
		@param groceryList. First parameter. A map comtaining grocery items mapped to their names.
		@param filterName. Second parameter. String name of the filter.
		@param filterValue. Third parameter. String value of the filter.
		@return Pair<String, Map<String, GroceryItem> > A 'Pair' containg status message for the operation and the 
		filtered grocery-list.
	*/
	public Pair<String, Map<String, GroceryItem> > filterGroceryList(Map<String, GroceryItem> groceryList, String filterName, String filterValue){
		Map<String, GroceryItem> filteredGroceryList = new Hashtable<String, GroceryItem>();
		String message = "Successfully filtered on: " + filterName + " for value: " + filterValue;
		try{
			for(Map.Entry<String, GroceryItem> entry : groceryList.entrySet()){
				GroceryItem groceryItem = entry.getValue();
				if(filterName.equals(Constants.AISLE_STR)){
					int filterValueInt = Integer.parseInt(filterValue);
					if(filterValueInt <= 0)
						throw new NegativeValueException("Aisle value cannot be negative!");
					if(groceryItem.getAisle() == filterValueInt){
						filteredGroceryList.put(entry.getKey(), groceryItem);
					}
				} else if (filterName.equals(Constants.CUSTOM_STR)){
					if(groceryItem.getDiet().equals(filterValue)){
						filteredGroceryList.put(entry.getKey(), groceryItem);
					}
				}
			}
		}catch (NumberFormatException | NegativeValueException ex){
			message = "Could not filter on: " + filterName + " for value: " + filterValue + "becasue: "+ ex.toString();
			return new Pair<String, Map<String, GroceryItem> >(message, groceryList);
		}
		return new Pair<String, Map<String, GroceryItem> >(message, filteredGroceryList);
	}

	/**
		Get a list grocery items as a JSON Object
		@param groceryList. First parameter. A map comtaining grocery items mapped to their names.
		@return JSONObject. The object containing the grocery list
	*/
	public JSONObject getGroceryListJSON(Map<String, GroceryItem> groceryList){
		JSONObject groceryValues = new JSONObject();

		JSONArray headers = new JSONArray();
		headers.put("Product Name");
		headers.put("Brand Name");
		headers.put("Aisle Number");
		headers.put("Quantity");
		headers.put("Diet Type");

		groceryValues.put(Constants.JR_GROCERY_LIST_HEADERS, headers);

		JSONArray rows = new JSONArray();
		if(groceryList.size() == 0){
			JSONArray data = new JSONArray();
			data.put("No Items!");
			rows.put(data);
		} else{
			for(Map.Entry<String, GroceryItem> entry : groceryList.entrySet()){
				GroceryItem groceryItem = entry.getValue();
				JSONArray data = new JSONArray();
				data.put(groceryItem.getPname());
				data.put(groceryItem.getBname());
				data.put(groceryItem.translateToString(groceryItem.getAisle()));
				data.put(Integer.toString(groceryItem.getQty()));
				data.put(groceryItem.getDiet());
				rows.put(data);
			}
		}
		groceryValues.put(Constants.JR_GROCERY_LIST_ROWS, rows);
		return groceryValues;
	}

	/**
	Replace the specials token in the embedded string response to get a 
	pure text respone.
	@param respone. First parameter. The JSON respone for the get request.
	@return String. The plain text string representation of the response.
	*/
	private String jsonToText(JSONObject response){
		String responseString = jsonToEmbeddedText(response);
		List<String> htmlTokens = Arrays.asList("html", "body", "head", "title", "table", "tr", "th", "td", "br", "span", "p");
		for(String token: htmlTokens){
			responseString = responseString.replaceAll("<" + token + ">", "");
			responseString = responseString.replaceAll("</" + token + ">", "");
		}
		// special case for 'a' tag
		responseString = responseString.replaceAll("<a href=\"", "");
		responseString = responseString.replaceAll("\">here</a>", "");

		// replace newlines and tabs
		responseString = responseString.replaceAll("<NEWLINE>", "\n");
		responseString = responseString.replaceAll("<TAB>", "\t");
		return responseString;
	}

	/**
	Replace the specials token in the embedded string response to get a 
	pure HTML respone.
	@param respone. First parameter. The JSON respone for the get request.
	@return String. The HTML string representation of the response.
	*/
	private String jsonToHTML(JSONObject response){
		String responseString = jsonToEmbeddedText(response);
		responseString = responseString.replaceAll("<NEWLINE>", "");
		responseString = responseString.replaceAll("<TAB>", "");
		return responseString;
	}

	/**
	Create a token embedded String representation of the JSON respone.
	This embedded representation is used by 'jsonToHTML' and 'jsonToText'
	@param respone. First parameter. The JSON respone for the get request.
	@return String. The embedded string representation of the JSON response.
	*/
	private String jsonToEmbeddedText(JSONObject response) {
		log.info("response: " + response.toString());
		StringBuilder responseString = new StringBuilder();
		responseString.append("<html><head><title>Grocery List</title></head><body><NEWLINE>");
		
		Boolean hasErrored = (Boolean) response.get(Constants.JR_ERROR);
		if(hasErrored) {
			responseString.append("<span>" + response.get(Constants.JR_ERROR_MSG) + "</span></br><NEWLINE>");
			responseString.append("</br><NEWLINE>");
		} else {
			JSONArray filterMessages = (JSONArray) response.get(Constants.JR_FILTER_MESSAGES);
			for(int i = 0; i < filterMessages.length(); i++){
				responseString.append("<span>" + filterMessages.get(i) + "</span></br><NEWLINE>");
			}
			responseString.append("</br><NEWLINE>");
			JSONObject groceryValues = (JSONObject) response.get(Constants.JR_GROCERY_LIST);
			JSONArray groceryValuesHeaders = (JSONArray) groceryValues.get(Constants.JR_GROCERY_LIST_HEADERS);
			responseString.append("<table>");
			responseString.append("<tr>");
			for(int i = 0; i < groceryValuesHeaders.length(); i++){				
				responseString.append("<th>" + groceryValuesHeaders.get(i) + "</th><TAB>");
			}
			responseString.append("</tr><NEWLINE>");
			JSONArray groceryValuesRows = (JSONArray) groceryValues.get(Constants.JR_GROCERY_LIST_ROWS);
			for(int i = 0; i < groceryValuesRows.length(); i++){
				responseString.append("<tr>");
				JSONArray groceryValuesRowValues = (JSONArray) groceryValuesRows.get(i);
				for (int j = 0; j < groceryValuesRowValues.length(); j++){
					responseString.append("<td>" + groceryValuesRowValues.get(j) + "</td><TAB>");
				}
				responseString.append("</tr><NEWLINE>");
			}	
			responseString.append("</table><NEWLINE>");
		}
		responseString.append("<span>Add More: <a href=\"" + response.get(Constants.JR_HOME) + "\">here</a></span><NEWLINE>");
		responseString.append("</body></html>");
		return responseString.toString();
	}
}
