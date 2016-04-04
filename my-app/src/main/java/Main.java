import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

public class Main {
	  public static void main(String[] args)
      {
		  final Database database = new Database();
          Spark.externalStaticFileLocation("/"); // temp fix, put site into root
         // Spark.staticFileLocation("/"); // needs to be fixed by demo

          database.connect();

	        get("/login/:uid/:pass", new Route() {
	            public Object handle(Request request, Response response) {
	            	int uid = Integer.parseInt(request.params(":uid"));
	            	int pass = Integer.parseInt(request.params(":pass"));

                    if (database.auth(uid,pass) == 1)
                        response.redirect("/site/index.html");
                    else
                        return "Invalid password, please go back and try again!";

                    return null;
	            }
	        });

          get("/deposit/:uid/:amount/:accountType", new Route() {
              public Object handle(Request request, Response response)
              {
                  int uid = Integer.parseInt(request.params(":uid"));
                  int amount = Integer.parseInt(request.params(":amount"));
                  int accountType = Integer.parseInt(request.params(":accountType"));
                  return database.deposit(uid,amount,accountType);
              }
          });

          get("/withdraw/:uid/:amount/:accountType", new Route() {
              public Object handle(Request request, Response response)
              {
                  int uid = Integer.parseInt(request.params(":uid"));
                  int amount = Integer.parseInt(request.params(":amount"));
                  int accountType = Integer.parseInt(request.params(":accountType"));
                  return database.withdraw(uid,amount,accountType);
              }
          });

          get("/accountprint/:uid/", new Route() {
              public Object handle(Request request, Response response)
              {
                  int uid = Integer.parseInt(request.params(":uid"));

                  return database.getAccountStatement(uid);
              }
          });
          
          
          
          
	    }
	  
	  
	 	  
	  
	  
}

	