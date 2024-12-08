import java.io.*;
public class UserManipulations {
    protected static void AddUser(String usertype,String[] data) {
        String[] header;
        if (usertype == "member"){
            header = new String[]{"ID", "Username", "Password","endDate","Schedule","RenewPrice","coach"};
        }
        else if (usertype == "coach"){
            header = new String[]{"ID", "Username", "Password"};
        }
        else if (usertype == "admin"){
            header = new String[]{"ID", "Username", "Password"};
        }
        else {
            System.out.println("Not vaild");
            return;
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
                String id = (readLastLine())[0];
                bw.write(id + 1);
                bw.write(",");
            }
            // Write data
            for (String cell : data) {
                bw.write(cell);
                bw.write(",");
            }
            bw.newLine();

            System.out.println("Data appended to " + "users.csv");

        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
    public static String[] Login(String [] data){
        return null;
    }
    public static void replace(String[] data){
    }
    private static String[] readLastLine(){
        String lastLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
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
                String[] values = line.split("و");
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
