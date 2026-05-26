package com.diego.sistemacontrolcomputadoras.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSeleniumTest {

    private WebDriver driver;

    @Test
    void loginCorrectoDebeMostrarDashboard() throws InterruptedException {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("http://localhost:8080");

        WebElement usuario = driver.findElement(By.name("username"));
        usuario.sendKeys("diego.landeo");

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");

        WebElement botonIngresar = driver.findElement(By.cssSelector("button[type='submit']"));
        botonIngresar.click();

        Thread.sleep(2000);

        String contenidoPagina = driver.getPageSource();

        assertTrue(contenidoPagina.contains("SIGECOM"));
    }

    @Test
    void loginIncorrectoDebeMostrarMensajeError() throws InterruptedException {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("http://localhost:8080/login");

        WebElement usuario = driver.findElement(By.name("username"));
        usuario.sendKeys("usuarioIncorrecto");

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("claveIncorrecta");

        WebElement botonIngresar = driver.findElement(By.cssSelector("button[type='submit']"));
        botonIngresar.click();

        Thread.sleep(2000);

        String contenidoPagina = driver.getPageSource();

        assertTrue(contenidoPagina.contains("Usuario o contraseña incorrectos"));
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}