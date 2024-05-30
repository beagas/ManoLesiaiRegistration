package org.example.Registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ManoLesiaiRegistration {

    public static WebDriver _driver;

    String name;
    String lastname;
    String email;
    String password;
    String repeatPassword;

    public ManoLesiaiRegistration(String name, String lastname, String email, String password, String repeatPassword) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public static WebElement snoozeUntilElement(By by) {
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element;
    }

    public void fillRegistration() {
        openRegistration();
        fillPersonalInformation();
        fillPassword();
        getMessage();
    }

    public void openRegistration() {
        //Open Dropdown menu
//        Utils._driver.get("https://www.manolesiai.lt/lt/");
//        Utils._driver.findElement(By.cssSelector("[data-cookiefirst-action='accept']")).click();//accept cookies
//        Utils._driver.findElement(By.xpath("/html/body/div[2]/header/div/div[2]/nav/div/div[2]/div/div[3]/div")).click();//open dropdown menu
//
//        WebDriverWait wait = new WebDriverWait(Utils._driver, Duration.ofSeconds(10));//wait for the dropdown window
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("\"/html/body/div[3]/div/div[1]/div/div[2]\"")));
//
//        //Utils._driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div[2]")).click();
//        //Utils._driver.findElements(By.className("dropdown-item")).get(3).click();
//        //Utils._driver.findElement(By.xpath("//a[text()='Užsiregistruoti']")).click();


        Utils._driver.get("https://www.manolesiai.lt/lt/register");
        WebDriverWait wait = new WebDriverWait(Utils._driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-cookiefirst-action='accept']")));
        Utils._driver.findElement(By.cssSelector("[data-cookiefirst-action='accept']")).click();//accept cookies
    }

    public void fillPersonalInformation() {
        //fill in Name
        Utils._driver.findElement(By.id("sylius_customer_registration_firstName")).sendKeys(name);

        //fill in Lastname
        Utils._driver.findElement(By.id("sylius_customer_registration_lastName")).sendKeys(lastname);

        //fill in Email
        Utils._driver.findElement(By.id("sylius_customer_registration_email")).sendKeys(email);
    }

    public void fillPassword() {
        //fill in Password
        Utils._driver.findElement(By.id("sylius_customer_registration_user_plainPassword_first")).sendKeys(password);

        //repeat Password
        Utils._driver.findElement(By.id("sylius_customer_registration_user_plainPassword_second")).sendKeys(repeatPassword);
    }

    public void getMessage() {
        Utils._driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/form/button")).click();

            Utils._driver.findElement(By.className("d-flex align-items-start"));

            //




        /*
        <span class="form-error-message">Šis el. paštas jau naudojamas.</span>
         */

    }
}