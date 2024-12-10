import java.util.List;

public class Admin extends User{

    Admin(String username, String password) {
        super(username, password, "admin");
    }


    public  List<String[]> checkAllUsers(){
        return UserManipulations.getAllUsers();
    }


    public  void makeReport(String report,String username){
        String[] member = UserManipulations.lookup("member",username);
        if (member != null){
            member[8] = report;
            int line = UserManipulations.lineLookup("member",username);
            UserManipulations.updater("member", member,line);
        }
        else {
            System.out.println("user doesn't exist");
        }
    }


    public  void changeBill(int newBill,String username){
        String[] user = UserManipulations.lookup("member",username);
        if (user != null){
            user[5] = Integer.toString(newBill);
            int line = UserManipulations.lineLookup("member",username);
            UserManipulations.updater("member",user,line);
        }
        else {
            System.out.println("User doesn't exist");
        }
    }


    public  void assignCoach(String coach, String username){
        String[] user = UserManipulations.lookup("member",username);
        if (user != null){;
            if (UserManipulations.lookup("coach",coach) == null){
                System.out.println("coach not found");
                return;
            }
            user[6] = coach;
            int line = UserManipulations.lineLookup("member",username);
            UserManipulations.updater("member",user,line);
        }
        else {
            System.out.println("User doesn't exist");
        }
    }
    public void deleteUser(String username,String usertype){
        String[] user = UserManipulations.lookup(usertype,username);
        if (user == null){
            System.out.println("erorr deleting user");
        }
        int line = UserManipulations.lineLookup(usertype,username);
        UserManipulations.DeleteLine(usertype,line);
    }
}
