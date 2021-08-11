package com.testing;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Verify;

public class ExtentReportWithTestNGDemo {

	static WebDriver driver;
	WebElement searchField;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeMethod
	public void setup() {
		
        htmlReporter = new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
		File dr = new File("projectData");
		File chromeDriver = new File(dr, "chromedriver.exe");
		
		System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
		driver = new ChromeDriver();
		
		driver.manage().window().maximize(); // Maximize the browser
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,10);
		
		driver.get("https://www.google.com/");
		
		searchField = driver.findElement(By.xpath("//input[@title=\"Search\"]"));
		
	}
	
	@AfterMethod
	public void tearDown() {
		
		driver.quit();
		extent.flush();
		
	}
	
	@Test 
	public void indiaSearchTest() throws Exception {
		
		test = extent.createTest("India Search Test", "Sample description");
		
		test.log(Status.INFO, "Log Info");
		test.info("India Search Test Begins.");
		
		test.info("Entered India in Search field");
		searchField.sendKeys("India");
		
		searchField.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		
		test.info("Check India text presents in current URL");
		if(driver.getCurrentUrl().contains("India")) {
			System.out.println("India Search Test Pass.");
			test.pass("India Search Test Pass.");
			test.info("India Search Test Finished.");
		}
		else {
			System.out.println("India Search Test Fail.");
			test.fail("India Search Test Fail.");
			test.info("India Search Test Finished.");
		}
			
	}

}
