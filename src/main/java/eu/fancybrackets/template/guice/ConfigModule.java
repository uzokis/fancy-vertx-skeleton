package eu.fancybrackets.template.guice;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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
}
