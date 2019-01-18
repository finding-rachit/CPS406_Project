import javax.swing.table.*;
import java.util.*;

/**
 * Manag all of the Member objects
 */
class MemberManager {
	
    private List<Member> memberList;

    public MemberManager() {
        memberList = new LinkedList<Member>();
    }

    public void addMember(Member newMember) {
        memberList.add(newMember);
    }

    /*
     * Set a Member to inactive.
     *
     * Deleting the Member would require deleting their attendance and
     * payment histories.
     */
    public boolean deActivateMember(UUID memberId) {

        Member tmpMember = this.findMemberById(memberId);

        if (tmpMember != null) {
            tmpMember.setActive(false);
            return true;
        }

        return false;

    }
    
    public boolean activateMember(UUID memberId) {

        Member tmpMember = this.findMemberById(memberId);

        if (tmpMember != null) {
            tmpMember.setActive(true);
            return true;
        }

        return false;

    }
    
    public void deleteMember(UUID memberId) {

        Member tmpMember = this.findMemberById(memberId);

        if (tmpMember != null) {
        	 this.memberList.remove(tmpMember);
        }

    }

    public Member findMemberById(UUID memberId) {

        ListIterator<Member> li = memberList.listIterator();

        Member tmpMember;

        while (li.hasNext()) {

            tmpMember = li.next();

            if (tmpMember.getId().equals(memberId)) {
                return tmpMember;
            }
        }

        return null;

    }

    //method for find member by personal information
    //@return UUID.
    public UUID findMemberbyPI(String firstName , String lastName, String emailAddr)
    {
        ListIterator<Member> li = memberList.listIterator();

        Member tmpMember;

        while (li.hasNext()) {

        	tmpMember = li.next();

            if (tmpMember.getFirstName().equalsIgnoreCase(firstName) &&
            	tmpMember.getLastName().equalsIgnoreCase(lastName)&&
            	tmpMember.getEmailAddr().equals(emailAddr)) {
                return tmpMember.getId();

            }
        }

        return null;

    }
    //method for find member by personal information
    //@return boolean.
    public boolean findMember(String firstName , String lastName, String emailAddr)
    {
        ListIterator<Member> li = memberList.listIterator();

        Member tmpMember;

        while (li.hasNext()) {

        	tmpMember = li.next();

            if (tmpMember.getFirstName().equalsIgnoreCase(firstName) &&
            	tmpMember.getLastName().equalsIgnoreCase(lastName)&&
            	tmpMember.getEmailAddr().equals(emailAddr)) {
                return true;

            }
        }

        return false;

    }

    public Member[] getArray() {
        return this.memberList.toArray(new Member[0]);
    }


    public int numberOfMembers() {
        return memberList.size();
    }

    public int numberOfActiveMembers() {
        ListIterator<Member> li = memberList.listIterator();

        int c = 0;
        Member tmpMember;

        while (li.hasNext()) {
            tmpMember = li.next();

            if (tmpMember.getActive()) {
                c++;
            }

        }

        return c;

    }

}
