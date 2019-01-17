package com.srm.HazelCast.Receiver.main;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.srm.HazelCast.Receiver.verticles.MessengerReceiver;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Launcher {
	static Logger log = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		
		Config config = new Config();
		
		NetworkConfig network = config.getNetworkConfig();
        network.getInterfaces().setEnabled(true).addInterface("127.0.0.*");
        network.setPort(5701);
        JoinConfig join = network.getJoin();
        join.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1:5701").addMember("127.0.0.1:5702");
        join.getMulticastConfig().setEnabled(false);
		final ClusterManager mgr = new HazelcastClusterManager(config);
		
		final VertxOptions options = new VertxOptions().setClusterManager(mgr).setClusterHost("127.0.0.1");
		Vertx.clusteredVertx(options, cluster -> {
			if (cluster.succeeded()) {
				cluster.result().deployVerticle(MessengerReceiver.class.getName(),
						new DeploymentOptions().setInstances(5).setWorker(true), res -> {
							if (res.succeeded()) {
								log.info("Deployment id is: " + res.result());
							} else {
								log.error("Deployment failed!");
							}
						});
			} else {
				log.error("Cluster up failed: " + cluster.cause());
			}
		});
	}
}
