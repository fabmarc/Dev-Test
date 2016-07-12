package goeuro.appclient.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "goeuro.appclient.core")
public class AppClientConfig {

	@Bean
	AppClient mainRunner() {
		return new AppClient();
	}

}
