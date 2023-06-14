package practicetest;

import org.testng.annotations.Test;

import com.test.utility.TestUtil;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class SauceDemo {
	
	WebDriver driver;
	
 @BeforeTest
  public void beforeTest() throws InterruptedException {
	  System.setProperty("webdriver.chrome.driver", "C:\\New folder\\New folder\\AssignmentTest\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		 driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
//Navigate to site		
		driver.get("https://www.saucedemo.com/");
		
//Invalid input check		
		WebElement uname = driver.findElement(By.name("user-name"));
	    uname.sendKeys("ads");
		 WebElement pwd =  driver.findElement(By.name("password"));
		 pwd.sendKeys("ghy");
		  driver.findElement(By.name("login-button")).click();
		  
		 String actual_msg=driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
		  String expect="Epic sadface: Username and password do not match any user in this service";

		  try {
			    Assert.assertEquals(actual_msg, expect);
			} catch (AssertionError e) {
			    System.out.println("Not equal");
			    throw e;
			}
			System.out.println("Equal");
			uname.clear();
			pwd.clear();
			driver.navigate().refresh();
			Thread.sleep(3000);
  }
  

  @DataProvider
  public Iterator<Object[]> getTestData() {
			ArrayList<Object[]> testData=TestUtil.getDataFromExcel();
			return testData.iterator();
		}
  
  
  @Test(dataProvider= "getTestData" )
  public void LoginCheck(String Username, String Password) throws InterruptedException {
	  
//Valid input check read from excel	  
	  Thread.sleep(5000);
	  driver.findElement(By.name("user-name")).sendKeys(Username);
	  driver.findElement(By.name("password")).sendKeys(Password);
	  driver.findElement(By.name("login-button")).click();
	  String nextPageURL = driver.getCurrentUrl();
      int assertValue = 0;
	     if(nextPageURL.equals("https://www.saucedemo.com/inventory.html"))
	       {
	         assertValue++;
	         System.out.println("login successful");
	       }
	     else {
	    	 Assert.assertFalse(assertValue==0, "Since the URL is not what is expected, hence marking the Test Case as FAILED");
	       }

	      Assert.assertTrue(true, "login successful.");
	      
	
	      
//sort products in price low to high
	      Thread.sleep(5000);
	     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	     driver.findElement(By.className("product_sort_container")).click();
	     driver.findElement(By.xpath("//option[@value='lohi']")).click();

	     
//Add all items to cart	   
	     Thread.sleep(5000);
	     List<WebElement> itemList = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory']"));
	     System.out.println(itemList.size());
	    
	          for(int i=0; i<itemList.size();i++) {
	    		  System.out.println(itemList.get(i).getText());
                  itemList.get(i).click();
                  System.out.println(i);
		    	}
	   

	     
//Go to cart and remove items price<15     
	          Thread.sleep(5000);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	    driver.navigate().to("https://www.saucedemo.com/cart.html");

	     List<WebElement> removeItemFromCart = driver.findElements(By.cssSelector("div.inventory_item_price"));
    	
    	for(WebElement product : removeItemFromCart ) {
    		String priceText = driver.findElement(By.cssSelector("div.inventory_item_price")).getText();
    		double price = Double.parseDouble(priceText.replace("$", ""));
    		
    		if(price <15.0) {
    			WebElement removeButton = driver.findElement(By.xpath("//button[@class='btn btn_secondary btn_small cart_button']"));
    			removeButton.click();
    		}
    	}
    	
    	
    	
//click on checkout button  
    	 Thread.sleep(5000);
    	WebElement checkoutButton = driver.findElement(By.name("checkout"));
	    checkoutButton.click();
	    	
//Enter details in information page and continue
	         Thread.sleep(5000);
	    	driver.findElement(By.name("firstName")).sendKeys("Maria");
	    	driver.findElement(By.name("lastName")).sendKeys("Joseph");
	    	driver.findElement(By.name("postalCode")).sendKeys("689575");
	    	driver.findElement(By.name("continue")).click();
	    	
//Finish the checkout	
	    	 Thread.sleep(5000);
	    	driver.findElement(By.name("finish")).click();
	    	
//Return to home page
	    	 Thread.sleep(5000);
	    	driver.findElement(By.name("back-to-products")).click();
	    	
//Perform Logout	 
	    	 Thread.sleep(5000);
	    	 driver.findElement(By.id("react-burger-menu-btn")).click();
	    	 driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
}


@AfterTest
  public void afterTest() {
	driver.quit();
  }

}
