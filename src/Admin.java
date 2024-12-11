import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends User{

    private String notifications = "null";

    Admin(String username, String password) {
        super(username, password, "admin");
        this.notifications = "null";
    }


    Admin(String username, String password, String notifications) {
        super(username, password, "admin");
        this.notifications = notifications;
    }


    public List<String[]> checkAllUsers(){
        return UserManipulations.getAllUsers();
    }
    public List<String[]> getTypeUser(String usertype){
        return UserManipulations.getSomeUsers(usertype);
    }
    
    public void makeReport(String report,String username){
        String[] member = UserManipulations.lookup("member",username);

        if (member != null){
            member[8] = report;

            // updates report in csv
            int line = UserManipulations.lineLookup("member",username);
            UserManipulations.updater("member", member,line);
        }
        else {
            System.out.println("user doesn't exist");
        }
    }


    public void changeBill(int newBill,String username){
        String[] user = UserManipulations.lookup("member",username);

        // changes bill
        if (user != null){
            user[5] = Integer.toString(newBill);
            int line = UserManipulations.lineLookup("member",username);
            UserManipulations.updater("member",user,line);
        }
        else {
            System.out.println("User doesn't exist");
        }
    }

    // this one looks for both the user and coach before assaging it
    public void assignCoach(String coachUsername, String memberUsername){
        String[] user = UserManipulations.lookup("member",memberUsername);

        if (user != null){;
            if (UserManipulations.lookup("coach",coachUsername) == null){
                System.out.println("coach not found");
                return;
            }

            user[6] = coachUsername;
            int line = UserManipulations.lineLookup("member",memberUsername);
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
    
    public void updateUserUsername(String userType, String oldUsername, String NewUsername)
    {
        // looksup for user in db and updates it
        String[] userData = UserManipulations.lookup(userType,oldUsername);
        int line = UserManipulations.lineLookup(userType,oldUsername);

        // checks if username is taken
        if (!UserManipulations.isUnique(userType, NewUsername)) {
            System.out.println("username already taken");
            return;
        }

        userData[1] = NewUsername;

        UserManipulations.updater(userType,userData,line);
    }


    public void updateUserPassword(String userType, String username, String password)
    {
        // looksup for user in db and updates its password
        String[] userData = UserManipulations.lookup(userType,username);
        int line = UserManipulations.lineLookup(userType,username);

        userData[2] = password;

        UserManipulations.updater(userType,userData,line);
    }

    // kill ali
    public void updateMemberEndDate(String username, String EndDate)
    {
        // looksup for user in db and updates its enddate
        String[] userData = UserManipulations.lookup("member",username);
        if (userData == null)
        {
            System.out.println("user does not exist");
            return;
        }
        int line = UserManipulations.lineLookup("member",username);


       EndDate = EndDate.replaceAll("\\b(\\d)\\b", "0$1"); // Zero-pad single digits
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Corrected pattern
        // Use getter for endDate
        if (EndDate == null || EndDate.isEmpty()) {
            System.out.println("please enter date");
            return; // If endDate is null (missing), return false
        }

        try {
            LocalDate endDateFormatted = LocalDate.parse(EndDate, formatter);
            LocalDate currentDate = LocalDate.now();
            // Return true if endDate is not before currentDate
            // if the entered date is not valid return it to null and display error
            if (!endDateFormatted.isBefore(currentDate))
            {
                userData[3] = endDateFormatted.toString();
                UserManipulations.updater("member",userData,line);
            }
            else
            {
                System.out.println("enter a proper end date in the future with proper date formatting dd-MM-yyyy");
            }
        }
         catch (DateTimeParseException e) {
           return; // Return false if the date format is invalid
        }
        
        
    }

    // cry to ali
    public void updateMemberNotification(String member, String notification)
    {
        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            System.out.println("This member does not exist in the system.");
            return;
        }

        // Member's notifications are at index 7 
        String memberNotifications = memberDetails[7];

        // Check if the notification is already in the member's notifications
        if (memberNotifications != "null" && Arrays.asList(memberNotifications.split(":")).contains(notification)) {
            System.out.println("This notification is already there.");
            return;
        }

        // Add the notification
        memberNotifications = (memberNotifications == null || memberNotifications.equals("null")) ? notification + ":" : memberNotifications + notification + ":";

        // Update memberDetails and save changes to CSV
        memberDetails[7] = memberNotifications;
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));
    }
    
    // cry to ali
    public void updateMemberSchedule(String username, String schedule)
    {
        String[] userData = UserManipulations.lookup("member",username);
        if (userData == null)
        {
            System.out.println("user does not exist");
            return;
        }

        // Member's schedule is at index 4
        String memberSchedule = userData[4];

        // Check if the schedule is already in the member's schedule
        if (memberSchedule != null && Arrays.asList(memberSchedule.split(":")).contains(schedule)) {
            System.out.println("This schedule is already there.");
            return;
        }

        // Add the schedule
        memberSchedule = (memberSchedule == null || memberSchedule.equals("null")) ? schedule + ":" : memberSchedule + schedule + ":";

        // Update memberDetails and save changes to CSV
        userData[4] = memberSchedule;
        UserManipulations.updater("member", userData, UserManipulations.lineLookup("member", username));

    }

    public void updateMemberBill(String username, int bill)
    {
        String[] userData = UserManipulations.lookup("member",username);
        if (userData == null)
        {
            System.out.println("user does not exist");
            return;
        }

        changeBill(bill, username);
    }

    
    public void updateMemberCoach(String memberUsername, String coachUsername)
    {
        // checks if member and coach exists before assigning
        String[] memberData = UserManipulations.lookup("member",memberUsername);
        String[] coachData = UserManipulations.lookup("coach",coachUsername);
        if (memberData == null)
        {
            System.out.println("member does not exist");
            return;
        }
        if (coachData == null)
        {
            System.out.println("coach does not exist");
            return;
        }
        assignCoach(coachUsername, memberUsername);
    }

    public String[] searchUser(String userType, String username)
    {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(userType)){
            System.out.println("invalid user type");
            return null;
        }

        // return an array of user data
        return UserManipulations.lookup(userType, username);
    }

    

    private String getNotifications() 
    {
        if (notifications == null || notifications.equals("null")) {
            System.out.println("There are no notifications to display.");
            return "null";
        }
        //update the values in the Class
        updateClass();
        return notifications;
    }

    public void setNotification(String notifications) 
    {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            System.out.println("This admin is not registered");
            return;
        }
        if(this.notifications == null || notifications.equals("null"))
        {
            updateNotifications("null");
        }
        else if(this.notifications.contains(":")){
            String s0 = this.notifications + notifications + ":";
            updateNotifications(s0);
        }
        else{
            updateNotifications(notifications + ":");
        }
    }

    private void updateNotifications(String notifications)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[3] = notifications;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.notifications = notifications;
    }

    public String[] getNotificationsArray() 
    {
        // Split the notifications string by ":"
        if (notifications == null || notifications.equals("null")) {
            return null;  // Return empty array if no notifications
        }
        return notifications.split(":");
    }

    public void printNotifications()
    {
        if(getNotificationsArray() == null)
        {
            System.out.println("No notifications");
        }
        else
        {
            for (String noti : getNotificationsArray()) {
                System.out.println(noti);
            }
        }
    }

    // Delete a notification from the notifications List
    public void deleteNotification(String notificationToRemove) 
    {
        String[] notificationsArray = this.getNotificationsArray();
        ArrayList<String> resultList = new ArrayList<>();

        // Loop through the array and add each string to the result list except the one to remove
        for (String str : notificationsArray) {
            if (!str.equals(notificationToRemove)) {
                resultList.add(str);
            }
        }
        this.setNotification("null"); // Clear notifications
        // Loop through each element in the updated list and apply the setNotifications function
        for (String item : resultList) {
            this.setNotification(item);
        }
        //update the values in Class 
        this.updateClass();
    }


    public void notifyMemberEndDate()
    {
        List<String[]> users = checkAllUsers();

        List<String[]> list = UserManipulations.getAllUsers();
        ArrayList<String> membersUsername = new ArrayList<>();
        ArrayList<String> membersDates = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            try {
                String x = list.get(i)[7];
                membersUsername.add(list.get(i)[1]);
                membersDates.add(list.get(i)[3]);
            }
            catch(IndexOutOfBoundsException e)
            {
                continue;
            }
        }

        for (int i = 0; i < membersUsername.size(); i++)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate endDateFormatted = LocalDate.parse(membersDates.get(i), formatter);
            LocalDate currentDate = LocalDate.now();

            if (endDateFormatted.isBefore(currentDate))
            {
                setNotification(membersUsername.get(i) + "'s subscription ended on " + membersDates.get(i));
            }
        }
    }

    private void updateClass()
    {
        String[] userData = UserManipulations.lookup(this.getUserType(), this.getUsername());
        notifications = userData[3];
    }


    @Override
    public void register() 
    {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(this.getUserType())) {
            System.out.println("invalid user type");
            return;
        }

        // checks if username is taken
        if (!UserManipulations.isUnique(this.getUserType(), this.getUsername())) {
            System.out.println("username already taken");
            return;
        }

        // adds user to the CSV file 
        try {
            String[] data = {
                this.getUsername(),
                this.getPassword(),
                this.getNotifications(),
            };
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            System.err.println("something went wrong");
        }
    }

}
