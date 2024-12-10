import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Member extends User {
    private String endDate;
    private String schedule;
    private String renewPrice;
    private String coach;
    private String notifications;

    // Constructor for Member with all valuables (probably will never use it)
    public Member(String username, String password, String endDate, String schedule, String renewPrice, String coach, String notifications) 
    {
        super(username, password, "member");
        this.setEndDate(endDate);
        this.setSchedule(schedule);
        this.setRenewPrice(renewPrice);
        this.setCoach(coach);
        this.setNotifications(notifications);
    }

    // Constructor for Member with username and password only
    public Member(String username, String password) 
    {
        super(username, password, "member");
        this.setEndDate("null");
        this.setSchedule("null");
        this.setRenewPrice("null");
        this.setCoach("null");
        this.setNotifications("null");
    }

    public String getEndDate() 
    {
        if (endDate.equals("null")) {
            System.out.println("There is no end date for the member.");
            return null;
        }
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSchedule() 
    {
        if (schedule.equals("null")) {
            System.out.println("There is no Schedule for this member");
            return null;
        }
        return notifications;
    }

    public void setSchedule(String schedule) 
    {
        // Append ":" after the notification
        if (this.schedule == null || this.schedule.equals("null")){
            this.schedule = schedule + ":";
        } 
        else if(schedule.equals("null")){
            this.schedule = "null";
        }
        else {
            this.notifications += schedule + ":";
        }
    }

    public String[] getScheduleArray() 
    {
        // Split the notifications string by ":"
        if (schedule == null || schedule.equals("null")) {
            return new String[0];  // Return empty array if no notifications
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
        //update the CSV file
        this.updateCSVFile();
    }


    public String getRenewPrice() 
    {
        if (renewPrice.equals("null")) {
            System.out.println("There is no renewal price for the member.");
            return "null";
        }
        return renewPrice;
    }

    public void setRenewPrice(String renewPrice) 
    {
        this.renewPrice = renewPrice;
    }

    public String getCoach() 
    {
        if (coach.equals("null")) {
            System.out.println("There is no coach for the member.");
            return "null";
        }
        return coach;
    }

    public void setCoach(String coach) 
    {
        this.coach = coach;
    }

    public String getNotifications() 
    {
        if (notifications.equals("null")) {
            System.out.println("There are no notifications to display.");
            return "null";
        }
        return notifications;
    }

    public void setNotifications(String notification) 
    {
        // Append ":" after the notification
        if (notifications == null || notifications.equals("null")) {
            this.notifications = notification + ":";
        }else if(notification.equals("null")){
            this.notifications = "null";
        }
        else {
            this.notifications += notification + ":";
        }
    }

    public String[] getNotificationsArray() 
    {
        // Split the notifications string by ":"
        if (notifications == null || notifications.equals("null")) {
            return new String[0];  // Return empty array if no notifications
        }
        return notifications.split(":");
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
        this.setNotifications("null"); // Clear notifications
        // Loop through each element in the updated list and apply the setNotifications function
        for (String item : resultList) {
            this.setNotifications(item);
        }
        //update the CSV file
        this.updateCSVFile();
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
                this.getEndDate(), 
                this.getSchedule(), 
                this.getRenewPrice(),
                this.getCoach(), 
                this.getNotifications(),
            };
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            System.err.println("something went wrong");
        }
    }

    @Override
    public void login() 
    {
        // checks if there's a logged in account in all users
        if (super.getSomeoneIsLoggedin()) {
            System.out.println("logout from all accounts to login");
            return;
        }

        if (!checkEndDate()) {
            this.setNotifications("Your subscription has ended");
        }

        // logs user in and flags all users that there's a logged in user
        super.setSomeoneIsLoggedin(true);
        this.setUserIsLoggedin(true);
    }

    public boolean checkEndDate() 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Corrected pattern
        String endDate = this.getEndDate(); // Use getter for endDate
        if (endDate == null) {
            return false; // If endDate is null (missing), return false
        }

        LocalDate endDateFormatted = LocalDate.parse(endDate, formatter);
        LocalDate currentDate = LocalDate.now();

        return !endDateFormatted.isBefore(currentDate); // Return true if endDate is not before currentDate
    }
    public void updateCSVFile()
    {
        String[] newContent = {
            this.getUsername(),
            this.getPassword(),
            this.getEndDate(), 
            this.getSchedule(), 
            this.getRenewPrice(),
            this.getCoach(), 
            this.getNotifications(),
        };
        UserManipulations.updater(this.getUserType(), newContent, UserManipulations.lineLookup(this.getUserType(), this.getUsername()));
    }
}
