import org.example.Registration.ManoLesiaiRegistration;
import org.example.Registration.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tests {
    public static WebDriver _driver;

    @BeforeClass
    public void SetupWebDriver() {
        Utils._driver = new ChromeDriver();
        Utils._driver.manage().window().maximize();
        Utils._driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeClass
    public static String generateRandomEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};
        String[] characters = {"abcdefghijklmnopqrstuvwxyz", "0123456789"};

        Random random = new Random();
        StringBuilder email = new StringBuilder();

        // Generate username part
        int usernameLength = random.nextInt(10) + 5; // Random length between 5 and 14 characters
        for (int i = 0; i < usernameLength; i++) {
            String characterSet = characters[random.nextInt(2)]; // Selecting either alphabets or numbers
            char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
            email.append(randomChar);
        }

        // Adding '@' symbol
        email.append("@");

        // Selecting random domain
        String randomDomain = domains[random.nextInt(domains.length)];
        email.append(randomDomain);

        return email.toString();
    }

    @Test
    public void ml_01(){
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas","Jonauskas", "jonas@gmail.com",
                "Jonas", "Jonas");
        reg01.fillRegistration();
    }

    @Test
    public void ml() {
        Utils._driver.get("https://www.manolesiai.lt/lt/register");
        Utils._driver.findElement(By.cssSelector("[data-cookiefirst-action='accept']")).click();//accept cookies
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //fill in Name
        _driver.findElement(By.id("sylius_customer_registration_firstName")).sendKeys("jonas");

        //fill in Lastname
        _driver.findElement(By.id("sylius_customer_registration_lastName")).sendKeys("jonas");

        //fill in Email
        _driver.findElement(By.id("sylius_customer_registration_email")).sendKeys("jonas@gmail.com");
        _driver.findElement(By.id("sylius_customer_registration_user_plainPassword_first")).sendKeys("jonas");

        //repeat Password
        _driver.findElement(By.id("sylius_customer_registration_user_plainPassword_second")).sendKeys("jonas");

    }

}
