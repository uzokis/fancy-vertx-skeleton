package eu.fancybrackets.template.guice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class ConfigModule extends AbstractModule {
	public static final String RON_SWANSON_API_URL = "RonSwansonAPIURL";
	private final Vertx vertx;
	private final Context context;
	private final JsonObject config;

	public ConfigModule(Vertx vertx, JsonObject config) throws IOException {
		this.vertx = vertx;
		if (config == null || config.isEmpty()) {
			//falling back on internal config if vertx started without -conf argument
			try (final InputStream is = getClass().getResourceAsStream("/config.json")) {
				this.config = Buffer.factory.buffer(CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8))).toJsonObject();
			}
		} else {
			this.config = config;
		}
		this.context = vertx.getOrCreateContext();
	}

	@Override
	protected void configure() {
		bind(Vertx.class).toInstance(vertx);
		// TODO your configuration
	}

	@Provides
	@Singleton
	Router provideRouter() {
		Router router = Router.router(vertx);
		return router;
	}

	@Provides
	@Singleton
	HttpClient getHttpClient() {
		return vertx.createHttpClient();
	}

	@Provides
	@Singleton
	@Named(RON_SWANSON_API_URL)
	public URL RonSwansonAPIURL() throws MalformedURLException {
		return new URL(config.getString(RON_SWANSON_API_URL));
	}
	
	@Provides
	@Singleton
	public DataSource provideDataSource() {
		//TODO move to config
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/skeleton");
		config.setUsername("postgres");
		config.setPassword("");
		
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	
	@Provides
	@Inject
	public DSLContext provideDSLContext(DataSource ds) {
		return DSL.using(ds, SQLDialect.POSTGRES);
	}
}
