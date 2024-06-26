import org.example.Registration.ManoLesiaiRegistration;
import org.example.Registration.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tests {

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

    @BeforeClass
    public void SetupWebDriver() {
        Utils._driver = new ChromeDriver();
        Utils._driver.manage().window().maximize();
        Utils._driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test //positive test
    public void ml_01() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonauskas", generateRandomEmail(),
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Expected text
        String goodRegistrationText = "Ačiū už registraciją, pasitikrinkite savo el. paštą ir patvirtinkite savo paskyrą.";

        //Received text
        WebElement result = Utils._driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div[2]"));//successful registration text
        String goodRegistration = result.getText();

        //Compare text
        Assert.assertEquals(goodRegistration, goodRegistrationText);
        Utils._driver.close();
    }

    @Test //no mandatory fields filled
    public void ml_02() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("", "", "",
                "", "");
        reg01.fillRegistration();

        //Received missing name error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Įveskite savo vardą.']"));

        //Received missing lastname error text
       Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Įveskite savo pavardę.']"));

        //Received missing email error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Įveskite savo elektroninio pašto adresą.']"));

        //Received missing password error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Įveskite savo slaptažodį.']"));
        Utils._driver.close();
    }

    @Test //numbers in name
    public void ml_03() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas123", "Jonaitis", generateRandomEmail(),
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received name error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Varde negali būti simbolių ar skaičių']"));
        Utils._driver.close();
    }

    @Test //symbols in name
    public void ml_04() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas~`!@#$%^&*()-_+={}[]|/\\:;\"`<>,.?", "Jonaitis", generateRandomEmail(),
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received missing name error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Varde negali būti simbolių ar skaičių']"));
        Utils._driver.close();
    }

    @Test //numbers in lastname
    public void ml_05() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis123", generateRandomEmail(),
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received lastname error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Pavardėje negali būti simbolių']"));
        Utils._driver.close();
    }

    @Test //symbols in lastname
    public void ml_06() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis~`!@#$%^&*()-_+={}[]|/\\:;\"`<>,.?", generateRandomEmail(),
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received lastname error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Pavardėje negali būti simbolių']"));
        Utils._driver.close();
    }

    @Test //email is already registered
    public void ml_07() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis", "jonas@gmail.com",
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received missing name error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Šis el. paštas jau naudojamas.']"));
        Utils._driver.close();
    }
    @Test //incorrect email
    public void ml_08() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis123", "jonasgmail.com",
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received incorrect email error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Šis adresas yra neteisingas.']"));
        Utils._driver.close();
    }

    @Test //email not full
    public void ml_09() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis123", "jonas@gmail",
                "Jonas123+", "Jonas123+");
        reg01.fillRegistration();

        //Received incorrect email error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Šis adresas yra neteisingas.']"));
        Utils._driver.close();
    }

    @Test //3 symbol password
    public void ml_10() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis", generateRandomEmail(),
                "Jon", "Jon");
        reg01.fillRegistration();

        //Receive too short password error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Slaptažodis turi būti ilgesnis nei 4 simboliai.']"));
        Utils._driver.close();
    }

    @Test //different passwords
    public void ml_11() {
        ManoLesiaiRegistration reg01 = new ManoLesiaiRegistration("Jonas", "Jonaitis", generateRandomEmail(),
                "Jonas123+", "Jonas123");
        reg01.fillRegistration();

        //Received missing name error text
        Utils._driver.findElement(By.xpath("//span[@class='form-error-message' and text()='Įvesti slaptažodžiai nesutampa']"));
        Utils._driver.close();
    }


}
