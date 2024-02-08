package marolix;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
public class login {
	
	public boolean loginBasedOnSystemTime(String name) throws Throwable
	{
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		LocalTime Morning = LocalTime.of(8, 50, 0);
		LocalTime evening = LocalTime.of(18, 0);
		WebDriver driver = new  ChromeDriver();
		WebDriverManager.chromedriver().setup();
		DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		if (isWeekday(currentDate) && (currentTime.isAfter(Morning) || currentTime.equals(Morning))) 
		{


			
			
			driver.get("https://marolixhr.com/");
			driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kalluruashok.marolix@gmail.com");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("7993771243");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[@id='login_button']")).click();
			WebElement button = driver.findElement(By.id("clock_in"));

			


			try 
			{
				wait.until(ExpectedConditions.elementToBeClickable(button)); 

				
				if (currentTime.isAfter(Morning) && currentTime.isBefore(evening))
				{
					
					button.click();
					System.out.println("sucessfully Login");
                    
                }
				else if (currentTime.isAfter(evening)) 
				{
					button.click();
				
					String morningMessageLocator = "body.theme-2:nth-child(2) div.position-fixed.top-0.end-0.p-3:nth-child(6) div.toast.text-white.fade.show.bg-danger div.d-flex > div.toast-body";
                    By morningMessage = By.cssSelector(morningMessageLocator);
                    WebElement morningElement = wait.until(ExpectedConditions.visibilityOfElementLocated(morningMessage));
                    String morningMessageText = morningElement.getText();
                    System.out.println("Morning Message: " + morningMessageText);
				}
			}
				
			

			catch (org.openqa.selenium.TimeoutException e) 
			{
				System.out.println("Current Time:"+currentTime);
				System.out.println("Your Login Time Is 9.00 :"+Morning);
				String ANSI_BOLD = "\u001B[1m";
		        System.out.println(ANSI_BOLD + "Your Already Clocked_In." + ANSI_BOLD);
				
				driver.quit();
			}
			return true;
			
		}
		
		

		else if (isWeekend(currentDate)) 
		{
			System.err.println("Login not allowed WeekEnds ...");
		}
		else
		{

			System.err.println("Today Is : " +currentDayOfWeek+" And Time Is: "+currentTime);
			System.out.println("Current time is earlier than the 9.00AM.");
		}

		
		driver.quit();
		return false;
	}
	
	
	
	private boolean isWeekend(LocalDate currentDate) 
	{
		DayOfWeek dayOfWeekk = currentDate.getDayOfWeek();
		return dayOfWeekk == DayOfWeek.SATURDAY || dayOfWeekk == DayOfWeek.SUNDAY;
	}

	private static boolean isWeekday(LocalDate date) 
	{
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}


	

}
