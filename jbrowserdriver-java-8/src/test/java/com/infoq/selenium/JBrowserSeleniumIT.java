package com.infoq.selenium;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JBrowserSeleniumIT {

    protected WebDriver driver;

    // ----------------------------------------------------

    @BeforeEach
    public final void connect() {
        driver = new JBrowserDriver(Settings.builder()
                .timezone(Timezone.AMERICA_NEWYORK)
                .userAgent(UserAgent.CHROME).build());

        // says 120 but is really 0
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
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

        WebElement qcon = driver.findElement(By.className("qcon"));
        Set<String> cities = qcon.findElements(By.tagName("strong"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
        assertTrue(cities.contains("New York"), "New York is an upcoming city: " + cities);

    }
}
