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


public class logout 
{

	public  void logoutBasedOnSystemTime()
	{
		WebDriver driver = new  ChromeDriver();
		WebDriverManager.chromedriver().setup();
		driver.manage().window().maximize();
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		LocalTime targetTime = LocalTime.of(18, 0);
		 try {
			 
		if ((isWeekday(currentDate) && currentTime.isAfter(targetTime)) || currentTime.equals(targetTime)) 
		{


			
			driver.get("https://marolixhr.com/");
			driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("kalluruashok.marolix@gmail.com");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("7993771243");

			driver.findElement(By.xpath("//button[@id='login_button']")).click();

			WebElement button = driver.findElement(By.xpath("//button[@id='clock_out']"));

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			try 
			{
				wait.until(ExpectedConditions.elementToBeClickable(button));

				button.click();
				System.out.println("Current Time Is:"+currentTime+ " Logout Time Is: " +targetTime);
				System.out.println("Your Successfully Logout");

			}
			catch (org.openqa.selenium.TimeoutException e) 
			{
				System.out.println("Current Time:"+currentTime);
				System.out.println("Your Logout Time Is:"+targetTime);
				System.out.println("Your Already Logged_Out.");
			}
			driver.quit();
		} 
		else if (isWeekend(currentDate))
		{
			System.err.println("LogOut not allowed WeekEnds ...");
		}
		else 
		{
			System.out.println("Your Logout Time Is:"+targetTime);
			System.out.println("Logout Current time is earlier than the 18.00");

		}
		driver.quit(); 
		 }
		 catch (Throwable throwable) {
	            throwable.printStackTrace();
	        }
		driver.quit();   
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
