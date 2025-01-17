
import javax.imageio.plugins.tiff.TIFFField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends User {

    private String notifications = "null";

    Admin() {
    }

    Admin(String username, String password) {
        super(username, password, "admin");
        this.notifications = "null";
    }

    Admin(String username, String password, String notifications) {
        super(username, password, "admin");
        this.notifications = notifications;
    }

    private List<String[]> checkAllUsers() {
        return UserManipulations.getAllUsers();
    }

    public List<String[]> getAllUserOfType(String usertype) {
        return UserManipulations.getSomeUsers(usertype);
    }

    public void makeReport(String report, String username) {
        String[] member = UserManipulations.lookup("member", username);

        if (member != null) {
            member[8] = report + ",";

            // updates report in csv
            int line = UserManipulations.lineLookup("member", username);
            UserManipulations.updater("member", member, line);
        } else {
            throw new IllegalArgumentException("Member doesn't exist");
        }
    }

    public void changeBill(int newBill, String username) {
        String[] user = UserManipulations.lookup("member", username);

        // changes bill
        if (user != null) {
            user[5] = Integer.toString(newBill);
            int line = UserManipulations.lineLookup("member", username);
            UserManipulations.updater("member", user, line);
        } else {
            throw new IllegalArgumentException("User doesn't exist");
        }
    }

    // this one looks for both the user and coach before assaging it
    public void assignCoach(String coachUsername, String memberUsername) {
        String[] member = UserManipulations.lookup("member", memberUsername);
        String[] coach = UserManipulations.lookup("coach", coachUsername);
        int memberId = UserManipulations.lineLookup("member", memberUsername);
        int coachId = UserManipulations.lineLookup("coach", coachUsername);

        if (coach == null || coachId < 0) {
            throw new IllegalArgumentException("Coach doesn't exist");
        }

        if (member == null || memberId < 0) {
            throw new IllegalArgumentException("Member doesn't exist");
        }

        if(member[6].equals(coachUsername)){
            throw new IllegalArgumentException("Member is already assigned to this coach");
        }

        member[6] = coachUsername;

        if (!coach[3].equals("null")) {
            String memberOfCoach = coach[3];
            coach[3] = memberOfCoach + memberUsername + ":";
        } else {
            coach[3] = memberUsername + ":";
        }
        try{
            UserManipulations.updater("member", member, memberId);
            UserManipulations.updater("coach", coach, coachId);
        }catch (Exception e){
            throw new IllegalArgumentException("Error assigning coach");
        }
    }

    public void deleteUser(String username, String usertype) {
        String[] user = UserManipulations.lookup(usertype, username);

        if (user == null) {
            throw new IllegalArgumentException("No user with that username");
        }

        int line = UserManipulations.lineLookup(usertype, username);
        UserManipulations.DeleteLine(usertype, line);
    }

    public void updateUserUsername(String userType, String oldUsername, String newUsername) {
        // looksup for user in db and updates it
        String[] userData = UserManipulations.lookup(userType, oldUsername);
        int line = UserManipulations.lineLookup(userType, oldUsername);

        // checks if username is taken
        if (!UserManipulations.isUnique("admin", newUsername)) {
            throw new IllegalArgumentException("username already taken");

        } else if (!UserManipulations.isUnique("member", newUsername)) {
            throw new IllegalArgumentException("username already taken");

        } else if (!UserManipulations.isUnique("coach", newUsername)) {
            throw new IllegalArgumentException("username already taken");
        }

        userData[1] = newUsername;

        UserManipulations.updater(userType, userData, line);
    }

    public void updateUserPassword(String userType, String username, String password) {
        // looksup for user in db and updates its password
        String[] userData = UserManipulations.lookup(userType, username);
        int line = UserManipulations.lineLookup(userType, username);

        userData[2] = password;

        UserManipulations.updater(userType, userData, line);
    }

    // kill ali
    public void updateMemberEndDate(String username, String EndDate) {
        // looksup for user in db and updates its enddate
        String[] userData = UserManipulations.lookup("member", username);
        if (userData == null) {
            throw new IllegalArgumentException("user does not exist");
        }
        int line = UserManipulations.lineLookup("member", username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Corrected pattern

        // Use getter for endDate
        if (EndDate == "null" || EndDate.isEmpty()) {
            return; // If endDate is null (missing), return false
        }

        try {
            LocalDate endDateFormatted = LocalDate.parse(EndDate, formatter);
            LocalDate currentDate = LocalDate.now();

            String date = endDateFormatted.format(formatter);

            // Return true if endDate is not before currentDate
            if (!endDateFormatted.isBefore(currentDate)) {
                userData[3] = date;
                UserManipulations.updater("member", userData, line);
            } else {
                throw new IllegalArgumentException("enter a proper end date in the future with proper date formatting d-M-yyyy");
            }
        } catch (DateTimeParseException e) {
            return; // Return false if the date format is invalid
        }

    }

    // cry to ali
    public void updateMemberNotification(String member, String notification) {
        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            throw new IllegalArgumentException("This member does not exist in the system.");
        }

        // Member's notifications are at index 7
        String memberNotifications = memberDetails[7];

        // Check if the notification is already in the member's notifications
        if (memberNotifications != "null" && Arrays.asList(memberNotifications.split(":")).contains(notification)) {
            throw new IllegalArgumentException("This notification is already there.");
        }
        if (notification.equals("null")) {
            memberNotifications = "null";
        } else if (memberNotifications.contains(":")) {
            String s0 = memberNotifications + notification + ":";
            memberNotifications = s0;
            throw new IllegalArgumentException(memberNotifications);
        } else {
            memberNotifications = notification + ":";
        }

        // Update memberDetails and save changes to CSV
        memberDetails[7] = memberNotifications;
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));
    }

    // cry to ali
    public void updateMemberSchedule(String username, String schedule) {
        String[] userData = UserManipulations.lookup("member", username);
        if (userData == null) {
            throw new IllegalArgumentException("user does not exist");
        }

        // Member's schedule is at index 4
        String memberSchedule = userData[4];

        // Check if the schedule is already in the member's schedule
        if (memberSchedule != null && Arrays.asList(memberSchedule.split(":")).contains(schedule)) {
            throw new IllegalArgumentException("This schedule is already there.");
        }

        if (schedule.equals("null")) {
            memberSchedule = "null";
        } else if (memberSchedule.contains(":")) {
            String s0 = memberSchedule + schedule + ":";
            memberSchedule = s0;
        } else {
            memberSchedule = schedule + ":";
        }
        // Update memberDetails and save changes to CSV
        userData[4] = memberSchedule;
        UserManipulations.updater("member", userData, UserManipulations.lineLookup("member", username));

    }

    public void updateMemberBill(String username, int bill) {
        String[] userData = UserManipulations.lookup("member", username);
        if (userData == null) {
            throw new IllegalArgumentException("user does not exist");
        }

        changeBill(bill, username);
    }

    public void updateMemberCoach(String memberUsername, String coachUsername) {
        // checks if member and coach exists before assigning
        String[] memberData = UserManipulations.lookup("member", memberUsername);
        String[] coachData = UserManipulations.lookup("coach", coachUsername);
        if (memberData == null) {
            throw new IllegalArgumentException("member does not exist");
        }
        if (coachData == null) {
            throw new IllegalArgumentException("coach does not exist");
        }
        assignCoach(coachUsername, memberUsername);
    }

    public String[] searchUser(String userType, String username) {
        // checks if usertype is valid
        String[] validTypes = {"admin", "member", "coach"};
        if (!Arrays.asList(validTypes).contains(userType)) {
            throw new IllegalArgumentException("invalid user type");
        }

        // return an array of user data
        return UserManipulations.lookup(userType, username);
    }

    private String getNotifications() {
        if (notifications == null || notifications.equals("null")) {
            return "null";
        }
        //update the values in the Class
        updateClass();
        return notifications;
    }

    public void setNotification(String notifications) {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("This admin is not registered");
        }
        if (this.notifications == null || notifications.equals("null")) {
            updateNotifications("null");
        } else if (this.notifications.contains(":")) {

            String[] notiArray = getNotificationsArray();
            for (String noti : notiArray)
            {
                if (noti.equals(notifications))
                    return;
            }

            String s0 = this.notifications + notifications + ":";
            updateNotifications(s0);
        } else {
            updateNotifications(notifications + ":");
        }
    }

    private void updateNotifications(String notifications) {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[3] = notifications;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.notifications = notifications;
    }

    public String[] getNotificationsArray() {

        String[] memberData = UserManipulations.lookup("admin", this.getUsername());
        String memberNotifications = memberData[3];
        // Split the notifications string by ":"
        if (memberNotifications == null || memberNotifications.equals("null") || memberNotifications.isBlank() ) {
            return new String[0];  // Return empty array if no notifications
        }
        return memberNotifications.split(":");
    }

    public void printNotifications() {
        if (getNotificationsArray() == null) {
            System.out.println("No notifications");
        } else {
            for (String noti : getNotificationsArray()) {
                System.out.println(noti);
            }
        }
    }

    // Delete a notification from the notifications List
    public void deleteNotification(String notificationToRemove) {
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

    public void notifyMemberEndDate() {
        List<String[]> users = checkAllUsers();

        List<String[]> list = UserManipulations.getAllUsers();
        ArrayList<String> membersUsername = new ArrayList<>();
        ArrayList<String> membersDates = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                String x = list.get(i)[7];
                membersUsername.add(list.get(i)[1]);
                membersDates.add(list.get(i)[3]);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }

        for (int i = 0; i < membersUsername.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                LocalDate endDateFormatted = LocalDate.parse(membersDates.get(i), formatter);
                LocalDate currentDate = LocalDate.now();
                if (endDateFormatted.isBefore(currentDate)) {
                    setNotification(membersUsername.get(i) + "'s subscription ended on " + membersDates.get(i));
                } 
            } catch (DateTimeParseException e) {
                continue;
            }
        }
    }

    private void updateClass() {
        String[] userData = UserManipulations.lookup(this.getUserType(), this.getUsername());
        notifications = userData[3];
    }

    @Override
    public void register() {
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
                this.getNotifications(),};
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addUser(String username, String password, String Usertype) {
        if (Usertype == "admin") {
            String[] data = {
                username,
                password,
                "null",};
            UserManipulations.AddUser(Usertype, data);
            return;
        } else if (Usertype == "coach") {
            String[] data = {
                username,
                password,
                "null",};
            UserManipulations.AddUser(Usertype, data);
            return;
        } else if (Usertype == "member") {
            String[] data = {
                username,
                password,
                "null",
                "null",
                "null",
                "null",
                "null",
                "null",};
            UserManipulations.AddUser(Usertype, data);
            return;
        }
    }

}
