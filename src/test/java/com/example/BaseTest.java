package com.example;

// BaseTest.java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseTest {

    WebDriver driver;
    WebDriverWait wait;
    // Baas-URL testimiskeskkonnale
    final String BASE_URL = "http://uitestingplayground.com";

    @BeforeEach
    void setUp() {
        // Seadista Chrome (non-headless on vaikimisi)
        driver = new ChromeDriver();
        
        // Seadista WebDriverWait (Reegel: 5-10 sekundit)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Hea tava: maksimeeri aken
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        // Sulge brauser pärast testi
        if (driver != null) {
            driver.quit();
        }
    }
}
