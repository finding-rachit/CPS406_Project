import java.util.*;

class AttendanceRecord {

    UUID id;
    UUID memberId;
    UUID sessionId;
    boolean paid;

    public AttendanceRecord(UUID memberId, UUID sessionId, boolean paid) {

        this.id = UUID.randomUUID();
        this.memberId = memberId;
        this.sessionId = sessionId;
        this.paid = paid;

    }

    public UUID getId() {
        return this.id;
    }

    public UUID getMemberId() {
        return this.memberId;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}
