package by.itransition.service.user;

/**
 * Contract password generating strategy type.
 */
public interface PasswordGenerator {
	/**
	 * Generate password.
	 * @return
	 */
	String generate();
}
