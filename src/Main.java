public class Main {
    public static void main(String[] args) {
        // DON'T DELETE ME
        UserManipulations.initailizeFiles();

        
        try{
            Member member1 = new Member("member1", "member1Password");
            member1.login("member1", "member1Password");
            Admin admin1 = new Admin("admin1", "admin1");
            System.out.println(member1.getEndDate());
            admin1.updateMemberEndDate("member1", "2-12-2029");
            member1.setEndDate("null");
            admin1.notifyMemberEndDate();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        Admin a = new Admin("dezz","nuts");
        a.addUser("hi","it's me","admin");
    }

}
