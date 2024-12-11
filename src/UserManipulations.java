import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserManipulations {
    // adds a new user
    public static void AddHeader(String usertype) {
        String[] header;
        //check usertype
        switch (usertype) {
            case "member" ->
                    header = new String[]{"ID", "Username", "Password", "endDate", "Schedule", "RenewPrice", "coach","notifications"};
            case "coach" ->
                    header = new String[]{"ID", "Username", "Password", "members"};
            case "admin" -> header = new String[]{"ID", "Username", "Password", "Notification"};
            case null, default -> {
                System.out.println("Not valid");
                return;
            }
        }
        //open a new writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s.csv", usertype), true))) {
            
            for (String field : header) {
                bw.write(field);
                bw.write(",");
            }
            bw.newLine();
            switch (usertype) {
                case "member" -> bw.write("3000");
                case "admin" -> bw.write("1000");
                case "coach" -> bw.write("2000");
            }
            bw.write(",");
            bw.newLine();
        
        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // initializes all our csv files with the headers and the first id 
    // SHOULD ALWAYS BE IN THE BEGINNING OF YOUR MAIN FUNCTION
    public static void initailizeFiles()
    {
        String[] validTypes = {"admin", "member", "coach"};
        for (String usertype : validTypes)
        {
            boolean fileEmpty = isFileEmpty(usertype);
            if (fileEmpty) {
                AddHeader(usertype);
            }
        }
    }
        
    protected static void AddUser(String usertype,String[] data) {

        //open a new writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("%s.csv", usertype), true))) {
            // Check if file is empty (write header only once)
            boolean fileEmpty = isFileEmpty(usertype);
            if (fileEmpty) {
                System.out.println("please initialize headers");
            }
            else {
                //checks uniqueness
                if (!isUnique("admin", data[0])){
                    System.out.println("username already used");
                    return;
                }
                else if (!isUnique("member", data[0])){
                    System.out.println("username already used");
                    return;
                }
                else if (!isUnique("coach", data[0])){
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
                
                // the first line of the csv file is always "1," only with no username so if the array is out of bound then just skip to the next line
                try{
                    //checks if username are equal
                    if (Username.equals(values[1])){
                        return values;
                    }
                }
                catch(Exception ArrayIndexOutOfBoundsException) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return null;
        }
        return null;
    }


    public static int lineLookup(String usertype,String Username){
        //opens a new reader
        try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", usertype)))) {
            String line;
            //line starts at 0
            int linenumber = 0;

            while ((line = br.readLine()) != null) {
                //increase line number and checks the file for it's username
                linenumber++;
                
                String[] values = line.split(",");

                // the first line of the csv file is always "1," only with no username so if the array is out of bound then just skip to the next line
                try{
                    if (Username.equals(values[1])){
                        return linenumber;
                    }
                }
                catch(Exception ArrayIndexOutOfBoundsException) {
                    continue;
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
        // the first line of the csv file is always "1," only with no username so if the array is out of bound then just skip to the next line
        try{
            lines.set(targetLine - 1, content); // targetLine is 1-based
        }
        catch(Exception ArrayIndexOutOfBoundsException) {
            return;
        }

        // Write back the modified content
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Updated file at line " + targetLine);
    }

    
    public static boolean isUnique(String usertype,String username){
        return lookup(usertype, username) == null;
    }


    protected static List<String[]> getAllUsers(){
        String[] usertype = {"admin","coach","member"};
        // stores the users in an linked list of an array
        List<String[]> Users = new ArrayList<>();
        String line;
        //loops over all types of users
        for (int i = 0; i < 3; i++) {
            // j to start from line 3 instead of 1
            int j = 0;
            String currentUser = usertype[i];
            //opens a reader
            try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", currentUser  )))) {
                //reads line by line and stores them
                while ((line = br.readLine()) != null){
                    if (j > 1){
                        String[] values = line.split(",");
                        Users.add(values);
                    }
                    else {
                        j++;
                    };
                }
            } catch (IOException e) {
                System.err.println("Error reading CSV file: " + e.getMessage());
            }
        }
        return Users;
    }


    protected static void DeleteLine(String usertype,int targetLine){
        String filePath = String.format("%s.csv", usertype); // Path to your CSV file
        String tempFile = "temp.csv"; // Temporary file to store the updated data
        try {
            // Read the file and store rows in a list
            List<String> rows = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int currentRow = 1;
            while ((line = reader.readLine()) != null) {
                // Add to rows if it's not the row to delete
                if (currentRow != targetLine) {
                    rows.add(line);
                }
                currentRow++;
            }
            reader.close();

            // Write the updated data to the temporary file
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            for (String row : rows) {
                writer.write(row);
                writer.newLine();
            }
            writer.close();

            // Replace the original file with the temporary file
            File originalFile = new File(filePath);
            File temp = new File(tempFile);

            if (originalFile.delete() && temp.renameTo(originalFile)) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("Error occurred while deleting the row.");
            }
        } catch (IOException e) {
            System.out.println("erorr deleting line");
        }
    }
}
