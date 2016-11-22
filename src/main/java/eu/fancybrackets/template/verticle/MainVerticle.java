package eu.fancybrackets.template.verticle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.google.inject.Guice;
import com.google.inject.Injector;

import eu.fancybrackets.template.guice.ConfigModule;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ResponseTimeHandler;

public class MainVerticle extends AbstractVerticle {
	Injector injector = null;
	private final static Logger LOG = Logger.getLogger(MainVerticle.class.getName());
	
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

		printDBVersion(injector.getInstance(DataSource.class));
	}

	protected void printDBVersion(DataSource ds) {
		vertx.executeBlocking(future -> {
			try (Connection conn = ds.getConnection()) {
				Record record = DSL.using(conn, SQLDialect.POSTGRES).fetchOne("select version();");
				future.complete(record.get(0));
			} catch (SQLException e) {
				future.fail(e);
				LOG.log(Level.SEVERE, "Unexpected exception", e);
				e.printStackTrace();
			}
		}, res -> {
			LOG.info(String.format("Connected to %s", res.result()));
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		injector.getInstance(DSLContext.class).close();
	}

}
