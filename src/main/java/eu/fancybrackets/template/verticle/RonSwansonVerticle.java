package eu.fancybrackets.template.verticle;

import javax.inject.Inject;

import eu.fancybrackets.template.handler.RonSwansonHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class RonSwansonVerticle extends AbstractVerticle {
	@Inject
	private Router router;
	
	@Inject
	private RonSwansonHandler ronSwansonHandler;
	
	@Override
	public void start() throws Exception {
		super.start();
		this.router.route("/swanson").method(HttpMethod.GET).handler(ronSwansonHandler::handleGet);
		this.router.route("/swanson").method(HttpMethod.POST).handler(ronSwansonHandler::handlePost);
	}
}
