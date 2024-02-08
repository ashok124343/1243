package marolix;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.Duration;

public class MarolixEmployees {
    private static Workbook workbook;

	public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        driver.get("https://marolixhr.com/");
        driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kalluruashok.marolix@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("7993771243");

        driver.findElement(By.xpath("//button[@id='login_button']")).click();

        driver.findElement(By.xpath("//span[contains(text(),'Messenger')]")).click();

        if (driver.getTitle().contains("Messenger")) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/a[2]/*[1]")));
            nameElement.click();

            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Employee Data");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue(" Row Number ");
            headerRow.createCell(1).setCellValue("  First Name  ");

            int i = 1;
            while (true) {
                String firstNameSelector = ".messenger-list-item:nth-child(" + i + ") td:nth-child(2) p";

                try {
                    WebElement firstNameElement = driver.findElement(By.cssSelector(firstNameSelector));
                    System.out.println(i + " " + firstNameElement.getText());
                    
                    Row dataRow = sheet.createRow(i);
                    dataRow.createCell(0).setCellValue(i);
                    dataRow.createCell(1).setCellValue(firstNameElement.getText());

                    i++;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    break;
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream("EmployeeData.xlsx")) {
                workbook.write(fileOut);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.close();
        }
    }
}
