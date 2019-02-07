package edu.asupoly.ser422;

public class PhoneEntry {
    private String firstname;
    private String lastname;
    private String phone;

    public PhoneEntry(String name, String lname, String phone)
    {
	this.firstname  = name;
	this.lastname  = lname;
	this.phone = phone;
    }

    public void changeName(String newfname, String newlname) {
    	firstname = newfname;
    	// This is here to introduce artifical latency for testing purposes
    	try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	lastname  = newlname;
    }
    public String toString()
    { return firstname + "\n" + lastname + "\n" + phone; }
}



