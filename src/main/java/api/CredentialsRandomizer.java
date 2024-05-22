package api;

import org.apache.commons.lang3.RandomStringUtils;

public class CredentialsRandomizer {

    public static User getNewRandomUser() {
        String randomUsername = generateRandomUsername();
        String randomEmail = generateRandomEmail();
        String randomPassword = generateRandomPassword();

        return new User(randomEmail, randomPassword, randomUsername);
    }

    private static String generateRandomUsername() {
        return "user_" + RandomStringUtils.randomAlphanumeric(8);
    }

    private static String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(6).toLowerCase() + "@example.com";
    }

    private static String generateRandomPassword() {
        return RandomStringUtils.random(10, true, true);
    }
}
