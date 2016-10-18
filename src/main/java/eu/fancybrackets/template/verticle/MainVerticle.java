package eu.fancybrackets.template.verticle;

import org.jooq.DSLContext;
import org.jooq.Record;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.fancybrackets.template.guice.ConfigModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseTimeHandler;

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

		vertx.createHttpServer().requestHandler(router::accept).listen(8081);

		printDBVersion(injector.getInstance(DSLContext.class));
	}

	protected void printDBVersion(DSLContext dsl) {
		vertx.executeBlocking(future -> {
			Record fetchOne = dsl.fetchOne("select version();");
			future.complete(fetchOne.get(0));
		}, res -> {
			System.out.println("Connected to: " + res.result());
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		injector.getInstance(DSLContext.class).close();
	}

}
