package com.diego.sistemacontrolcomputadoras.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompletarComputadoraSeleniumTest {

    private WebDriver driver;

    @Test
    void completarComputadoraPendienteDebeQuedarActiva() throws InterruptedException {

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Login
        driver.get("http://localhost:8080/login");

        driver.findElement(By.name("username")).sendKeys("diego.landeo");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(1500);

        // Ir a pendientes
        driver.get("http://localhost:8080/computadoras/pendientes");

        Thread.sleep(1500);

        String paginaPendientes = driver.getPageSource();

        assertTrue(paginaPendientes.contains("Pendiente"));

        // Entrar al primer botón Completar
        driver.findElement(By.xpath("//a[contains(.,'Completar')]")).click();

        Thread.sleep(1500);

        // Completar datos administrativos
        driver.findElement(By.name("local")).clear();
        driver.findElement(By.name("local")).sendKeys("Laboratorio Selenium");

        WebElement responsable = driver.findElement(By.name("responsable"));
        Select selectResponsable = new Select(responsable);
        selectResponsable.selectByIndex(1);

        WebElement ambienteDocente = driver.findElement(By.name("ambienteDocente"));
        Select selectDocente = new Select(ambienteDocente);
        selectDocente.selectByVisibleText("Sí");

        WebElement horaInicio = driver.findElement(By.name("horaInicioNavegacion"));
        Select selectInicio = new Select(horaInicio);
        selectInicio.selectByValue("08:00");

        WebElement horaFin = driver.findElement(By.name("horaFinNavegacion"));
        Select selectFin = new Select(horaFin);
        selectFin.selectByValue("18:00");

        // Guardar
        driver.findElement(By.xpath("//button[contains(.,'Guardar')]")).click();

        Thread.sleep(2000);

        // Verificar que vuelve al listado y aparece activa
        String contenidoPagina = driver.getPageSource();

        assertTrue(contenidoPagina.contains("Datos administrativos completados correctamente")
                || contenidoPagina.contains("Activa"));
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}