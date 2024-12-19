import java.util.Arrays;
import java.util.ArrayList;

public class Coach extends User{
    private String members;
    //probably won't use this constructor
    public Coach() {}
    public Coach(String username, String password,String members) 
    {
        super(username, password, "coach");
        this.members = members;
    }
    // Constructor for Member with username and password only
    public Coach(String username, String password) 
    {
        super(username, password, "coach");
        this.members = "null";
    }
    private String getMembers() 
    {
        if (members.equals("null")) {
            return "null";
        }
        return members;
    }

    public void setMember(String members) 
    {
        String[] userIsRegistered = UserManipulations.lookup(this.getUserType(), this.getUsername());
        if (userIsRegistered == null) {
            throw new IllegalArgumentException("This Coach is not registered");
        }
        //entry is null 
        if(this.members == null || members.equals("null"))
        {
            //update in CSV file
            updateCoachInMembers("null");
            updateMembers("null");
        }
        //members has already an entry  
        else if(this.members.contains(":")){
            String s1 = this.members + members + ":";
            //update in CSV
            updateCoachInMembers(members);

            updateMembers(s1);
        }
        //first entery in the CSV file 
        else{
            //update in CSV file
            updateCoachInMembers(members);

            updateMembers(members + ":");
        }
    }

    private void updateMembers(String members)
    {
        // updates user data in the CSV file  
        String[] userdata = UserManipulations.lookup(this.getUserType(), this.getUsername());
        int line = UserManipulations.lineLookup(this.getUserType(), this.getUsername());
        userdata[3] = members;
        UserManipulations.updater(this.getUserType(), userdata, line);


        // updates user data in program
        this.members = members;
    }


    private void updateCoachInMembers(String members) {
        String[] memberdata = UserManipulations.lookup("member", members);
        int line2 = UserManipulations.lineLookup("member", members);
        memberdata[6] = members;
        UserManipulations.updater("member", memberdata, line2);
    }

    public String[] getMembersArray() {
        // Split the members string by ":"
        String[] coach = UserManipulations.lookup("coach", this.getUsername());
        String membersOfCoach = coach[3];

        if (membersOfCoach == null || membersOfCoach.isBlank() || membersOfCoach.equals("null")) {
            return new String[0];  // Return empty array if no members
        }
        //retrun the String Array of members
        return membersOfCoach.split(":");
    }

    // Delete a notification from the members List
    public void deleteMember(String memberToRemove) {
        String[] membersArray = this.getMembersArray();
        ArrayList<String> resultList = new ArrayList<>();

        // Loop through the array and add each string to the result list except the one to remove
        for (String str : membersArray) {
            if (!str.equals(memberToRemove)) {
                resultList.add(str);
            }
        }
        this.setMember("null"); // Clear notifications
        // Loop through each element in the updated list and apply the setmembers function
        for (String item : resultList) {
            this.setMember(item);
        }
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
                this.getMembers(),
            };
            UserManipulations.AddUser(this.getUserType(), data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    
    private void updateCSVFile()
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
            throw new IllegalArgumentException("This member is not assigned to this coach.");
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            throw new IllegalArgumentException("This member does not exist in the system.");
        }

        // Member's schedule is at index 3 
        String memberSchedule = memberDetails[4];
        
        if(schedule.equals("null"))
        {
            memberSchedule = "null";
        }
        else if(memberSchedule.contains(":")){
            String s0 = memberSchedule + schedule + ":";
            memberSchedule = s0;
        }
        else{
            memberSchedule = schedule + ":";
        }
        // Update memberDetails and save changes to CSV
        memberDetails[4] = memberSchedule;
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));
    }

    public void deleteScheduleFromMember(String member, String schedule) 
    {
        // Check if the member is in the Coach's members array
        if (!Arrays.asList(this.getMembersArray()).contains(member)) {
            throw new IllegalArgumentException("This member is not assigned to this coach.");
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            throw new IllegalArgumentException("This member does not exist in the system.");
        }

        // Member's schedule is at index 3 
        String memberSchedule = memberDetails[3];

        // Check if the schedule exists in the member's schedule
        if (memberSchedule.equals("null") || !Arrays.asList(memberSchedule.split(":")).contains(schedule)) {
            throw new IllegalArgumentException("This schedule does not exist for the member.");
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
    }

    public void addNotificationToMember(String member, String notification) {
        // Check if the member is in the Coach's members array
        if (!Arrays.asList(this.getMembersArray()).contains(member)) {
            throw new IllegalArgumentException("This member is not assigned to this coach.");
        }

        // Use the lookup function to get the member's details
        String[] memberDetails = UserManipulations.lookup("member", member);
        if (memberDetails == null) {
            throw new IllegalArgumentException("This member does not exist in the system.");
        }
        String memberNotifications = memberDetails[7];
        if(notification.equals("null"))
        {
            memberNotifications = "null";
        }
        else if(memberNotifications.contains(":")){
            String s0 = memberNotifications + notification + ":";
            memberNotifications = s0;
        }
        else{
            memberNotifications = notification + ":";
        }
        // Update memberDetails and save changes to CSV
        memberDetails[7] = memberNotifications;
        UserManipulations.updater("member", memberDetails, UserManipulations.lineLookup("member", member));

    }
}
