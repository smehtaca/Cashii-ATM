import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.*;

public class Main {
	  public static void main(String[] args) {
	        get("/login/:uid/:pass", new Route() {
	            @Override
	            public Object handle(Request request, Response response) {
	            	int uid = Integer.parseInt(request.params(":uid"));
	            	int pass = Integer.parseInt(request.params(":pass"));
	                return Database.auth(uid,pass);
	            }
	        });
	    }
	  
	  
	 	  
	  
	  
}

	