public class Main {
    public static void main(String[] args) {
        String[] data = UserManipulations.lookup("admin","hi");
        data[2] = "abdallah";
        int line = UserManipulations.lineLookup("admin","hi");
        UserManipulations.updater("admin",data,line);
    }
}