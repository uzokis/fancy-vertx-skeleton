package tests.eu.fancybrackets.template;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;

import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class GuiceTestModule extends AbstractModule {
	protected final Vertx vertx;
	private final Context context;
	private JsonObject config;
	
	public GuiceTestModule(Vertx vertx, JsonObject config) {
		this.vertx = vertx;
		this.config = config;
		this.context = vertx.getOrCreateContext();
	}
	
	@Override
	protected void configure() {
		// TODO test specific configurations
	}

	@Provides
	@Named("TestHttpClient")
	private HttpClient getSyncHttpClient() {
		return vertx.createHttpClient(new HttpClientOptions().setDefaultPort(8080));
	}
}
