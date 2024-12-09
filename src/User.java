import java.util.Arrays;

public class User {
    private String username;
    private String password;
    private String userType;

    // static bool for all users that allows only one user to login at a time (i have absulotly no idea if this will work)
    private static boolean someoneIsLogged = false;

    // local bool for user to check if hes logged in
    private boolean loggedIn = false;

    User(String username, String password, String userType)
    {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(userType)){
            System.out.println("invalid user type");
            return;
        }
        // checks if username is taken
        if (!UserManipulations.isUnique(userType, username)){
            System.out.println("username already taken");
            return;
        }

        // innitializes data in the program
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    // setters and getters for username and password
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean getUserIsLoggedin()
    {
        return loggedIn;
    }

    public static boolean getSomeoneIsLoggedin()
    {
        return someoneIsLogged;
    }

    
    public void updateUsername(String usertype, String username)
    {
        // checks if username is taken
        if (!UserManipulations.isUnique(usertype, username)){
            System.out.println("username already taken");
            return;
        }

        // updates user data in the db
        String[] userdata = UserManipulations.lookup(usertype, this.username);
        int line = UserManipulations.lineLookup(usertype, this.username);
        userdata[1] = username;
        UserManipulations.updater(usertype, userdata, line);
        // updates user data in program
        setUsername(username);
    }

    public void updatePassword(String usertype, String password)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(usertype, username);
        int line = UserManipulations.lineLookup(usertype, username);
        userdata[2] = password;
        UserManipulations.updater(usertype, userdata, line);
        // updates user data in program
        setPassword(password);
    }


    public void register()
    {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(this.userType)){
            System.out.println("invalid user type");
            return;
        }

        // checks if username is taken
        if (!UserManipulations.isUnique(userType, username)){
            System.out.println("username already taken");
            return;
        }

        // adds user to db
        try {
            // this part will be overritten in children
            String[] data = {username, password};
            UserManipulations.AddUser(userType, data);
        } 
        catch (Exception e) {
            System.err.println("something went wrong");
        }
    }

    public void login()
    {
        // checks if theres a logged in account in all users
        if (someoneIsLogged) {
            System.out.println("logout from all accounts to login");
            return;
        }

        // logs user in and flags a all users that theres a logged in user
        someoneIsLogged = true;
        loggedIn = true;
    }

    public void logout()
    {
        // cant log out without logging in
        if (!loggedIn) {
            System.out.println("you cant logout without logging in");
        }

        // logs out and flags all users that they can log in
        someoneIsLogged = false;
        loggedIn = false;
    }

}

