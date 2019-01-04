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

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Set<String> newYorkCity = driver.findElements(By.className("qcon"))
                .stream()
                .map(element -> element.getAttribute("innerText"))
                .filter(city -> city.trim().startsWith("New York"))
                .collect(Collectors.toSet());
        assertEquals(1, newYorkCity.size(), "New York is an upcoming city");
    }

}
