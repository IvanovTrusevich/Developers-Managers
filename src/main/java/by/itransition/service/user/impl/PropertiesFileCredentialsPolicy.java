package by.itransition.service.user.impl;

import by.itransition.service.user.CredentialsPolicy;
import by.itransition.service.user.PasswordGenerator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Sample {@link CredentialsPolicy} implementation which read policy items from
 * properties.file (In conjunction with Spring's {@link PropertySource}).
 */
@Component
@Configurable
@Scope("prototype")
public class PropertiesFileCredentialsPolicy implements CredentialsPolicy {
	@Value("${by.itransition.service.user.credential.alwaysGenerateOnRegistration}")
	private boolean alwaysGenerateOnRegistration;
	
	@Value("${by.itransition.service.user.credential.defaultPasswordGeneratorName}")
	private Class<PasswordGenerator> passwordGeneratorType;
	
	@Override
	public boolean alwaysGenerateOnRegistration() {
		return alwaysGenerateOnRegistration;
	}

	@Override
	public Class<PasswordGenerator> defaultPasswordGeneratorType() {
		return passwordGeneratorType;
	}
}
