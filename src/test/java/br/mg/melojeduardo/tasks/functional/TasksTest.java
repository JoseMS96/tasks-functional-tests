package br.mg.melojeduardo.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver accessApp() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void shouldSaveTaskSuccessfully() {
        WebDriver driver = accessApp();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithoutDescription() {
        WebDriver driver = accessApp();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithoutDate() {
        WebDriver driver = accessApp();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithPastDate() {
        WebDriver driver = accessApp();

        try {
            //Clicar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //Fechar o browser
            driver.quit();
        }
    }
}
