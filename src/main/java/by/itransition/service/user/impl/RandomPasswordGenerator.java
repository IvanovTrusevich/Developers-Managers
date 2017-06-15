package by.itransition.service.user.impl;

import by.itransition.service.user.PasswordGenerator;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Just a simple implementations of {@link PasswordGenerator}. Always return
 * same static string every password generation.
 */
@Component
public class RandomPasswordGenerator implements PasswordGenerator {
	@Override
	public String generate() {
		return RandomString.make();
	}
}
