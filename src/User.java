import java.util.Arrays;

public class User {
    private String username;
    private String password;
    private String userType;

    // static bool for all users that allows only one user to login at a time (I have absolutely no idea if this will work)
    private static boolean someoneIsLogged = false;

    // local bool for user to check if he's logged in
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

        // initializes data in the program
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    // setters and getters for username and password
    public void setUsername(String username)
    {
        String[] userIsRegistered = UserManipulations.lookup(userType, this.username);
        if (userIsRegistered == null) {
            this.username = username;
            return;
        }
        updateUsername(username);
    }

    public String getUsername()
    {
        return username;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setPassword(String password)
    {
        String[] userIsRegistered = UserManipulations.lookup(userType, username);
        if (userIsRegistered == null) {
            this.password = password;
            return;
        }
        updatePassword(password);
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

    
    public void updateUsername(String username)
    {
        // checks if username is taken
        if (!UserManipulations.isUnique(this.userType, username)){
            System.out.println("username already taken");
            return;
        }

        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.userType, this.username);
        int line = UserManipulations.lineLookup(this.userType, this.username);
        userdata[1] = username;
        UserManipulations.updater(this.userType, userdata, line);
        // updates user data in program
        this.username = username;
    }

    public void updatePassword(String password)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.userType, username);
        int line = UserManipulations.lineLookup(this.userType, username);
        userdata[2] = password;
        UserManipulations.updater(this.userType, userdata, line);
        // updates user data in program
        this.password = password;
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
            // this part will be overwritten in children
            String[] data = {username, password};
            UserManipulations.AddUser(userType, data);
        } 
        catch (Exception e) {
            System.err.println("something went wrong");
        }
    }

    public void login()
    {
        // checks if user exists
        String[] userIsRegistered = UserManipulations.lookup(userType, this.username);
        if (userIsRegistered == null) {
            System.out.println("user is not registered");
            return;
        }

        // checks if theres a logged in account in all users
        if (someoneIsLogged) {
            System.out.println("logout from all accounts to login");
            return;
        }

        // logs user in and flags  all users that there's a logged in user
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

