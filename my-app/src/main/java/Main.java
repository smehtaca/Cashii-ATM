import static spark.Spark.*;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

/**
 * PLEASE READ FIRST
 * Main utilizes the SparkJava API found here: http://sparkjava.com/
 * This program mainly makes use of the get command. Each contains a
 * command i.e /withdraw, /deposit. Anything with a colon in it first
 * means it's an input that the front-end website uses to pass to the
 * back-end. The back-end functions are then utilized and the user is
 * re-directed accordingly.
 */
public class Main {
    public static int uid;


    public static void main(String[] args) {
        // Init the back-end database, SQL connections are made here
        final Database database = new Database();

        Spark.externalStaticFileLocation("/"); // temp fix, put site into root directory of system i.e C:/
        // Explanation: The StaticFileLocation command of this API is broken in this version.
        // Spark.staticFileLocation("/"); // needs to be fixed by demo

        database.connect();

        // Login uses the auth function where UID and pass are passed into the
        // back-end auth function where the password is checked, if it returns a
        // 1 that means the UID and password are correct
        get("/login/:uid/:pass", new Route() {
            public Object handle(Request request, Response response) {
                uid = Integer.parseInt(request.params(":uid"));
                int pass = Integer.parseInt(request.params(":pass"));

                if (database.auth(uid, pass) == 1)
                    response.redirect("/site/html/main.html");
                else
                    return "Invalid password, please go back and try again!";

                return null;
            }
        });

        // Deposit takes in the amount from the front-end and the account type
        get("/deposit/:amount/:accountType", new Route() {
            public Object handle(Request request, Response response) {
                int amount = Integer.parseInt(request.params(":amount"));
                int accountType = Integer.parseInt(request.params(":accountType"));
                database.deposit(uid, amount, accountType);
                response.redirect("/site/html/main.html"); // redirect after deposit is done
                return null;
            }
        });

        // Withdraw does a similar thing but forwards the user to a message if they're trying
        // to withdraw too much
        get("/withdraw/:amount/:accountType", new Route() {
            public Object handle(Request request, Response response) {
                int amount = Integer.parseInt(request.params(":amount"));
                int accountType = Integer.parseInt(request.params(":accountType"));

                // If the result is -1 it means the amount being withdrawn is too big
                if (database.withdraw(uid, amount, accountType) == -1)
                    return "The amount you are trying to withdraw is greater than your balance, " +
                            "please go back and choose a smaller amount.";
                else // otherwise it has succeeded and will go back to the main pg
                    response.redirect("/site/html/main.html");
                return null;
            }
        });

        // Prints the account statement
        get("/accountprint", new Route() {
            public Object handle(Request request, Response response) {
                response.type("text/html");
                return database.getAccountStatement(uid);
            }
        });


    }


}

	