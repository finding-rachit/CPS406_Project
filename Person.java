import java.util.*;

class Person {

    UUID id;
    String firstName;
    String lastName;
    String emailAddr;

    public Person(String fName, String lName, String email) {

        this.id = UUID.randomUUID();
        this.firstName = fName;
        this.lastName = lName;
        this.emailAddr = email;

    }

    public UUID getId() {

        return this.id;

    }

    public String getFirstName() {

        return this.firstName;

    }

    public String getLastName() {

        return this.lastName;

    }

    public String getEmailAddr() {

        return this.emailAddr;

    }

    public void setFirstName(String newFirstName) {

        this.firstName = newFirstName;

    }

    public void setLastName(String newLastName) {

        this.firstName = newLastName;

    }

    public void setEmailAddr(String newEmailAddr) {

        this.firstName = newEmailAddr;

    }

    public String getFullName() {

        return this.firstName + " " + this.lastName;

    }

    public String getLabel() {
        return this.toString();
    }

    public String toString() {
        return this.getFullName();
    }

}
