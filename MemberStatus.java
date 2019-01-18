import java.util.*;

class MemberStatus {

    public UUID id;
    public String firstName;
    public String lastName;
    public String emailAddr;
    public boolean active;
    public int attended;
    public int paid;

    public MemberStatus(UUID id, String firstName, String lastName, String emailAddr, boolean active, int attended, int paid) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddr = emailAddr;
        this.active = active;
        this.attended = attended;
        this.paid = paid;

    }

}
