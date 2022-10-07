package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.devtools.v96.performance.Performance;
import org.openqa.selenium.devtools.v96.performance.model.Metric;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.util.List;
import java.util.Optional;

public class SeleniumHelper {
    public static WebDriver driver;
    public static final String chromeDriverPath = "webdriver.chrome.driver";

    public static void launchBrowser(String url) {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_106\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(url);

    }

    public static void performanceMetricsExample(String url) {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_101\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());
        driver.manage().window().maximize();
        driver.navigate().to(url);
        for(Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
        driver.quit();
    }

    public static void consoleLogExample(String url) {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_101\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        driver.manage().window().maximize();
        driver.navigate().to(url);

        WebElement imFeeling = driver.findElement(By.xpath("(//input[@name='btnI' and @type='submit'])[2]"));
        WebElement search =driver.findElement(RelativeLocator.with(By.xpath("//input")).above(imFeeling));
        search.sendKeys("Accenture ");

        imFeeling.click();

        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: "+logEntry.getText());
                    System.out.println("level: "+logEntry.getLevel());
                });

        driver.quit();
    }

    public static void netWorkLogExample(String url) throws InterruptedException {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_101\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("-------------------------------------------");
            System.out.println("Request ID = " + logEntry.getNetworkRequestId());
            System.out.println("URL = " + logEntry.getUrl());
            System.out.println("Source = " + logEntry.getSource());
            System.out.println("Level = " + logEntry.getLevel());
            System.out.println("Text = " + logEntry.getText());
            System.out.println("Timestamp = " + logEntry.getTimestamp());
            System.out.println("-------------------------------------------");
        });
        driver.get(url);
       Thread.sleep(1000*3);
       driver.quit();
    }


}
