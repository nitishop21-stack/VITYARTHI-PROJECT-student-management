package model;

/**
 * Abstract class representing a Person.
 * Demonstrates Object-Oriented principles: Abstraction and Encapsulation.
 */
public abstract class Person {
    private String name;
    private String email;
    private String contactNumber;

    public Person(String name, String email, String contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Abstract method to be overridden by subclasses to display specific details.
     */
    public abstract void displayDetails();
}
