import java.util.List;

public class Admin extends User{
    Admin(String username, String password, String userType) {
        super(username, password, userType);
    }
    public static List<String[]> checkAllUsers(){
        return UserManipulations.getAllUsers();
    }
    public static void makeReport(String report,String username){
        String[] user = UserManipulations.lookup("member",username);
        user[8] = report;
        int line = UserManipulations.lineLookup("member",username);
        UserManipulations.updater("member",user,line);
    }
    public static void changeBill(int newBill,String username){
        String[] user = UserManipulations.lookup("member",username);
        user[5] = Integer.toString(newBill);
        int line = UserManipulations.lineLookup("member",username);
        UserManipulations.updater("member",user,line);
    }
    public static void assignCoach(String coach, String username){
        String[] user = UserManipulations.lookup("member",username);
        user[6] = coach;
        int line = UserManipulations.lineLookup("member",username);
        UserManipulations.updater("member",user,line);
    }
}
