package com.example;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class UITestingPlaygroundTests extends BaseTest {

    @Test
    void sampleAppLogin() {
        driver.get(BASE_URL + "/sampleapp");
        String userName = "TestUser";


        driver.findElement(By.cssSelector("input[name='UserName']")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name='Password']")).sendKeys("pwd");
        driver.findElement(By.id("login")).click();


        WebElement statusLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginstatus")));
        assertEquals("Welcome, " + userName + "!", statusLabel.getText());
    }

    @Test
    void sampleAppLogout() {
        driver.get(BASE_URL + "/sampleapp");
        String userName = "TestUser";


        driver.findElement(By.cssSelector("input[name='UserName']")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name='Password']")).sendKeys("pwd");
        driver.findElement(By.id("login")).click();

        wait.until(ExpectedConditions.textToBe(
                By.id("loginstatus"), "Welcome, " + userName + "!"
        ));

        WebElement logoutButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        logoutButton.click();


        wait.until(ExpectedConditions.textToBe(By.id("loginstatus"), "User logged out."));

        WebElement statusLabel = driver.findElement(By.id("loginstatus"));
        assertEquals("User logged out.", statusLabel.getText());
    }

    @Test
    void dynamicId() {
        driver.get(BASE_URL + "/dynamicid");


        WebElement button = driver.findElement(By.cssSelector("button.btn-primary"));
        

        
        button.click();

        assertTrue(true, "Click succeeded without exception.");
    }

    @Test
    void classAttribute() {
        driver.get(BASE_URL + "/classattr");


        driver.findElement(By.cssSelector("button.btn-primary")).click();


        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        

        alert.accept();


        assertTrue(alertText.contains("Primary"), "Alert text should contain 'Primary'");
    }

    @Test
    void hiddenLayers() {
        driver.get(BASE_URL + "/hiddenlayers");
        WebElement greenButton = driver.findElement(By.id("greenButton"));

        greenButton.click();


        assertThrows(ElementClickInterceptedException.class, () -> {
            greenButton.click();
        }, "Second click on green button should be intercepted by the blue button.");
    }

    @Test
    void loadDelay() {
        driver.get(BASE_URL + "/loaddelay");


        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button.btn-primary")
        ));


        assertTrue(button.isDisplayed(), "Button should be displayed after delay.");
    }

    @Test
    void ajaxData() {
        driver.get(BASE_URL + "/ajax");


        driver.findElement(By.id("ajaxButton")).click();


        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement content = longWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#content p")
        ));


        assertEquals("Data loaded with AJAX get request.", content.getText());
    }   

    @Test
    void textInput() {
        driver.get(BASE_URL + "/textinput");
        String newText = "Hello";


        driver.findElement(By.id("newButtonName")).sendKeys(newText);
        

        WebElement button = driver.findElement(By.id("updatingButton"));
        button.click();

        wait.until(ExpectedConditions.textToBe(By.id("updatingButton"), newText));
        assertEquals(newText, button.getText());
    }

    @Test
    void scrollbars() {
        driver.get(BASE_URL + "/scrollbars");
        WebElement button = driver.findElement(By.id("hidingButton"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);


        wait.until(ExpectedConditions.elementToBeClickable(button)).click();


        assertTrue(true, "Click succeeded after scrolling.");
    }

    @Test
    void overlappedElement() {
        driver.get(BASE_URL + "/overlapped");
        WebElement nameInput = driver.findElement(By.id("name"));
        String textToType = "abc";


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nameInput);


        wait.until(ExpectedConditions.visibilityOf(nameInput)).sendKeys(textToType);


        assertEquals(textToType, nameInput.getAttribute("value"));
    }

    @Test
    void visibility() {
        driver.get(BASE_URL + "/visibility");


        driver.findElement(By.id("hideButton")).click();

        int removedButtonCount = driver.findElements(By.id("removedButton")).size();
        assertEquals(0, removedButtonCount, "Removed button should not be found in DOM.");


        WebElement invisibleButton = driver.findElement(By.id("invisibleButton"));
        assertFalse(invisibleButton.isDisplayed(), "Invisible button should be in DOM but not displayed.");
    }

    @Test
    void click() {
        driver.get(BASE_URL + "/click");
        WebElement button = driver.findElement(By.id("badButton"));


        assertTrue(button.getAttribute("class").contains("btn-primary"));
        

        button.click();


        wait.until(ExpectedConditions.attributeContains(button, "class", "btn-success"));
        assertTrue(button.getAttribute("class").contains("btn-success"),
                "Button class should update to btn-success");
    }

    @Test
    void progressBar() {
        driver.get(BASE_URL + "/progressbar");
        WebElement progressBar = driver.findElement(By.id("progressBar"));


        driver.findElement(By.id("startButton")).click();

        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        longWait.until(d -> {
            String currentValue = progressBar.getAttribute("aria-valuenow");

            return !currentValue.isEmpty() && Integer.parseInt(currentValue) >= 75;
        });


        driver.findElement(By.id("stopButton")).click();

        int finalValue = Integer.parseInt(progressBar.getAttribute("aria-valuenow"));
        assertTrue(finalValue >= 75, "Progress bar should stop at or after 75 (oli: " + finalValue + ")");
    }

    @Test
    void mouseOver() {
        driver.get(BASE_URL + "/mouseover");
        WebElement clickMeLink = driver.findElement(By.cssSelector("a.text-primary"));
        WebElement clickCount = driver.findElement(By.id("clickCount"));
        

        Actions actions = new Actions(driver);


        actions.moveToElement(clickMeLink).perform();

        actions.click().perform();
        actions.click().perform();
        


        assertEquals("2", clickCount.getText(), "Click count should be 2.");
    }
    
    @Test
    void shadowDOM() {
        driver.get(BASE_URL + "/shadowdom");


        WebElement shadowHost = driver.findElement(By.tagName("guid-generator"));
        

        SearchContext shadowRoot = shadowHost.getShadowRoot();
        

        WebElement generateButton = shadowRoot.findElement(By.cssSelector("button#buttonGenerate"));
        generateButton.click();

        WebElement inputInsideShadow = shadowRoot.findElement(By.cssSelector("input#editField"));
        

        String guidValue = inputInsideShadow.getAttribute("value");
        assertNotNull(guidValue, "Input value should not be null.");
        assertTrue(guidValue.length() > 10, "Input should contain a GUID value.");
    }
}