import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

public class Main {
	  public static void main(String[] args)
      {
		  final Database database = new Database();

		  database.connect();

	        get("/login/:uid/:pass", new Route() {
	            public Object handle(Request request, Response response) {
	            	int uid = Integer.parseInt(request.params(":uid"));
	            	int pass = Integer.parseInt(request.params(":pass"));
	                return database.auth(uid,pass);
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

	    }
	  
	  
	 	  
	  
	  
}

	