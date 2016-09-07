package eu.fancybrackets.template.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseTimeHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.fancybrackets.template.guice.ConfigModule;

public class MainVerticle extends AbstractVerticle {
	Injector injector = null;

	public MainVerticle() {
		super();
	}
	
	@Override
	public void start() throws Exception {
		super.start();
		injector = Guice.createInjector(new ConfigModule(vertx, this.config()));
		
		Router router = injector.getInstance(Router.class);
		router.route().handler(ResponseTimeHandler.create());
		
		vertx.deployVerticle(injector.getInstance(RonSwansonVerticle.class));

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		//injector.getInstance(Session.class).getCluster().close();
		//TODO stop cassandra cluster client
	}

}
