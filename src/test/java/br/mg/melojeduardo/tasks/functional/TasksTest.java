package br.mg.melojeduardo.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver accessApp() throws MalformedURLException {
//        WebDriver driver = new ChromeDriver();

        // Redirecionando a execução a um grid
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName("chrome");
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.0.104:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void shouldSaveTaskSuccessfully() throws MalformedURLException {
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
    public void shouldNotSaveTaskWithoutDescription() throws MalformedURLException {
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
    public void shouldNotSaveTaskWithoutDate() throws MalformedURLException {
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
    public void shouldNotSaveTaskWithPastDate() throws MalformedURLException {
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
