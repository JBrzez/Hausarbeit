/*

 !!! Wurde auskommentiert, weil Fehler aufkommen, da Pfade vom Geckodriver ect. individuell nochmal geändert werden müssen.
     Bei uns läuft dieser jedoch angepasst !!!

package org.Hausarbeit.test;

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
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class loginSeleniumTest {

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
        driver.findElement(By.xpath("//*[@id="Hausarbeitwar-727407250"]/div/div[2]/div[1]/div/div/div[3]/div/div[3]/div/span")).click();

        //Click on Button "Login"
        driver.findElement(By.xpath("//*[@id="Hausarbeitwar-727407250-overlays"]/div[2]/div/div/span[2]/span")).click();

        //Daten eingeben
        driver.findElement(By.xpath("//*[@id="gwt-uid-3"]")).sendKeys("asdasd@asd.de");
        driver.findElement(By.xpath("//*[@id="gwt-uid-7"]")).sendKeys("asdasd");

        //Login button drücken
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[5]/div/div[2]/div/div[13]/div/div[1]/div")).click();

        //Login Button clicken
        driver.findElement(By.xpath("//*[@id="Hausarbeitwar-727407250"]/div/div[2]/div[5]/div/div[2]/div/div[5]/div/div[1]/div")).click();

        //Pause um zu checken
        TimeUnit.SECONDS.sleep(3);

        //Check ob gleich
        assertEquals("http://localhost:8080/Hausarbeit_war/main#!main",driver.getCurrentUrl());
    }
    @After
    public void tearDownClass() {
        driver.quit();
    }

}*/
