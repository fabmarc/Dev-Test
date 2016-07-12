package goeuro.appclient.core;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class GoEuroTest {

	private final static Logger LOGGER = Logger.getLogger(GoEuroTest.class);

	public static void main(String[] args) {

		LOGGER.info("Started");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Parameter args = " + Arrays.toString(args));
		}

		AnnotationConfigApplicationContext context = null;

		try {
			context = new AnnotationConfigApplicationContext();
			context.register(AppClientConfig.class);
			context.refresh();

			AppClient appClient = (AppClient) context.getBean("mainRunner");
			appClient.run(args);

		} finally {
			close(context);
			LOGGER.info("Finished");
		}
	}

	private static void close(AbstractApplicationContext context) {
		if (context != null) {
			try {
				context.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

}
