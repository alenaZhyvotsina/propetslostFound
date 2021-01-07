package propets.lostfound.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProPetsLostFoundConfiguration {
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
			.setSkipNullEnabled(true)
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE);
		
		return modelMapper;
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
