package edu.asupoly.ser422.grocery;

import java.util.*; 
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.logging.Logger;

public class GroceryItem {
	private static Logger log = Logger.getLogger(GroceryItem.class.getName());
	int aisle = 999999;  // default value
	int qty;
	String pname, diet;
	String bname = "N/A";  // default value
	final static String AISLE_KEY = "aisle";
	final static String QTY_KEY = "qty";
	final static String PNAME_KEY = "pname";
	final static String BNAME_KEY = "bname";
	final static String DIET_KEY = "diet";

	public void setAisle(int aisle){
		this.aisle = aisle;
	}
	public void setQty(int qty){
		this.qty = qty;
	}
	public void setPname(String pname){
		this.pname = pname;
	}
	public void setBname(String bname){
		this.bname = bname;
	}
	public void setDiet(String diet){
		this.diet = diet;
	}

	public int getAisle(){
		return this.aisle;
	}
	public String getPname(){
		return this.pname;
	}
	public String getBname(){
		return this.bname;
	}
	public int getQty(){
		return this.qty;
	}
	public String getDiet(){
		return this.diet;
	}

	public String translateToString(int val) {
		if(val == -1)
			return "N/A";
		return Integer.toString(val);
	}

	/**
		Blueprint of the Class object. A mapping of this classes variables and the form data on the landing (index.html) page.
		@return Map<String, Boolean>.
	*/
	public static Map<String, Boolean> getGroceryItemBlueprint(){
		Map<String, Boolean> blueprint = new HashMap<String, Boolean>();
		blueprint.put(AISLE_KEY, true);
		blueprint.put(BNAME_KEY, true);
		blueprint.put(QTY_KEY, true);
		blueprint.put(PNAME_KEY, true);
		blueprint.put(DIET_KEY, true);
		return blueprint;
	}

	/**
		Create a GroceryItem object from the given JSONObject blueprint - a key-value mapping of the Class variables and their values.
	*/
	public static GroceryItem getGroceryItemObjFromBlueprint(JSONObject blueprint) throws NegativeValueException, NumberFormatException, UnknownKeyException  {
		Map<String, Boolean> blueprintReq = getGroceryItemBlueprint();
		Map<String, String> blueprintFinal = new Hashtable<String, String>();
		for(Map.Entry<String, Boolean> entry : blueprintReq.entrySet()){
			String key = entry.getKey();
			if(blueprint.has(key)){
				Object object = blueprint.get(key);
				if (object instanceof Integer)
					blueprintFinal.put(key, Integer.toString((int) object));
				else
					blueprintFinal.put(key, (String) object);
			}
		}
		return GroceryItem.getGroceryItemObjFromBlueprint(blueprintFinal);
	}

	/**
		Create a GroceryItem object from the given blueprint - a key-value mapping of the Class variables and their values.
	*/
	public static GroceryItem getGroceryItemObjFromBlueprint(Map<String, String> blueprint) throws NegativeValueException, NumberFormatException, UnknownKeyException {
		Map<String, Boolean> blueprintReq = getGroceryItemBlueprint();
		
		// check if all required keys are present
		//log.info("Checking for required keys");
		for(Map.Entry<String, Boolean> entry : blueprintReq.entrySet()){
			Boolean required = entry.getValue();
			String key = entry.getKey();
			if(required && !blueprint.containsKey(key))
				throw new UnknownKeyException("Required Key: '" + key + "' not present in sent blueprint");
		}

		//log.info("Checking for 'unexpected' keys");
		// check if any 'non-existant' key has been sent
		for(Map.Entry<String, String> entry : blueprint.entrySet()){
			String key = entry.getKey();
			if(!blueprintReq.containsKey(key))  // invalid key sent
				throw new UnknownKeyException("Key: '" + key + "' does not exits for GroceryItem.");
		}
		
		log.info("Populating 'GroceryItem'.");
		// populate grocery item object from blueprint
		GroceryItem groceryItem = new GroceryItem();
		for(Map.Entry<String, String> entry : blueprint.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			//log.info("Key: "+ key + " Value: "+ value);
			if(key.equals(AISLE_KEY)){
				if(!(value.length() == 0) || !value.isEmpty()){
					int valueInt = 0;
					try {
						valueInt = Integer.parseInt(value);
					} catch (NumberFormatException ex){
						//log.info("NumberFormatException: " + ex.getMessage());
						throw new NumberFormatException(ex.getMessage());
					}
					if(valueInt <= 0){
						//log.info("NegativeValueException:");
						throw new NegativeValueException("Quantity/Aisle value cannot be negative or 0!");
					}
					groceryItem.setAisle(valueInt);			
				}
			}
			else if(key.equals(QTY_KEY)){
				int valueInt = 0;
				try {
					valueInt = Integer.parseInt(value);
				} catch (NumberFormatException ex){
					//log.info("NumberFormatException: " + ex.getMessage());
					throw new NumberFormatException(ex.getMessage());
				}
				if(valueInt <= 0){
					//log.info("NegativeValueException:");
					throw new NegativeValueException("Quantity/Aisle value cannot be negative or 0!");
				}
				groceryItem.setQty(valueInt);
			}
			else if(key.equals(PNAME_KEY))
				groceryItem.setPname(value);
			else if(key.equals(BNAME_KEY))
				groceryItem.setBname(value);
			else if(key.equals(DIET_KEY))
				groceryItem.setDiet(value);
		}
		//log.info("Done populating grocery items.");
		return groceryItem;
	}

	/**
		JSONObject representation of the Class object.
		@return JSONObject. JSONObject representation of the Class object.
	*/
	public JSONObject getJSONObject(){
		JSONObject jObject = new JSONObject();
		Map<String, Boolean> blueprint = GroceryItem.getGroceryItemBlueprint();
		for(String key: blueprint.keySet()) {
			if(key.equals(AISLE_KEY))
				jObject.put(AISLE_KEY, this.getAisle());
			else if(key.equals(PNAME_KEY))
				jObject.put(PNAME_KEY, this.getPname());
			else if(key.equals(BNAME_KEY))
				jObject.put(BNAME_KEY, this.getBname());
			else if(key.equals(QTY_KEY))
				jObject.put(QTY_KEY, this.getQty());
			else if(key.equals(DIET_KEY))
				jObject.put(DIET_KEY, this.getDiet());
		}
		return jObject;
	}
}	
