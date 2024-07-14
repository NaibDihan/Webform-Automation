import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyJunit {
    WebDriver driver;
    @BeforeAll
    public void setup(){
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--headed");
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Get Title")
    @Test
    public void getTitle(){
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        String titleActual = driver.getTitle();
        System.out.println(titleActual);
        String titleExpected = "Practice webform for learners | Digital Unite";
        Assertions.assertTrue(titleActual.contains(titleExpected));
        Assertions.assertEquals(titleExpected,titleActual);
    }
    @Test
    public void formFillup() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.className("banner-close-button")).click();

        Faker faker = new Faker();
        String name = faker.name().name();
        String firstname = faker.name().firstName();
        System.out.println(name);


        List<WebElement> formControls = driver.findElements(By.className("form-control"));
        formControls.get(0).sendKeys(name);
        formControls.get(1).sendKeys("01313445613");
        String date = "07/15/2024";
        formControls.get(2).sendKeys(date);
        //driver.findElement(By.cssSelector("[data-drupal-selector=edit-date]")).click();
        formControls.get(3).sendKeys(firstname+"@gmail.com");
        Utils.scroll(driver,1000);
        formControls.get(4).sendKeys("I am"+ " " +name);
        //Utils.scroll(driver,600);
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys("D://Fake PDF.pdf");
        driver.findElement(By.id("edit-age")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name=uploadocument_remove_button]")));
        //Thread.sleep(6000);
        driver.findElement(By.id("edit-submit")).click();

        String submissonMessageExpected = "Thank you for your submission!";
        String submissonMessageActual = driver.findElement(By.id("block-pagetitle-2")).getText();
        Assertions.assertEquals(submissonMessageExpected,submissonMessageActual);
        //System.out.println(submissonMessageActual);


    }

    @AfterAll
    public void closeBrowser(){
        //driver.close();
        driver.quit();
    }
}
