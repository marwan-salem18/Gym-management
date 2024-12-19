
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    CardLayout cardLayout;
    JPanel cardPanel;
    Admin loggedInAdmin = null;
    Coach loggedInCoach = null;
    Member loggedInMember = null;

    public Main() {
        // Set up the main frame
        setTitle("User Registration and Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set up card layout for page navigation
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add pages (initialize panels with their content)
        cardPanel.add(new WelcomePage(), "Welcome");
        cardPanel.add(new RegistrationPage(), "Register");
        cardPanel.add(new LoginPage(), "Login");
        cardPanel.add(new AdminPanel(), "Admin");
        cardPanel.add(new MemberPanel(), "Member");
        cardPanel.add(new CoachPanel(), "Coach");

        // Add the cardPanel to the frame
        add(cardPanel);
        cardLayout.show(cardPanel, "Welcome");
    }

    private void getMYInfo(User user){
        try{
            String myUserName = user.getUsername();
            String password =   user.getPassword();
            JOptionPane.showMessageDialog(this, "Your username is: " + myUserName +" & your password is: "+password, "User Profile", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed To Retrieve Profile", JOptionPane.ERROR_MESSAGE);

        }
    }
    private void updateMyInfo(User user){
        try {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to update? " , "Update Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {

                String updatedUserName = JOptionPane.showInputDialog(this, "Enter your new username:");
                String updatedPassword = JOptionPane.showInputDialog(this, "Enter your new password:");

                if (!updatedPassword.isBlank() && !updatedPassword.trim().isEmpty()) {
                    try {
                        user.updatePassword( updatedPassword);
                        JOptionPane.showMessageDialog(this, "Your password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Password Update Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (!updatedUserName.isBlank() && !updatedUserName.trim().isEmpty()) {
                    try {
                        user.updateUsername(updatedUserName);
                        JOptionPane.showMessageDialog(this, "Your Username updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Username Update Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Utility method for creating styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setBorder(new LineBorder(new Color(30, 60, 90), 2, true));
        return button;
    }

    public static void main(String[] args) {
        UserManipulations.initailizeFiles();
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Welcome, Page with Login and Register options
    public class WelcomePage extends JPanel {

        public WelcomePage() {
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            setBorder(new LineBorder(Color.GRAY, 2, true));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20);

            JLabel title = new JLabel("Welcome!");
            title.setFont(new Font("Arial", Font.BOLD, 30));
            gbc.gridy = 0;
            add(title, gbc);

            JButton loginButton = createStyledButton("Login");
            loginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
            gbc.gridy = 1;
            add(loginButton, gbc);

            JButton registerButton = createStyledButton("Register");
            registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
            gbc.gridy = 2;
            add(registerButton, gbc);
        }
    }

    // Abstract form for Login and Registration
    public abstract class UserFormPanel extends JPanel {

        JTextField usernameField;
        JPasswordField passwordField;
        JComboBox<String> userTypeComboBox;
        String successMessage;
        String nextPage;

        public UserFormPanel(String titleText, String successMessage, String nextPage) {
            this.successMessage = successMessage;
            this.nextPage = nextPage;

            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            setBorder(new LineBorder(Color.GRAY, 2, true));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Title
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel titleLabel = new JLabel(titleText);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
            add(titleLabel, gbc);

            // Username field
            gbc.gridy++;
            add(new JLabel("Username"), gbc);
            gbc.gridy++;
            usernameField = new JTextField();
            usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
            usernameField.setPreferredSize(new Dimension(250, 40));
            usernameField.setBorder(new LineBorder(Color.GRAY, 1));
            add(usernameField, gbc);

            // Password field
            gbc.gridy++;
            add(new JLabel("Password"), gbc);
            gbc.gridy++;
            passwordField = new JPasswordField();
            passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
            passwordField.setPreferredSize(new Dimension(250, 40));
            passwordField.setBorder(new LineBorder(Color.GRAY, 1));
            add(passwordField, gbc);

            // User type dropdown
            gbc.gridy++;
            add(new JLabel("User Type"), gbc);
            gbc.gridy++;
            userTypeComboBox = new JComboBox<>(new String[]{"admin", "member", "coach"});
            userTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
            add(userTypeComboBox, gbc);

            // Submit button
            gbc.gridy++;
            JButton submitButton = createStyledButton(titleText);
            submitButton.addActionListener(e -> handleSubmit());
            add(submitButton, gbc);
        }

        protected abstract void handleSubmit();
    }

    // Registration Page
    public class RegistrationPage extends UserFormPanel {

        public RegistrationPage() {
            super("Register", "Registration Successful!", "Login");
        }

        @Override
        protected void handleSubmit() {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String userType = (String) userTypeComboBox.getSelectedItem();

            User user = null;

            switch (userType.toLowerCase()) {
                case "admin":
                    user = new Admin(username, password);
                    break;
                case "coach":
                    user = new Coach(username, password);
                    break;
                case "member":
                    user = new Member(username, password);
                    break;
            }

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                user.register();
                JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "Login");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
// Login Page

    public class LoginPage extends UserFormPanel {

        public LoginPage() {
            super("Login", "Login Successful!", null);
        }

        @Override
        protected void handleSubmit() {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String userType = (String) userTypeComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid username and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if(userType!=null){
                switch (userType) {
                    case "admin":
                        loggedInAdmin = new Admin(username, password);
                        loggedInAdmin.login(username, password);
                        break;
                    case "member":
                        loggedInMember = new Member(username, password);
                        loggedInMember.login(username, password);
                        break;
                    case "coach":
                        loggedInCoach = new Coach(username, password);
                        loggedInCoach.login(username, password);
                        break;
                    default:
                        System.out.println("Invalid user type");
                        break;
                }
                }

                usernameField.setText("");
                passwordField.setText("");
                userTypeComboBox.setSelectedIndex(0);

                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                switch (userType) {
                    case "admin":
                        cardLayout.show(cardPanel, "Admin");
                        break;
                    case "member":
                        cardLayout.show(cardPanel, "Member");
                        break;
                    case "coach":
                        cardLayout.show(cardPanel, "Coach");
                        break;
                }

            } catch (Exception e) {
                if (e.getMessage().equalsIgnoreCase("user is not registered")) {
                    int choice = JOptionPane.showConfirmDialog(this,
                            "User not found. Would you like to register?",
                            "Login Error",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (choice == JOptionPane.YES_OPTION) {
                        cardLayout.show(cardPanel, "Register");
                    }
                    return;
                }
                JOptionPane.showMessageDialog(this, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

// ADMIN PANEL
    public class AdminPanel extends JPanel {

        public AdminPanel() {

            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            add(new JLabel("Admin Panel", JLabel.CENTER), BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
            buttonPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

            JButton manageCoachesButton = createStyledButton("Manage Coaches");
            manageCoachesButton.addActionListener(e -> openManageCoachesDialog());

            JButton manageMembersButton = createStyledButton("Manage Members");
            manageMembersButton.addActionListener(e -> openManageMembersDialog());

            JButton manageBillingButton = createStyledButton("Manage Billing");
            manageBillingButton.addActionListener(e -> handleBillingToMembers());

            JButton sendNotificationsButton = createStyledButton("Members' Subscription End Date");
            sendNotificationsButton.addActionListener(e -> handleSendNotifications());
            
            JButton assignMembersButton = createStyledButton("Assign Members to Coaches");
            assignMembersButton.addActionListener(e -> handleAssignMembers());

            JButton makeReportButton = createStyledButton("Make Reports About Members");
            makeReportButton.addActionListener(e -> handleMakeReports());

            JButton logoutButton = createStyledButton("Logout");
            logoutButton.addActionListener(e -> handleLogout());

            JButton updateInfoButton = createStyledButton("Update Info");
            updateInfoButton.addActionListener(e -> updateMyInfo(loggedInAdmin));

            JButton myInfoButton = createStyledButton("My Info");
            myInfoButton.addActionListener(e -> getMYInfo(loggedInAdmin));

            JButton setEndDate = createStyledButton("Set end date for member");
            setEndDate.addActionListener(e -> handleSetDate());

            buttonPanel.add(manageCoachesButton);
            buttonPanel.add(manageMembersButton);
            buttonPanel.add(manageBillingButton);
            buttonPanel.add(setEndDate);
            buttonPanel.add(sendNotificationsButton);
            buttonPanel.add(assignMembersButton);
            buttonPanel.add(makeReportButton);
            buttonPanel.add(myInfoButton);
            buttonPanel.add(updateInfoButton);
            buttonPanel.add(logoutButton);

            add(buttonPanel, BorderLayout.CENTER);
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            return button;
        }

        private void openManageCoachesDialog() {
            JDialog manageDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Coaches", true);
            manageDialog.setSize(400, 300);
            manageDialog.setLocationRelativeTo(this);
            manageDialog.setLayout(new GridLayout(4, 1, 10, 10));

            JButton addCoachButton = createStyledButton("Add Coach");
            JButton deleteCoachButton = createStyledButton("Delete Coach");
            JButton listCoachesButton = createStyledButton("List Coaches");
            JButton searchCoachButton = createStyledButton("Search Coach");
            JButton updateCoachInfo = createStyledButton("Update Coach Info");

            addCoachButton.addActionListener(e -> handleAddCoach());
            deleteCoachButton.addActionListener(e -> handleDeleteCoach());
            listCoachesButton.addActionListener(e -> handleListCoaches());
            searchCoachButton.addActionListener(e -> handleSearchCoach());
            updateCoachInfo.addActionListener(e -> handleUpdateCoachInfo());

            manageDialog.add(addCoachButton);
            manageDialog.add(deleteCoachButton);
            manageDialog.add(listCoachesButton);
            manageDialog.add(searchCoachButton);
            manageDialog.add(updateCoachInfo);

            manageDialog.setVisible(true);
        }

        private void openManageMembersDialog() {
            JDialog manageDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Members", true);
            manageDialog.setSize(400, 300);
            manageDialog.setLocationRelativeTo(this);
            manageDialog.setLayout(new GridLayout(4, 1, 10, 10));

            JButton addMemberButton = createStyledButton("Add Member");
            JButton deleteMemberButton = createStyledButton("Delete Member");
            JButton listMembersButton = createStyledButton("List Members");
            JButton searchMemberButton = createStyledButton("Search Member");
            JButton updateMemberInfo = createStyledButton("Update Member Info");


            addMemberButton.addActionListener(e -> handleAddMember());
            deleteMemberButton.addActionListener(e -> handleDeleteMember());
            listMembersButton.addActionListener(e -> handleListMembers());
            searchMemberButton.addActionListener(e -> handleSearchMember());
            updateMemberInfo.addActionListener(e -> handleUpdateMemberInfo());


            manageDialog.add(addMemberButton);
            manageDialog.add(deleteMemberButton);
            manageDialog.add(listMembersButton);
            manageDialog.add(searchMemberButton);
            manageDialog.add(updateMemberInfo);

            manageDialog.setVisible(true);
        }

        // Coach management methods



        private void handleAddCoach() {
            String username = JOptionPane.showInputDialog(this, "Enter Coach Username:");
            if(username == null){
                return;
            }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String password = JOptionPane.showInputDialog(this, "Enter Coach Password:");
            if(password == null){
                return;
            }
            if (password.isBlank() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Coach coachUser = new Coach(username, password);
            try {
                coachUser.register();
                JOptionPane.showMessageDialog(this, "Coach added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);

            }

        }

        private void handleDeleteCoach() {
            String username = JOptionPane.showInputDialog(this, "Enter Coach Username to Delete:");
           if(username == null){
               return;
           }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                 loggedInAdmin.deleteUser(username, "coach");
                JOptionPane.showMessageDialog(this, "Coach deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        private void handleListCoaches() {
            try {

                List<String[]> coachList =  loggedInAdmin.getAllUserOfType("coach");
                ArrayList<String[]> coachArrayList = new ArrayList<>(coachList);

                StringBuilder coachStringBuilder = new StringBuilder("Registered Coaches:\n");

                for (String[] coach : coachArrayList) {
                    coachStringBuilder.append("ID: ").append(coach[0]).append("  Username: ").append(coach[1]).append("\n");
                }
                JOptionPane.showMessageDialog(this, coachStringBuilder.toString(), "Coach List: ", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Coach List", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleSearchCoach() {
            String username = JOptionPane.showInputDialog(this, "Enter Coach Username to Search:");
           if(username == null){
               return;
           }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

                String[] coachList =  loggedInAdmin.searchUser("coach", username);
                JOptionPane.showMessageDialog(this, "Coach found: Username: " + coachList[1] + " password: " + coachList[2] , "Search Result", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Coach not found with Username: " + username , "Search Result", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleUpdateCoachInfo(){
            try {

                String username = JOptionPane.showInputDialog(this, "Enter Coach Username you need to update:");
                if(username == null){
                    return;
                }
                if (username.isBlank() || username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] coachData = loggedInAdmin.searchUser("coach",username);
                //username[1],password[2]
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to update coach with username: " + coachData[1] + " and his password is: " + coachData[2] , "Update Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {

                    String updatedUserName = JOptionPane.showInputDialog(this, "Enter Coach new username:");
                    String updatedPassword = JOptionPane.showInputDialog(this, "Enter Coach new password:");

                    if (!updatedPassword.isBlank() && !updatedPassword.trim().isEmpty()) {
                        try {
                            loggedInAdmin.updateUserPassword("coach", username, updatedPassword);
                            JOptionPane.showMessageDialog(this, "Coach password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        }
                        catch (Exception e){
                            JOptionPane.showMessageDialog(this, e.getMessage(), "Password Update Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (!updatedUserName.isBlank() && !updatedUserName.trim().isEmpty()) {
                        try {
                            loggedInAdmin.updateUserUsername("coach", username, updatedUserName);
                            JOptionPane.showMessageDialog(this, "Coach Username updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        }catch (Exception e){
                            JOptionPane.showMessageDialog(this, e.getMessage(), "Username Update Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    }
              }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Member management methods
        private void handleAddMember() {
            String username = JOptionPane.showInputDialog(this, "Enter Member Username:");
           if(username == null){
               return;
           }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String password = JOptionPane.showInputDialog(this, "Enter Member Password:");
            if (password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String endDate = JOptionPane.showInputDialog(this, "Enter Member EndDate(after today and in format DD-MM-YYYY):");

            try {            

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate endDateFormatted = LocalDate.parse(endDate, formatter);
                LocalDate currentDate = LocalDate.now();

                if (endDate == null || endDate.trim().isEmpty() || endDateFormatted.isBefore(currentDate) || endDateFormatted.isEqual(currentDate)) {
                    JOptionPane.showMessageDialog(this, "EndDate should be in format DD-MM-YYYY and after TODAY", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Member memberUser = new Member(username, password, endDate);

                memberUser.register();
                JOptionPane.showMessageDialog(this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            }catch(DateTimeParseException el){
                JOptionPane.showMessageDialog(this, "EndDate should be in format DD-MM-YYYY", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);

            }
        }

    private void handleUpdateMemberInfo(){
        try {

            String username = JOptionPane.showInputDialog(this, "Enter Member Username you need to update:");
            if(username == null){
                return;
            }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] memberData = loggedInAdmin.searchUser("member",username);
            //username[1],password[2]
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to update member with username: " + memberData[1] + " and his password is: " + memberData[2] , "Update Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {

                String updatedUserName = JOptionPane.showInputDialog(this, "Enter Member new username:");
                String updatedPassword = JOptionPane.showInputDialog(this, "Enter Member new password:");

                if (!updatedPassword.isBlank() && !updatedPassword.trim().isEmpty()) {
                    try {
                        loggedInAdmin.updateUserPassword("member", username, updatedPassword);
                        JOptionPane.showMessageDialog(this, "Member password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Password Update Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (!updatedUserName.isBlank() && !updatedUserName.trim().isEmpty()) {
                    try {
                        loggedInAdmin.updateUserUsername("member", username, updatedUserName);
                        JOptionPane.showMessageDialog(this, "Member Username updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Username Update Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void handleDeleteMember() {
            String username = JOptionPane.showInputDialog(this, "Enter Member Username to Delete:");
           if(username == null){
               return;
           }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                 loggedInAdmin.deleteUser(username, "member");
                JOptionPane.showMessageDialog(this, "Member deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleListMembers() {
            try {

                List<String[]> coachList =  loggedInAdmin.getAllUserOfType("member");
                ArrayList<String[]> coachArrayList = new ArrayList<>(coachList);

                StringBuilder coachStringBuilder = new StringBuilder("Registered Members:\n");

                for (String[] coach : coachArrayList) {
                    coachStringBuilder.append("ID: ").append(coach[0]).append("  Username: ").append(coach[1]).append("\n");
                }
                JOptionPane.showMessageDialog(this, coachStringBuilder.toString(), "Member List: ", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Member List", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleSearchMember() {
            String username = JOptionPane.showInputDialog(this, "Enter Member Username to Search: ");
           if(username == null){
               return;
           }
            if (username.isBlank() || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

                String[] memberList =  loggedInAdmin.searchUser("member",username);
                JOptionPane.showMessageDialog(this, "Member found: Username: " + memberList[1] + " Password: " + memberList[2] + " & Coach: " +memberList[6], "Search Result", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Member not found." + e.getMessage(), "Search Result", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Assign members to coaches
        private void handleAssignMembers() {

            String memberUsername = JOptionPane.showInputDialog(this, "Enter Member Username to Assign:");
              if(memberUsername == null){
                  return;
              }
            if (memberUsername.isBlank() || memberUsername.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String coachUsername = JOptionPane.showInputDialog(this, "Enter Coach Username:");
           if(coachUsername == null ){
               return;
           }
            if (coachUsername.isBlank() || coachUsername.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Coach Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {


                 loggedInAdmin.assignCoach(coachUsername, memberUsername);
                JOptionPane.showMessageDialog(this, "Member with username " + memberUsername + " assigned to coach " + coachUsername + ".", "Assignment Successful", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Assignment Failed ", JOptionPane.ERROR_MESSAGE);
            }
        }


    private void handleBillingToMembers() {

        String memberUsername = JOptionPane.showInputDialog(this, "Enter Member Username to assign billing:");
        if(memberUsername==null){
            return;
        }
        if (memberUsername.isBlank() || memberUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String billingAmount = JOptionPane.showInputDialog(this, "Enter billing amount \" Should be in number format only\"");
       if(billingAmount==null){
           return;
       }
        if (billingAmount.isBlank() || billingAmount.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Billing amount should be in number format only", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int billingAmountInt = Integer.parseInt(billingAmount);

             loggedInAdmin.changeBill(billingAmountInt, memberUsername);
            JOptionPane.showMessageDialog(this, "Billing with amount " + billingAmountInt + " is assigned successfully for member with username " +memberUsername , "Billing Assigned successfully" , JOptionPane.INFORMATION_MESSAGE);

        } catch(NumberFormatException ne){
            JOptionPane.showMessageDialog(this, "Billing amount should be in number format only", "Billing Assignment Failed ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Assignment Failed ", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void handleLogout(){
            try {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                     loggedInAdmin.logout();
                      cardLayout.show(cardPanel, "Welcome");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Logout Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Generate reports about members
        private void handleMakeReports() {
        String memberUsername = JOptionPane.showInputDialog(this, "Enter Member Username To Make Report For:");
           if(memberUsername == null) {
               return;
           }
        if (memberUsername.isBlank()|| memberUsername.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Member Username cannot Be Empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String reportMsg = JOptionPane.showInputDialog(this, "Enter Report Message:");
        if(reportMsg == null) {
               return;
           }
        if (reportMsg.isBlank() || reportMsg.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Report Message cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

            try {

         loggedInAdmin.makeReport(reportMsg, memberUsername);
        JOptionPane.showMessageDialog(this, "Member with username " + memberUsername + " has a report  " + reportMsg + ".", "Report Assigned Successfully", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Assignment Failed ", JOptionPane.ERROR_MESSAGE);
    }
}


    private void handleSetDate()
    {
        String username = JOptionPane.showInputDialog(this, "Enter Member Username:");
        if(username == null){
            return;
        }
        if (username.isBlank() || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String endDate = JOptionPane.showInputDialog(this, "Enter Member EndDate(after today and in format DD-MM-YYYY):");

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate endDateFormatted = LocalDate.parse(endDate, formatter);
            LocalDate currentDate = LocalDate.now();



            if (endDate == null || endDate.trim().isEmpty() || endDateFormatted.isBefore(currentDate) || endDateFormatted.isEqual(currentDate)) {
                JOptionPane.showMessageDialog(this, "EndDate should be in format DD-MM-YYYY and after TODAY", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            loggedInAdmin.updateMemberEndDate(username, endDate);

            JOptionPane.showMessageDialog(this, "Member end date set!", "Success", JOptionPane.INFORMATION_MESSAGE);

        }catch(DateTimeParseException el){
            JOptionPane.showMessageDialog(this, "EndDate should be in format DD-MM-YYYY", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Setting date Error", JOptionPane.ERROR_MESSAGE);
        }
    }


        // Send notifications when subscription of member ends
        private void handleSendNotifications() {

           // Mock implementation assuming end of subscription is checked
           try {
                loggedInAdmin.notifyMemberEndDate();

                StringBuilder notifications = new StringBuilder("Notifications:\n\n");

                String[] notificationsArray = loggedInAdmin.getNotificationsArray();

                if (notificationsArray.length == 0)
                {
                    throw new IllegalArgumentException("No ended subscription for any user");
                }
                for (String member : notificationsArray) {
                    notifications.append(member).append("\n");
                }
                JOptionPane.showMessageDialog(this, notifications.toString(), "Notifications Sent", JOptionPane.INFORMATION_MESSAGE);
           } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Notification Failed To Send ", JOptionPane.ERROR_MESSAGE);
           }
           
       }

    }

    // Coach Panel
    public class CoachPanel extends JPanel {


        public CoachPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            add(new JLabel("Coach Panel", JLabel.CENTER), BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

            JButton addScheduleButton = createStyledButton("Add Schedule for Member");
            JButton sendMessageButton = createStyledButton("Send Message to Members");
            JButton updateInfoButton = createStyledButton("Update Info");
            JButton logoutButton = createStyledButton("Logout");


            addScheduleButton.addActionListener(e -> handleAddSchedule());
            sendMessageButton.addActionListener(e -> handleSendMessage());
            logoutButton.addActionListener(e -> handleLogout());
            updateInfoButton.addActionListener(e -> updateMyInfo(loggedInCoach));

            JButton myInfoButton = createStyledButton("My Info");
            myInfoButton.addActionListener(e -> getMYInfo(loggedInCoach));

            buttonPanel.add(addScheduleButton);
            buttonPanel.add(sendMessageButton);
            buttonPanel.add(myInfoButton);
            buttonPanel.add(updateInfoButton);
            buttonPanel.add(logoutButton);

            add(buttonPanel, BorderLayout.CENTER);
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            return button;
        }

        private void handleAddSchedule() {
            String memberUsername = JOptionPane.showInputDialog(this, "Enter Member Username:");
            if (memberUsername == null){
                return;
            }
            if (memberUsername.isBlank() || memberUsername.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String scheduleDetails = JOptionPane.showInputDialog(this, "Enter Schedule Details:");
            if (scheduleDetails == null){
                return;
            }
            if (scheduleDetails.isBlank()|| scheduleDetails.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Schedule Details cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                loggedInCoach.addScheduleToMember(memberUsername, scheduleDetails);
                JOptionPane.showMessageDialog(this, "Schedule added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Add Schedule Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleSendMessage() {
            String memberUsername = JOptionPane.showInputDialog(this, "Enter Member Username:");
            if(memberUsername == null){
                return;
            }
            if (memberUsername.isBlank()|| memberUsername.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member Username cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String message = JOptionPane.showInputDialog(this, "Enter Message to Send:");
            if (message == null){
                return;
            }
            if (message.isBlank() || message.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                loggedInCoach.addNotificationToMember(memberUsername,message);
                JOptionPane.showMessageDialog(this, "Message sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Send Message Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleLogout(){
            try {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    loggedInCoach.logout();
                    cardLayout.show(cardPanel, "Welcome");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Logout Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //// Member Panel
    public class MemberPanel extends JPanel {

        public MemberPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            add(new JLabel("Member Panel", JLabel.CENTER), BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            buttonPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

            JButton viewEndDateButton = createStyledButton("View Subscription End Date");
            JButton viewCoachButton = createStyledButton("View Assigned Coach");
            JButton viewScheduleButton = createStyledButton("View Schedule");
            JButton updateInfoButton = createStyledButton("Update Info");
            JButton logoutButton = createStyledButton("Logout");
            JButton myNotifications = createStyledButton("View Notifications");
            JButton myReports = createStyledButton("View Reports");
            JButton myInfoButton = createStyledButton("My Info");
            JButton viewBill = createStyledButton("View Bill");


            viewEndDateButton.addActionListener(e -> handleViewEndDate());
            viewCoachButton.addActionListener(e -> handleViewCoach());
            viewScheduleButton.addActionListener(e -> handleViewSchedule());
            logoutButton.addActionListener(e -> handleLogout());
            updateInfoButton.addActionListener(e -> updateMyInfo(loggedInMember));
            myInfoButton.addActionListener(e -> getMYInfo(loggedInMember));
            myNotifications.addActionListener(e -> handleViewNotifications());
            myReports.addActionListener(e -> getMYReports());
            viewBill.addActionListener(e -> handleViewBill());


            buttonPanel.add(viewEndDateButton);
            buttonPanel.add(viewCoachButton);
            buttonPanel.add(viewScheduleButton);
            buttonPanel.add(myReports);
            buttonPanel.add(myNotifications);
            buttonPanel.add(viewBill);
            buttonPanel.add(myInfoButton);
            buttonPanel.add(updateInfoButton);
            buttonPanel.add(logoutButton);

            add(buttonPanel, BorderLayout.CENTER);
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            return button;
        }

        private void handleViewEndDate() {
           try{
               String endDate = loggedInMember.getEndDate();
               if(endDate.equals("null")){
                   JOptionPane.showMessageDialog(this, "You didn't have subscription end date yet", "Subscription End Date", JOptionPane.INFORMATION_MESSAGE);
                    return;
               }
               JOptionPane.showMessageDialog(this, "Your subscription ends on: " + endDate, "Subscription End Date", JOptionPane.INFORMATION_MESSAGE);
           } catch (Exception e) {
               JOptionPane.showMessageDialog(this, e.getMessage(), "View End Date Failed", JOptionPane.ERROR_MESSAGE);
           }
        }

        private void handleViewBill() {
            try{
                String bill = loggedInMember.getRenewPrice();
                if(bill.equals("null")){
                    JOptionPane.showMessageDialog(this, "You don't have a renew bill yet", "Renew Bill", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Your renew bill is: " + bill, "Renew Bill", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "View bill Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void getMYReports() {
           try{
               String report = loggedInMember.getReport();
               JOptionPane.showMessageDialog(this, report, "View Report", JOptionPane.INFORMATION_MESSAGE);
           } catch (Exception e) {
               JOptionPane.showMessageDialog(this, e.getMessage(), "View Report Failed", JOptionPane.ERROR_MESSAGE);
           }
        }

        private void handleViewCoach() {
        try{
            String assignedCoach = loggedInMember.getCoach();
            if(assignedCoach.equals("null")){
                JOptionPane.showMessageDialog(this, "You didn't assign to any coach yet", "Assigned Coach", JOptionPane.INFORMATION_MESSAGE);
                return;}
            JOptionPane.showMessageDialog(this, "The Coach you assigned with is : " + assignedCoach, "Assigned Coach", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "You didn't Assigned to a Coach ", JOptionPane.ERROR_MESSAGE);
             }
        }

        private void handleViewSchedule() {

            try{

                if(loggedInMember.getScheduleArray() == null || loggedInMember.getScheduleArray().length == 0){
                    JOptionPane.showMessageDialog(this,  "You didn't have any schedule yet", "Member Schedule ", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String[] scheduleArray = loggedInMember.getScheduleArray();
                StringBuilder scheduleBuilder = new StringBuilder("Your schedule is:\n");
                for (int i = 0; i < scheduleArray.length; i++) {
                    scheduleBuilder.append(scheduleArray[i]).append("\n");
                }
                if(scheduleBuilder.toString().equals("Your schedule is:\n")){
                    JOptionPane.showMessageDialog(this,  "You didn't have any schedule yet", "Member Schedule ", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this,  scheduleBuilder, "Member Schedule ", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Member Schedule Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleLogout(){
            try {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    loggedInMember.logout();
                    cardLayout.show(cardPanel, "Welcome");
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "Logout Failed", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleViewNotifications() {
            try {
                StringBuilder notifications = new StringBuilder("Notifications:\n\n");

                if (loggedInMember.getNotificationsArray().length == 0 || loggedInMember.getNotificationsArray()==null)
                {
                    throw new IllegalArgumentException("You have no notifications");
                }

                String[] notificationsArray = loggedInMember.getNotificationsArray();

                for (String member : notificationsArray) {
                    notifications.append(member).append("\n");
                }
                JOptionPane.showMessageDialog(this, notifications.toString(), "Notifications ", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Notification Failed To Retrieve", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
