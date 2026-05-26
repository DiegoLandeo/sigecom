package com.diego.sistemacontrolcomputadoras.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponsableSeleniumTest {

    private WebDriver driver;

    @Test
    void registrarResponsableDebeMostrarseEnListado() throws InterruptedException {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Login
        driver.get("http://localhost:8080/login");

        driver.findElement(By.name("username")).sendKeys("diego.landeo");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1500);

        // Ir a formulario de nuevo responsable
        driver.get("http://localhost:8080/responsables/nuevo");

        String nombreResponsable = "Prueba";

        driver.findElement(By.name("nombre")).sendKeys(nombreResponsable);
        driver.findElement(By.name("edad")).sendKeys("25");

        WebElement nivelProfesional = driver.findElement(By.name("nivelProfesional"));
        Select selectNivel = new Select(nivelProfesional);
        selectNivel.selectByVisibleText("Universitario");

        driver.findElement(By.xpath("//button[contains(.,'Guardar')]")).click();

        Thread.sleep(2000);

        driver.get("http://localhost:8080/responsables");

        Thread.sleep(1500);

        String contenidoPagina = driver.getPageSource();

        System.out.println(contenidoPagina);

        assertTrue(contenidoPagina.contains(nombreResponsable));
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}