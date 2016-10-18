package eu.fancybrackets.template.handler;

import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jooq.DSLContext;

import com.google.inject.name.Named;

import eu.fancybrackets.template.guice.ConfigModule;

public class RonSwansonHandler {
	private final static Logger LOG = Logger.getLogger(RonSwansonHandler.class.getName());
	
	@Inject
	private HttpClient httpClient;
	
	@Inject
	@Named(ConfigModule.RON_SWANSON_API_URL)
	private URL apiURL;
	
	@Inject 
	private DSLContext jooq;
	
	public void handleGet(RoutingContext event) {
		httpClient.getNow(apiURL.getPort()==-1?apiURL.getDefaultPort():apiURL.getPort(), apiURL.getHost(), apiURL.getFile(), response -> {
			response.bodyHandler(buffer -> {
				LOG.fine(String.format("Response (buffer):\n %s", buffer));
				JsonArray jsonArray = buffer.toJsonArray();
				event.response().setChunked(true);
				event.response().write(jsonArray.iterator().next().toString()).end();
			});
		});
	}
	
	public void handlePost(RoutingContext event) {
		//TODO insert a record
	}
}
