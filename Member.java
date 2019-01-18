import java.util.*;

class Member extends Person {

    private boolean active;

    public Member(String fName, String lName, String email) {
        super(fName, lName, email);
        this.active = true;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLabel() {
        return this.toString();
    }

    public String toString() {
        return this.getFullName();
    }

 }
