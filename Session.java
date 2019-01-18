import java.util.*;

class Session {

    UUID id;
    Calendar date;
    UUID instructorId;
    income_statement incomestatement;

    public Session(Calendar date) {
    	incomestatement = new income_statement(); 
        this.id = UUID.randomUUID();
        this.date = date;
        this.date.set(Calendar.HOUR_OF_DAY, 0);
        this.date.set(Calendar.MINUTE, 0);
        this.date.set(Calendar.SECOND, 0);
        this.instructorId = null;

    }

    public Session(Calendar sesDate, UUID instructorId) {
        this(sesDate);
        this.instructorId = instructorId;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getISId() {
    	return incomestatement.getId();
    }

    public income_statement getIS() {
    	return incomestatement;
    }

    public UUID getInstructorId() {
        return this.instructorId;
    }

    public void setInstructorId(UUID instructorId) {
        this.instructorId = instructorId;
    }

    public Calendar getDate() {

        return this.date;

    }

}
