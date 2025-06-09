package vertXcrudTemplateAPI;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Main {
	public static void main(String[] args) {
		String server_ip=args[0];
		String server_port=args[1];
		
		// Input parameters to be passed to verticle via JSON
		
		JsonObject objIn = new JsonObject().put("server_ip", server_ip)
				.put("server_port",server_port);
		
		DeploymentOptions opts = new DeploymentOptions().setConfig(objIn);
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new ApiVerticle(),opts,res->{
			if(res.succeeded()) {
				System.out.println("API Deployed.......");
			}else {
				System.err.println("Deployment failed: "+res.cause());
				res.cause().printStackTrace();
			}
		});
		
	}

}
