package edu.asupoly.cst425;

public class UserData {
    String username;
    String email;
    int age;
    int foo;

    public void setUsername( String value ) {
        username = value;
    }

    public void setEmail( String value ) {
        email = value;
    }

    public void setAge( int value ) {
        age = value;
    }

    public void setColor( int value ) {
        foo = value;
    }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public int getAge() { return age; }

    public int getColor() { return foo; }

    public String toString() {
        return "{"+ username +":"+ email +":"+ age +":"+foo +"}";
    }
}
