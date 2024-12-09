import java.io.*;
import java.util.Objects;

public class UserManipulations {
    protected static void AddUser(String usertype,String[] data) {
        String[] header;
        switch (usertype) {
            case "member" ->
                    header = new String[]{"ID", "Username", "Password", "endDate", "Schedule", "RenewPrice", "coach"};
            case "coach", "admin" -> header = new String[]{"ID", "Username", "Password"};
            case null, default -> {
                System.out.println("Not valid");
                return;
            }
        }
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
                try {
                    int id = Integer.parseInt((Objects.requireNonNull(readLastLine(usertype)))[0]);
                    id = id + 1;
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

            System.out.printf("%s.csv%n", usertype);

        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    public static String[] Login(String [] data){
        return null;
    }
    public static void replace(String[] data){
    }
    private static String[] readLastLine(String usertype){
        String lastLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader(String.format("%s.csv", usertype)))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                lastLine = currentLine;
            }
            return lastLine.split(",");
        } catch (IOException e) {
            return null;
        }
    }
    // Helper method to check if file is empty
    private static boolean isFileEmpty(String usertype) {
        java.io.File file = new java.io.File(String.format("%s.csv", usertype));
        return file.length() == 0;
    }
    private static String[] lookup(String[] dataFields,String[] data){
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (data[0].equals(values[1]) && data[1].equals(values[2])){
                    return values;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return null;
        }
        return null;
    }
}
