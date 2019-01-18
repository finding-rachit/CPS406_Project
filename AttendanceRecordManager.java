import java.util.*;

class AttendanceRecordManager {

    private static final int SESSION_PRICE = 10;

    private List<AttendanceRecord> attendanceList;

    public AttendanceRecordManager() {

        attendanceList = new LinkedList<AttendanceRecord>();

    }

    public void addRecord(AttendanceRecord newRecord) {

        attendanceList.add(newRecord);

    }

    public void addMemberToSession(UUID memberId, UUID sessionId, boolean paid) {

        if (!(this.memberAttendedSession(memberId, sessionId))) {

            this.attendanceList.add(new AttendanceRecord(memberId, sessionId, paid));

        }

    }

    public void removeMemberFromSession(UUID memberId, UUID sessionId) {

        ArrayList<AttendanceRecord> membersRecords = this.findAttendanceRecordsByMemberId(memberId);

        for (int i = 0; i < membersRecords.size(); i++) {
            if (membersRecords.get(i).getSessionId().equals(sessionId)) {
                this.deleteRecord(membersRecords.get(i).getId());
            }
        }

    }

    public boolean deleteRecord(UUID recordId) {

        AttendanceRecord tmpRecord = this.findRecordById(recordId);

        if (tmpRecord != null) {
            this.attendanceList.remove(tmpRecord); return true;
        }

        return false;

    }

    public int getNumberOfAttendeesForSession(UUID sessionId) {
        return this.findAttendanceRecordsBySessionId(sessionId).size();
    }

    public int getNumberOfPaidAttendeesForSession(UUID sessionId) {
        ArrayList<AttendanceRecord> attendance = this.findAttendanceRecordsBySessionId(sessionId);

        int paid = 0;

        for (int i = 0; i < attendance.size(); i++) {

            if (attendance.get(i).getPaid()) {
                paid++;
            }

        }

        return paid;

    }
/*
    public AttendanceRecord[] getAttendanceForSession(UUID sessionId) {

        ListIterator li = this.attendanceList.listIterator();

        AttendanceReco

    }
*/

    public AttendanceRecord findRecordById(UUID recordId) {

        ListIterator<AttendanceRecord> li = attendanceList.listIterator();

        AttendanceRecord tmpRecord;

        while (li.hasNext()) {

            tmpRecord = li.next();

            if (tmpRecord.getId().equals(recordId)) {
                return tmpRecord;
            }
        }

        return null;

    }

    public ArrayList<AttendanceRecord> findAttendanceRecordsBySessionId(UUID sessionId) {

        ListIterator<AttendanceRecord> li = attendanceList.listIterator();

        AttendanceRecord tmpRecord;
        ArrayList<AttendanceRecord> records = new ArrayList<AttendanceRecord>();
        //AttendanceRecord[] records = null;

        while (li.hasNext()) {

            tmpRecord = li.next();

            if (tmpRecord.getSessionId().equals(sessionId)) {
                records.add(tmpRecord);
            }
        }

        return records;

    }

    public AttendanceRecord[] getAttendanceArrayBySessionId(UUID sessionId) {
        return this.findAttendanceRecordsBySessionId(sessionId).toArray(new AttendanceRecord[0]);
    }

    public ArrayList<AttendanceRecord> findAttendanceRecordsByMemberId(UUID memberId) {

        ListIterator<AttendanceRecord> li = attendanceList.listIterator();

        AttendanceRecord tmpRecord;
        ArrayList<AttendanceRecord> records = new ArrayList<AttendanceRecord>();
        //AttendanceRecord[] records = null;

        while (li.hasNext()) {

            tmpRecord = li.next();

            if (tmpRecord.getMemberId().equals(memberId)) {
                records.add(tmpRecord);
            }
        }

        return records;

    }

    public AttendanceRecord[] getAllAttendanceRecordsArray() {
        return this.attendanceList.toArray(new AttendanceRecord[0]);
    }

    public boolean memberAttendedSession(UUID memberId, UUID sessionId) {

        ArrayList<AttendanceRecord> membersRecords = this.findAttendanceRecordsByMemberId(memberId);

        for (int i = 0; i < membersRecords.size(); i++) {
            if (membersRecords.get(i).getSessionId().equals(sessionId)) {
                return true;
            }
        }

        return false;

    }

    public int getTotalIncomeFromAllSessions() {

        AttendanceRecord[] records = this.getAllAttendanceRecordsArray();
        int total = 0;

        for (int i = 0; i < records.length; i++) {

            if (records[i].paid) {
                total += SESSION_PRICE;
            }

        }

        return total;
    }

    public int numberOfRecords() {

        return attendanceList.size();

    }

}
