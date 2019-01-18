/* MembershipManager.java */
import javax.swing.JFrame;

/**
 * This program allows a user to manage a club/group
*/
public class MembershipManager
{

    public static void main(String[] args)
    {
        JFrame frame = new MembershipManagerFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Membership Manager");
        frame.setVisible(true);
    }

}
