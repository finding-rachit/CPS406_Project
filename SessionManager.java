import java.util.*;

class SessionManager {

    private List<Session> sessionList;

    public SessionManager() {

        sessionList = new LinkedList<Session>();

    }

    public void addSession(Session newSession) {

        sessionList.add(newSession);

    }

    public boolean deleteSession(UUID sessionId) {

        Session tmpSession = this.findSessionById(sessionId);

        if (tmpSession != null) {
            this.sessionList.remove(tmpSession);
            return true;
        }

        return false;

    }

    public Session findSessionById(UUID sessionId) {

        ListIterator<Session> li = sessionList.listIterator();

        Session tmpSession;

        while (li.hasNext()) {

            tmpSession = li.next();

            if (tmpSession.getId().equals(sessionId)) {
                return tmpSession;
            }
        }

        return null;

    }

    public Session findSessionByDate(Calendar date) {

        ListIterator<Session> li = sessionList.listIterator();

        Session tmpSession;
        Calendar cal;

        while(li.hasNext()) {

            tmpSession = li.next();
            cal = tmpSession.getDate();

            if (cal.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                cal.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                cal.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                    return tmpSession;
            }
        }

        return null;

    }


    public Session[] getAllSessions() {
        return sessionList.toArray(new Session[0]);
    }

    public ArrayList<Session> findAllSessionsInMonth(Calendar date) {

        ArrayList<Session> sessions = new ArrayList<Session>();

        Session[] allSessions = this.getAllSessions();

        for (int i = 0; i < allSessions.length; i++) {
            if (allSessions[i].getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                allSessions[i].getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)) {
                sessions.add(allSessions[i]);
            }
        }

        return sessions;

    }

    public HashSet<Integer> getDaysWithSessionsInMonth(Calendar date) {

        HashSet<Integer> days = new HashSet<Integer>();

        ArrayList<Session> sessionsInMonth = this.findAllSessionsInMonth(date);

        for (int i = 0; i < sessionsInMonth.size(); i++) {
            days.add(sessionsInMonth.get(i).getDate().get(Calendar.DAY_OF_MONTH));
        }

        return days;

    }

    public int numberOfSessions() {

        return sessionList.size();

    }

}
