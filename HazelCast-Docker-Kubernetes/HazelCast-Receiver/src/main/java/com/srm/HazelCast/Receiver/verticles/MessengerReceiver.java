package com.srm.HazelCast.Receiver.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MessengerReceiver extends AbstractVerticle {
	Logger log = LoggerFactory.getLogger(MessengerReceiver.class);

	@Override
	public void start() {
//		final EventBus eventBus = vertx.eventBus();
		getVertx().eventBus().consumer("news.feed", receivedMessage -> {
			log.debug("Received message: {} ", receivedMessage.body());
			System.out.println(receivedMessage.body());
			receivedMessage.reply("Hello "+receivedMessage.body());
		});
		log.info("Receiver ready!");
	}
}
