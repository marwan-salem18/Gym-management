public class Main {
    public static void main(String[] args) {
        // DON'T DELETE ME
        UserManipulations.initailizeFiles();

        // Create and register initial members
        Member member1 = new Member("member1", "Yoga");
        Member member2 = new Member("member2", "Pilates");
        Member member3 = new Member("member3", "Zumba");

        member1.register();
        member2.register();
        member3.register();

        // Create and register a coach for the initial members
        Coach coach1 = new Coach("Coach1", "Fitness_Expert");
        coach1.register();

        // Assign members to the first coach and set the coach for members
        coach1.setMembers(member1.getUsername());
        coach1.setMembers(member2.getUsername());
        coach1.setMembers(member3.getUsername());

        member1.setCoach(coach1.getUsername());
        member2.setCoach(coach1.getUsername());
        member3.setCoach(coach1.getUsername());

        // Add schedules and notifications for the initial members
        member1.setSchedule("Yoga: Monday and Wednesday at 6 PM");
        member1.setNotifications("Don't forget your mat!");
        member1.setRenewPrice("50.0"); // Set renewal price

        member2.setSchedule("Pilates: Tuesday and Thursday at 7 PM");
        member2.setNotifications("Bring your water bottle!");
        member2.setRenewPrice("---------------------------"); // Set renewal price

        member3.setSchedule("Zumba: Friday at 5 PM");
        member3.setNotifications("Wear comfortable shoes!");
        member3.setRenewPrice("--------------------------"); // Set renewal price

        // Create and register new members
        Member member4 = new Member("member4", "Aerobics");
        Member member5 = new Member("member5", "Spin Class");
        Member member6 = new Member("member6", "CrossFit");

        member4.register();
        member5.register();
        member6.register();

        // Create and register a new coach for the new members
        Coach coach2 = new Coach("Coach2", "Strength_Training");
        coach2.register();

        // Assign new members to the new coach and set the coach for members
        coach2.setMembers(member4.getUsername());
        coach2.setMembers(member5.getUsername());
        coach2.setMembers(member6.getUsername());

        member4.setCoach(coach2.getUsername());
        member5.setCoach(coach2.getUsername());
        member6.setCoach(coach2.getUsername());

        // Add schedules and notifications for the new members
        member4.setSchedule("Aerobics: Monday and Thursday at 8 AM");
        member4.setNotifications("Wear light clothing!");
        member4.setRenewPrice("----------------------------"); // Set renewal price

        member5.setSchedule("Spin Class: Wednesday and Friday at 9 AM");
        member5.setNotifications("Bring a towel and water!");
        member5.setRenewPrice("----------------------------"); // Set renewal price

        member6.setSchedule("CrossFit: Saturday at 10 AM");
        member6.setNotifications("Be ready to push your limits!");
        member6.setRenewPrice("----------------------------"); // Set renewal price

        // Coach sends notifications to all their members
        sendNotificationsToMembers(coach1, "Stay motivated and keep up the great work!");
        sendNotificationsToMembers(coach2, "Don't forget to track your progress this week!");

        // Print details of all members
        System.out.println("Details of Members:");

        // Initial members
        printMemberDetails(member1);
        printMemberDetails(member2);
        printMemberDetails(member3);

        // New members
        printMemberDetails(member4);
        printMemberDetails(member5);
        printMemberDetails(member6);
        System.out.println();
        member1.printNotifications();
    }

    private static void printMemberDetails(Member member) {
        System.out.println("Username: " + member.getUsername());
        System.out.println("Coach: " + member.getCoach());
        System.out.println("Schedule: " + member.getSchedule());
        System.out.println("Notifications: " + member.getNotifications());
        System.out.println("Renewal Price: $" + member.getRenewPrice());
        System.out.println();
    }

    private static void sendNotificationsToMembers(Coach coach, String notification) {
        String[] members = coach.getMembersArray();
        for (String memberUsername : members) {
            coach.addNotificationToMember(memberUsername, notification); 
        }
    }
}
