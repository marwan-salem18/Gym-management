import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;


public class Member extends User {
    private String endDate = "null";
    private String schedule = "null";
    private String renewPrice = "null";
    private String coach = "null";
    private String notifications = "null";
    private String report = "null";

    // Constructor for Member with all valuables (probably will never use it)
    public Member(String username, String password, String endDate, String schedule, String renewPrice, String coach, String notifications) 
    {
        super(username, password, "member");
        this.endDate = endDate;
        this.schedule = schedule;
        this.renewPrice = renewPrice;
        this.coach = coach;
        this.notifications = notifications;
    }

    // Constructor for Member with username and password only
    public Member(String username, String password) 
    {
        super(username, password, "member");
        this.endDate = "null";
        this.schedule = "null";
        this.renewPrice = "null";
        this.coach = "null";
        this.notifications = "null";
    }

    public String getEndDate() 
    {
        updateClass();
        if (endDate == null || endDate.equals("null")) {
            return "null";
        }
        return endDate;
    }

    public void setEndDate(String endDate) {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            return;
        }
        if(this.endDate == null || endDate.equals("null"))
        {
            updateEndDate("null");
        }
        else{
            updateEndDate(endDate);
        }
    }

    private void updateEndDate(String endDate)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[3] = endDate;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.endDate = endDate;
    }

    private String getSchedule() 
    {
        updateClass();
        if (schedule == null || schedule.equals("null")) {
            return "null";
        }
        return schedule;
    }

    public void setSchedule(String schedule) 
    {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            return;
        }
        if(this.schedule == null || schedule.equals("null"))
        {
            updateSchedule("null");
        }
        else if(this.schedule.contains(":")){
            String s0 = this.schedule + schedule + ":";
            updateSchedule(s0);
        }
        else{
            updateSchedule(schedule + ":");
        }
    }

    private void updateSchedule(String schedule)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[4] = schedule;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.schedule = schedule;
    }

    public String[] getScheduleArray() 
    {
        updateClass();
        // Split the notifications string by ":"
        if (schedule == null || schedule.equals("null")) {
            return null;  // Return null if no notifications
        }
        return schedule.split(":");
    }

    // Delete a schedule from the schedules List
    public void deleteSchedule(String scheduleToRemove) 
    {
        String[] scheduleArray = this.getScheduleArray();
        ArrayList<String> resultList = new ArrayList<>();

        // Loop through the array and add each string to the result list except the one to remove
        for (String str : scheduleArray) {
            if (!str.equals(scheduleToRemove)) {
                resultList.add(str);
            }
        }
        this.setSchedule("null"); // Clear schedules 
        // Loop through each element in the updated list and apply the setSchedule function
        for (String item : resultList) {
            this.setSchedule(item);
        }
    }


    public String getRenewPrice() 
    {
        updateClass();
        if (renewPrice == null || renewPrice.equals("null")) {
            return "null";
        }
        return renewPrice;
    }

    public void setRenewPrice(String renewPrice) {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("This Member is not registered");
        }
        if(this.renewPrice == null || renewPrice.equals("null"))
        {
            updateRenewPrice("null");
        }
        else{
            updateRenewPrice(renewPrice);
        }
    }

    private void updateRenewPrice(String renewPrice)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[5] = renewPrice;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.renewPrice = renewPrice;
    }

    public String getCoach() 
    {
        updateClass();
        if (coach == null || coach == "null") {
            return "null";
        }
        //update the values in the Class
        return coach;
    }

    public void setCoach(String coach) {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("This Member is not registered");
        }
        if(this.coach == null || coach == "null")
        {
            updateCoach("null");
        }
        else{
            updateCoach(coach);
        }
    }

    private void updateCoach(String coach)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[6] = coach;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.coach = coach;
    }


    private String getNotifications() 
    {
        updateClass();
        if (notifications == null || notifications.equals("null")) {
            return "null";
        }
        return notifications;
    }

    public void setNotification(String notifications) 
    {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("This Member is not registered");
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
        userdata[7] = notifications;
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

    @Override
    public void register() 
    {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(this.getUserType())) {
            throw new IllegalArgumentException("invalid user type");
        }

        // checks if username is taken
        if (!UserManipulations.isUnique(this.getUserType(), this.getUsername())) {
            throw new IllegalArgumentException("username already taken");
        }

        // adds user to the CSV file 
        try {
            String[] data = {
                this.getUsername(),
                this.getPassword(),
                this.getEndDate(), 
                this.getSchedule(), 
                this.getRenewPrice(),
                this.getCoach(), 
                this.getNotifications(),
                this.getReport(),
            };
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            throw new IllegalArgumentException("something went wrong");
        }
    }

    @Override
    public void login(String username, String password)
    {
        // checks if user exists
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), username);
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("user is not registered");
        }

        if (!password.equals(userIsRegistered[2]))
        {
            throw new IllegalArgumentException("invalid password");
        }

        // checks if there's a logged in account in all users
        if (super.getSomeoneIsLoggedin()) {
            throw new IllegalArgumentException("logout from all accounts to login");
        }
        if (!checkEndDate()) {
            this.setNotification("Your subscription has ended");
        }
        // logs user in and flags all users that there's a logged in user
        super.setSomeoneIsLoggedin(true);
        this.setUserIsLoggedin(true);

    }

    private boolean checkEndDate() 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        String endDate = this.getEndDate(); // Use getter for endDate
        if (endDate == null || endDate.isEmpty()) {
            return false; // If endDate is null (missing), return false
        }
        try
        {
            LocalDate endDateFormatted = LocalDate.parse(endDate, formatter);
            LocalDate currentDate = LocalDate.now();

            // Return true if endDate is not before currentDate
            return !endDateFormatted.isBefore(currentDate);
        } 
        catch(DateTimeParseException e)
        {
            return false;
        }

    }

    private void updateCSVFile()
    {
        String[] userData = UserManipulations.lookup(this.getUserType(), this.getUsername());
        String[] newContent = {
            userData[0],
            this.getUsername(),
            this.getPassword(),
            endDate,
            schedule,
            renewPrice,
            coach,
            notifications,
            report,
        };
        UserManipulations.updater(this.getUserType(), newContent, UserManipulations.lineLookup(this.getUserType(), this.getUsername()));
    }

    private void updateClass()
    {
        String[] userData = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if(userData == null)
        {
            return;
        }
        else
        {
            endDate = userData[3];
            schedule = userData[4];
            renewPrice = userData[5];
            coach = userData[6];
            notifications = userData[7];
            report = userData[8];
        }
    }

    public String getReport()
    {
        updateClass();
        if (report == null || report.equals("null")) {
            return "null";
        }
        return report;
    }
}
