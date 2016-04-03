public class Main {
    public static Database database;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        database = new Database();
        database.withdraw(987654321, 100, 1); // DEBUG
        database.auth(987654321,1234);
    }
}
