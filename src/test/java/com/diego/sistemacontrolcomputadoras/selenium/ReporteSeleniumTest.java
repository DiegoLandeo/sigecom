package com.diego.sistemacontrolcomputadoras.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteSeleniumTest {

    private WebDriver driver;

    @Test
    void reportesDebeCargarCorrectamente() throws InterruptedException {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        // LOGIN
        driver.get("http://localhost:8080/login");

        driver.findElement(By.name("username")).sendKeys("diego.landeo");
        driver.findElement(By.name("password")).sendKeys("admin123");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1500);

        // IR A REPORTES
        driver.get("http://localhost:8080/reportes");

        Thread.sleep(1500);

        String contenidoPagina = driver.getPageSource();

        assertTrue(contenidoPagina.contains("Reportes"));
    }

    @AfterEach
    void cerrarNavegador() {

        if (driver != null) {
            driver.quit();
        }
    }
}