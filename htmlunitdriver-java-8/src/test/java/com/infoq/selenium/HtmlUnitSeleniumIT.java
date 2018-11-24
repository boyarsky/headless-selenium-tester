package com.infoq.selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Set;
import java.util.stream.Collectors;

public class HtmlUnitSeleniumIT {

    @Test
    void qconDates() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        //driver.setJavascriptEnabled(true);
        driver.get("https://www.infoq.com");

        WebElement qcon = driver.findElement(By.className("qcon"));
        Set<String> cities = qcon.findElements(By.tagName("strong"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toSet());
        assertTrue(cities.contains("New York"), "New York is an upcoming city: " + cities);

    }
}
