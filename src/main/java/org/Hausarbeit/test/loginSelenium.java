/*package org.Hausarbeit.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class seleniumRegisterViewTest {

    private WebDriver driver = null;

    @Before
    public void setUpClass(){
        System.setProperty("webdriver.gecko.driver","F:\\Uni\\4. Semester\\SE II\\Projekt\\Geckodriver\\geckodriver.exe");
        File pathBinary = new File("E:\\Mozilla Firefox\\firefox.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
        DesiredCapabilities desired = DesiredCapabilities.firefox();
        FirefoxOptions options = new FirefoxOptions();
        desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
        driver = new FirefoxDriver(options);
    }

    @Test
    public void startWebDriver() throws InterruptedException {

        //Öffne Seite
        driver.get("http://localhost:8080/Hausarbeit_war/#!main");

        //Fullsize
        driver.manage().window().maximize();

        //Click on Button "Menü"
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/div/div[3]/div/div[3]/div/span")).click();

        //Click on Button "Registrieren"
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/span[2]/span")).click();

        //Daten eingeben
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("asd@asd.de");
        driver.findElement(By.xpath("//*[@id=\"passwort1\"]")).sendKeys("asdasd");
        driver.findElement(By.xpath("//*[@id=\"passwort2\"]")).sendKeys("asdasd");

        //Click on Button "Vertriebler"
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[5]/div/div[2]/div/div[11]/div/div[2]/span[1]/label")).click();

        //Login button drücken
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[5]/div/div[2]/div/div[13]/div/div[1]/div")).click();

        //Ok Button clicken
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[3]/div/div/div[3]/div")).click();

        //Check ob gleich
        assertEquals("http://localhost:8080/Hausarbeit_war/#!main",driver.getCurrentUrl());
    }
    @After
    public void tearDownClass() {
        driver.quit();
    }

}*/
