package pageObject;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return new LoginPage(driver);
    }

    public MainPage getMainPage() {
        return new MainPage(driver);
    }

    public RegistrationPage getRegistrationPage() {
        return new RegistrationPage(driver);
    }

    public PasswordRecoveryPage getPasswordRecoveryPage() {
        return new PasswordRecoveryPage(driver);
    }

    public ProfilePageTestImproved getProfilePageTestImproved() {
        return new ProfilePageTestImproved(driver);
    }

    public UserProfilePage getUserProfilePage() {
        return new UserProfilePage(driver);
    }
}