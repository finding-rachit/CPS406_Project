import java.util.*;

class InstructorManager {

    private List<Instructor> instructorList;

    public InstructorManager() {

        instructorList = new LinkedList<Instructor>();


    }

    public void addInstructor(Instructor newInstructor) {

        instructorList.add(newInstructor);

    }

    public boolean deleteInstructor(UUID instructorId) {

        Instructor tmpInstructor = this.findInstructorById(instructorId);

        if (tmpInstructor != null) {
            this.instructorList.remove(tmpInstructor);
            return true;
        }

        return false;

    }

    public Instructor findInstructorById(UUID instructorId) {

        ListIterator<Instructor> li = instructorList.listIterator();

        Instructor tmpInstructor;

        while (li.hasNext()) {

            tmpInstructor = li.next();

            if (tmpInstructor.getId().equals(instructorId)) {
                return tmpInstructor;
            }
        }

        return null;

    }
    
    public UUID findInstructorbyPI(String firstName , String lastName, String emailAddr)
    {
        ListIterator<Instructor> li = instructorList.listIterator();

        Instructor tmpInstructor;

        while (li.hasNext()) {

        	tmpInstructor = li.next();

            if (tmpInstructor.getFirstName().equalsIgnoreCase(firstName) &&
            		tmpInstructor.getLastName().equalsIgnoreCase(lastName)&&
            		tmpInstructor.getEmailAddr().equals(emailAddr)) {
                return tmpInstructor.getId();

            }
        }

        return null;

    }

    public int numberOfInstructors() {

        return instructorList.size();

    }

    public Instructor[] getArray() {
        return this.instructorList.toArray(new Instructor[0]);
    }

}
