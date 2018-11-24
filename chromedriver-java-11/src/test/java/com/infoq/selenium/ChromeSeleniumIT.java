package com.infoq.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChromeSeleniumIT {

    private static final boolean HEADLESS = true;
    private static final String CHROME_DRIVER_DIRECTORY = "chrome-driver";

    protected WebDriver driver;

    // ----------------------------------------------------

    @BeforeEach
    public final void connect() {
        Path chrome = Paths.get(CHROME_DRIVER_DIRECTORY + "/chromedriver");
        chrome.toFile().setExecutable(true);
        System.setProperty("webdriver.chrome.driver", chrome.toAbsolutePath().toString());

        ChromeOptions chromeOptions = new ChromeOptions();
        if (HEADLESS) {
            chromeOptions.addArguments("--headless");
        }

        driver = new ChromeDriver(chromeOptions);

        // https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/27
        ((JavascriptExecutor) driver).executeScript("window.alert = function(msg) { }");
        ((JavascriptExecutor) driver).executeScript("window.confirm = function(msg) { }");
    }

    @AfterEach
    public final void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void qconDates() {
        driver.get("https://www.infoq.com");

        // using var to make it obvious this is the Java 11 version
        var qcon = driver.findElement(By.className("qcon"));
        var cities = qcon.findElements(By.tagName("strong"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
        assertTrue(cities.contains("New York"), "New York is an upcoming city: " + cities);

    }
}

