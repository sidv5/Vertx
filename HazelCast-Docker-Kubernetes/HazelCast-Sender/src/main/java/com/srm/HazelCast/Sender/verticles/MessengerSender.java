package com.srm.HazelCast.Sender.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MessengerSender extends AbstractVerticle {
	Logger log = LoggerFactory.getLogger(MessengerSender.class);
	final int port = 8080; 
	@Override
	public void start(Future<Void> future) {
		final Router router = Router.router(vertx);
		router.get("/").blockingHandler(rC -> {
			rC.response().end("Test!");
		});
		router.get("/sendForAll/:message").handler(this::sendMessageForAllReceivers);
		vertx.createHttpServer().requestHandler(router).listen(port, result -> {
			if (result.succeeded()) {
				log.info("HTTP server running on port : "+ port);
				future.complete();
			} else {
				log.error("Could not start a HTTP server", result.cause());
				future.fail(result.cause());
			}
		});
	}

	private void sendMessageForAllReceivers(RoutingContext routingContext) {
//		final EventBus eventBus = vertx.eventBus();
		final String message = routingContext.request().getParam("message");
		System.out.println(message);
		//eventBus.publish("news.feed", message);
		getVertx().eventBus().send("news.feed", message, reply -> {
			if(reply.succeeded()) {
				log.info(reply.result().body().toString());
				routingContext.response().end(reply.result().body().toString());
			}else {
				routingContext.response().end("No Reply!");
			}
		});
		log.info("Current Thread Id " + Thread.currentThread().getId() + " Is Clustered : " + vertx.isClustered());
	}
}
