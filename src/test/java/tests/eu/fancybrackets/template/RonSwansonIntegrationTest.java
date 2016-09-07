package tests.eu.fancybrackets.template;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import eu.fancybrackets.template.guice.ConfigModule;
import eu.fancybrackets.template.verticle.MainVerticle;

//If ran from studio add -Djava.util.logging.config.file=logger.properties for fine logging
@RunWith(VertxUnitRunner.class)
public class RonSwansonIntegrationTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().port(8089).httpsPort(8443));

	// @Rule
	// public RunTestOnContext rule = new RunTestOnContext();

	private Vertx vertx;
	private Injector testInjector;

	@Inject
	@Named("TestHttpClient")
	private HttpClient httpClient;

	@Before
	public void setup(TestContext ctx) throws IOException {
		vertx = Vertx.vertx();

		JsonObject testConfig = new JsonObject().put(ConfigModule.RON_SWANSON_API_URL, "http://localhost:8089/v2/quotes");

		Module testModule = Modules.override(new ConfigModule(vertx, testConfig)).with(new GuiceTestModule(vertx, testConfig));
		testInjector = Guice.createInjector(testModule);
		testInjector.injectMembers(this);

		DeploymentOptions options = new DeploymentOptions().setConfig(testConfig);
		vertx.deployVerticle(MainVerticle.class.getName(), options, ctx.asyncAssertSuccess());
	}

	@Test
	public void exampleTest(TestContext ctx) {
		wireMockRule.stubFor(get(urlMatching(".*")).willReturn(aResponse().proxiedFrom("http://ron-swanson-quotes.herokuapp.com")));

		Async async = ctx.async();
		httpClient.getNow(8080, "localhost", "/swanson", response -> {
			ctx.assertEquals(200, response.statusCode());
			response.bodyHandler(bodyHandler -> {
				async.complete();
			});
		});
		async.awaitSuccess();
		wireMockRule.verify(getRequestedFor(urlEqualTo("/v2/quotes")));
	}

}
