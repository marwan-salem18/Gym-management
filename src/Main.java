import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // DON'T DELETE ME
        UserManipulations.initailizeFiles();
    


/* 
        // Create Member object using the constructor with all properties
        Member member1 = new Member("john_doe", "password123", "10-12-2025", "Monday 10:00AM", "49.99", "Ahmed", "Welcome!");

        // Check registration
        System.out.println("Attempting to register member1...");
        member1.register(); // Make sure to adjust the UserManipulations class to avoid actual database calls for testing

        // Check login functionality
        member1.login();
        System.out.println("Member logged in: " + member1.getUserIsLoggedin());
        // Display initial details
        System.out.println("Initial member details:");
        System.out.println("Username: " + member1.getUsername());
        System.out.println("End Date: " + member1.getEndDate());
        System.out.println("Schedule: " + member1.getSchedule());
        System.out.println("Renew Price: " + member1.getRenewPrice());
        System.out.println("Notifications: " + member1.getNotifications());

        // Test notifications
        member1.setNotifications("New update available");
        System.out.println("Updated notifications: " + member1.getNotifications());

        // Show notifications array
        System.out.println("Notifications array:");
        System.out.println(Arrays.toString(member1.getNotificationsArray()));

        // Delete a notification
        member1.deleteNotification("New update available");
        System.out.println("Notifications after deletion: " + member1.getNotifications());

        //Log out
        member1.logout();

        // Create another Member object with username and password only (no subscription details)
        Member member2 = new Member("jane_doe", "password456");


        // Check registration
        System.out.println("Attempting to register member2...");
        member2.register(); // Make sure to adjust the UserManipulations class to avoid actual database calls for testing

        // Set all the details for member2 using setters
        member2.setEndDate("15-12-2024"); // Example end date
        member2.setSchedule("Mon-Fri 9AM - 5PM"); // Example schedule
        member2.setRenewPrice("50 USD"); // Example renewal price
        member2.setCoach("Coach Smith"); // Example coach name
        member2.setNotifications("Welcome to the club!"); // Example notification

        // Test login for member2 (subscription set)
        member2.login();
        System.out.println("Member2 logged in: " + member2.getUserIsLoggedin());
        System.out.println("Member2 notifications after login: " + member2.getNotifications());

        // Test end date check for member2
        // boolean isEndDateValid = member2.checkEndDate();
        // System.out.println("Is member2's subscription valid? " + isEndDateValid);

        // Get all information for member2
        // System.out.println("Member2 End Date: " + member2.getEndDate());
        // System.out.println("Member2 Schedule: " + member2.getSchedule());
        // System.out.println("Member2 Renew Price: " + member2.getRenewPrice());
        // System.out.println("Member2 Coach: " + member2.getCoach());
        // System.out.println("Member2 Notifications: " + member2.getNotifications());
        System.out.println(member2.getUserType());
        System.out.println(UserManipulations.lineLookup(member2.getUserType(), member2.getUsername()));
        member2.updateCSVFile();
*/      
        // Create three different members
        Member member1 = new Member("member1", "password1");
        Member member2 = new Member("member2", "password2");
        Member member3 = new Member("member3", "password3");

        member1.register();
        member2.register();
        member3.register();

        // Print their initial schedules and notifications
        System.out.println("Initial schedules and notifications:");
        printMemberDetails(member1);
        printMemberDetails(member2);
        printMemberDetails(member3);

        // Create a coach and assign members
        Coach coach1 = new Coach("coach1", "password1");
        coach1.setMembers("member1");
        coach1.setMembers("member2");
        coach1.setMembers("member3");
        
        coach1.register();

        // Add different schedules and notifications for each member
        coach1.addScheduleToMember("member1", "Yoga at 10 AM");
        coach1.addNotificationToMember("member1", "Bring your yoga mat!");

        coach1.addScheduleToMember("member2", "Cardio at 12 PM");
        coach1.addNotificationToMember("member2", "Wear running shoes!");

        coach1.addScheduleToMember("member3", "Strength Training at 2 PM");
        coach1.addNotificationToMember("member3", "Bring your water bottle!");

        // Add a common notification for all members
        String commonNotification = "Monthly meeting this Friday!";
        coach1.addNotificationToMember("member1", commonNotification);
        coach1.addNotificationToMember("member2", commonNotification);
        coach1.addNotificationToMember("member3", commonNotification);

        // Print updated schedules and notifications
        System.out.println("\nUpdated schedules and notifications:");
        printMemberDetails(member1);
        printMemberDetails(member2);
        printMemberDetails(member3);

        // Repeat the same for another set of members and coach
        Member member4 = new Member("member4", "password4");
        Member member5 = new Member("member5", "password5");
        Member member6 = new Member("member6", "password6");

        System.out.println("\nInitial schedules and notifications for new members:");
        printMemberDetails(member4);
        printMemberDetails(member5);
        printMemberDetails(member6);

        Coach coach2 = new Coach("coach2", "password2");

        coach2.register();

        coach2.setMembers("member4");
        coach2.setMembers("member5");
        coach2.setMembers("member6");

        coach2.addScheduleToMember("member4", "Swimming at 8 AM");
        coach2.addNotificationToMember("member4", "Don't forget your swimsuit!");

        coach2.addScheduleToMember("member5", "Boxing at 10 AM");
        coach2.addNotificationToMember("member5", "Bring your gloves!");

        coach2.addScheduleToMember("member6", "Pilates at 3 PM");
        coach2.addNotificationToMember("member6", "Bring a towel!");

        String commonNotification2 = "Weekend workshop this Saturday!";
        coach2.addNotificationToMember("member4", commonNotification2);
        coach2.addNotificationToMember("member5", commonNotification2);
        coach2.addNotificationToMember("member6", commonNotification2);

        System.out.println("\nUpdated schedules and notifications for new members:");
        printMemberDetails(member4);
        printMemberDetails(member5);
        printMemberDetails(member6);
    }
    private static void printMemberDetails(Member member) {
        System.out.println("Member: " + member.getUsername());
        System.out.println("Schedule: " + member.getSchedule());
        System.out.println("Notifications: " + member.getNotifications());
        System.out.println("-----------------------------------");

    }
}
