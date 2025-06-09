package vertXcrudTemplateAPI;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.sqlclient.Tuple;

public class ApiVerticle  extends AbstractVerticle{
	@Override
	public void start(Promise<Void> startPromise) {
		/*
		 * Adding headers for Cors
		 * 
		 * */
		Set<String> allowedHeaders = new HashSet<>();
		allowedHeaders.add("x-requested-with");
		allowedHeaders.add("Access-Control-Allow-Origin");
		allowedHeaders.add("origin");
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("accept");
		allowedHeaders.add("Authorization");
		
		Set<HttpMethod> allowedMethods = new HashSet<>();
		allowedMethods.add(HttpMethod.GET);
		allowedMethods.add(HttpMethod.POST);
	
		
		Router router = Router.router(vertx);
	    router.route().handler(BodyHandler.create());      // JSON body to Buffer
	    router.route().handler(CorsHandler.create("*")
	    		.allowedHeaders(allowedHeaders)
	    		.allowedMethods(allowedMethods));
	    
	    router.post("/recieveLog").handler(this::handleLog);
	    
	    
	    vertx.createHttpServer()
        .requestHandler(router)
        .listen(Integer.parseInt(config().getString("server_port")),config().getString("server_ip")
        		, res -> {
          if (res.succeeded()) {
            System.out.println("✔  CRUD API listening ...");
            startPromise.complete();
          } else {
            startPromise.fail(res.cause());
          }
        });
	    
	}
	
	
	private void handleLog(RoutingContext ctx) {
		@SuppressWarnings("deprecation")
		JsonObject body = ctx.getBodyAsJson();
	    String id  = body.getString("id");
	    String content = body.getString("content");
	    
	    ctx.response().setStatusCode(201)
		.end(new JsonObject().put("Message", "Recieved").encode());
	    
	}
}
