package com.david.ds.teles.springboot.seed.config;

import java.io.File;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * testcontainer configuration to start a docker-compose with needed services
 *
 * @author davidteles
 *
 */
public class TestContainersConfig
	implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	Logger log = LoggerFactory.getLogger(TestContainersConfig.class);

	/**
	 * for some reason testcontainers doesn't look to exposed port, but to inner
	 * container port.
	 */
	static final int DB_INNER_PORT = 3306;
	static final String DB_SERVICE = "mysql";

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		try {
			DockerComposeContainer<?> testComposer = new DockerComposeContainer<>(
				new File("src/test/resources/test-docker-compose.yml")
			);

			testComposer.withExposedService(
				DB_SERVICE,
				DB_INNER_PORT,
				Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30))
			);

			testComposer.start();

			String host = testComposer.getServiceHost(DB_SERVICE, DB_INNER_PORT);
			int port = testComposer.getServicePort(DB_SERVICE, DB_INNER_PORT);

			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
				applicationContext,
				"spring.datasource.url=jdbc:mysql://" + host + ":" + port + "/spring"
			);
		} catch (Throwable e) {
			log.error("failed to start test container", e);
			throw e;
		}
	}
}
