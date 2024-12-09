import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserManipulations {
    // adds a new user
    protected static void AddUser(String usertype,String[] data) {
        String[] header;
        //check usertype
        switch (usertype) {
            case "member" ->
                    header = new String[]{"ID", "Username", "Password", "endDate", "Schedule", "RenewPrice", "coach","notifications"};
            case "coach", "admin" -> header = new String[]{"ID", "Username", "Password"};
            case null, default -> {
                System.out.println("Not valid");
                return;
            }
        }
        //open a new writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s.csv", usertype), true))) {
            // Check if file is empty (write header only once)
            boolean fileEmpty = isFileEmpty(usertype);
            if (fileEmpty) {
                for (String field : header) {
                    bw.write(field);
                    bw.write(",");
                }
                bw.newLine();
                bw.write("1");
                bw.write(",");
            }
            else {
                //checks uniqueness
                if (isUnique(usertype, data[0])){
                    System.out.println("username already used");
                    return;
                }
                try {
                    // gets the latest id and gets id
                    int id = Integer.parseInt((Objects.requireNonNull(readLastLine(usertype)))[0]);
                    id++;
                    bw.write(String.format("%d", id));
                    bw.write(",");
                }
                catch (NumberFormatException e){
                    System.out.println("error reading data");
                    return;
                }

            }
            // Write data
            for (String cell : data) {
                bw.write(cell);
                bw.write(",");
            }
            bw.newLine();

        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    public static String[] Login(String [] data){
        return null;
    }
    public static void update(String usertype,String[] data){
    }
    private static String[] readLastLine(String usertype){
        String lastLine = "";
        //opens a new reader
        try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", usertype)))) {
            String currentLine;
            //checks if the next line is null
            while ((currentLine = br.readLine()) != null) {
                lastLine = currentLine;
            }
            //spilt it into an array
            return lastLine.split(",");
        } catch (IOException e) {
            return null;
        }
    }
    // Helper method to check if file is empty
    private static boolean isFileEmpty(String usertype) {
        //checks if the file is empty
        java.io.File file = new java.io.File(String.format("%s.csv", usertype));
        return file.length() == 0;
    }
    public static String[] lookup(String usertype,String Username){
        //opens a new reader
        try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", usertype)))) {
            String line;
            while ((line = br.readLine()) != null) {
                //turns the file values into an array
                String[] values = line.split(",");
                //checks if username are equal
                if (Username.equals(values[1])){
                    return values;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return null;
        }
        return null;
    }
    public static int lineLookup(String usertype,String Username){
        //opns a new reader
        try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", usertype)))) {
            String line;
            //line starts at 0
            int linenumber = 0;
            while ((line = br.readLine()) != null) {
                //increase line number and checks the file for it's username
                linenumber++;
                String[] values = line.split(",");
                if (Username.equals(values[1])){
                    return linenumber;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return 0;
        }
        return 0;
    }
    public static void updater(String usertype,String[] newContent,int targetLine){
        // Define file path
        Path filePath = Paths.get(String.format("%s.csv", usertype));
        // Read existing content
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Ensure the file has enough lines
        while (lines.size() < targetLine) {
            lines.add(""); // Add empty lines if needed
        }
        String content = String.join(",", newContent);
        // Modify the specific line
        lines.set(targetLine - 1, content); // targetLine is 1-based

        // Write back the modified content
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Updated file at line " + targetLine);
    }
    public static boolean isUnique(String usertype,String username){
        return lookup(usertype, username) != null;
    }
}
