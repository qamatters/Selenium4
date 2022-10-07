package locator;

import helper.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class NewLocators extends SeleniumHelper {
    @BeforeTest
    public static void launchBrowser() {
        SeleniumHelper.launchBrowser("https://www.google.com");
    }

    @Test
    public static void validatePageTitle() {
        System.out.println("page title is :" + driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Google");
        WebElement gmailLink = driver.findElement(By.xpath("//*[@class='gb_d' and contains(text(),'Gmail')]"));
        if (gmailLink.isDisplayed()) {
            gmailLink.click();
            Assert.assertEquals(driver.getTitle(), "Gmail: Private and secure email at no cost | Google Workspace");
        } else {
            Assert.fail(" Gmail link is not displayed");
        }
    }

    @AfterTest
    public static void quit() {
        driver.quit();
    }

}

