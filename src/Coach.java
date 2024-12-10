import java.util.Arrays;
import java.util.ArrayList;

public class Coach extends User{
    private String members;
    public Coach(String username, String password,String members) 
    {
        super(username, password, "coach");
        this.members = members;
    }
    public Coach(String username, String password) 
    {
        super(username, password, "coach");
        this.members = "null";
    }
    public String getMembers() 
    {
        if (members.equals("null")) {
            System.out.println("There is no members assigned to this Coach");
            return "null";
        }
        return members;
    }

    public void setMembers(String members) 
    {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            System.out.println("This Coach is not registered");
            return;
        }
        if(this.members == null || members.equals("null"))
        {
            updateMembers("null");
        }
        else if(this.members.contains(":")){
            String s1 = this.members + members + ":";
            updateMembers(s1);
        }
        else{
            updateMembers(members + ":");
        }
    }

    public void updateMembers(String members)
    {
        // updates user data in the db
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[3] = members;
        UserManipulations.updater(this.getUserType(), userdata, line);
        // updates user data in program
        this.members = members;
    }
    public String[] getMembersArray() {
        // Split the members string by ":"
        if (members == null || members.equals("null")) {
            return new String[0];  // Return empty array if no members
        }
        //retrun the String Array of members
        return members.split(":");
    }

    // Delete a notification from the members List
    public void deleteMembers(String memberToRemove) {
        String[] membersArray = this.getMembersArray();
        ArrayList<String> resultList = new ArrayList<>();

        // Loop through the array and add each string to the result list except the one to remove
        for (String str : membersArray) {
            if (!str.equals(memberToRemove)) {
                resultList.add(str);
            }
        }
        this.setMembers("null"); // Clear notifications
        // Loop through each element in the updated list and apply the setmembers function
        for (String item : resultList) {
            this.setMembers(item);
        }
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
                this.getMembers(),
            };
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            System.err.println("something went wrong");
        }
    }
    
    public void updateCSVFile()
    {
        String[] newContent = {
            this.getUsername(),
            this.getPassword(),
            this.getMembers(),
        };
        UserManipulations.updater(this.getUserType(), newContent, UserManipulations.lineLookup(this.getUserType(), this.getUsername()));
    }

    public void addScheduleToMember(String member, String schedule) 
    {
        // Check if the member is in the Coach's members array
        if (!Arrays.asList(this.getMembersArray()).contains(member)) {
            System.out.println("This member is not assigned to this coach.");
            return;
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            System.out.println("This member does not exist in the system.");
            return;
        }

        // Member's schedule is at index 3 
        String memberSchedule = memberDetails[4];

        // Check if the schedule is already in the member's schedule
        if (memberSchedule != null && Arrays.asList(memberSchedule.split(":")).contains(schedule)) {
            System.out.println("This schedule is already there.");
            return;
        }

        // Add the schedule
        memberSchedule = (memberSchedule == null || memberSchedule.equals("null")) ? schedule + ":" : memberSchedule + schedule + ":";

        // Update memberDetails and save changes to CSV
        memberDetails[4] = memberSchedule;
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));

        updateCSVFile();
    }

    public void deleteScheduleFromMember(String member, String schedule) 
    {
        // Check if the member is in the Coach's members array
        if (!Arrays.asList(this.getMembersArray()).contains(member)) {
            System.out.println("This member is not assigned to this coach.");
            return;
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            System.out.println("This member does not exist in the system.");
            return;
        }

        // Member's schedule is at index 3 
        String memberSchedule = memberDetails[3];

        // Check if the schedule exists in the member's schedule
        if (memberSchedule.equals("null") || !Arrays.asList(memberSchedule.split(":")).contains(schedule)) {
            System.out.println("This schedule does not exist for the member.");
            return;
        }

        // Remove the schedule
        String[] scheduleArray = memberSchedule.split(":");
        ArrayList<String> updatedSchedule = new ArrayList<>();
        for (String s : scheduleArray) {
            if (!s.equals(schedule)) {
                updatedSchedule.add(s);
            }
        }

        // Update memberDetails and save changes to CSV
        memberDetails[4] = String.join(":", updatedSchedule) + (updatedSchedule.isEmpty() ? "" : ":");
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));

        System.out.println("Schedule deleted successfully for member: " + member);
    }

    public void addNotificationToMember(String member, String notification) {
        // Check if the member is in the Coach's members array
        if (!Arrays.asList(this.getMembersArray()).contains(member)) {
            System.out.println("This member is not assigned to this coach.");
            return;
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            System.out.println("This member does not exist in the system.");
            return;
        }

        // Member's notifications are at index 6 
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

        System.out.println("Notification added successfully for member: " + member);
    }
}
