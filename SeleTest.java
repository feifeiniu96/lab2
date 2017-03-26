package seTest;

import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.csvreader.CsvReader;

@RunWith(Parameterized.class)
public class SeleTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id, pw, git;
	
	public SeleTest(String id, String git){
		this.id = id;
		this.pw = id.substring(4);
		this.git = git;
	}
	
  @Before
  public void setUp() throws Exception {
	  System.setProperty("webdriver.firefox.bin", "D:/Program Files/firefox/firefox.exe");
	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8080";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Parameters
  public static Collection<Object[]> getData() throws IOException {
	         Object[][] obj = new Object[118][];
	         CsvReader r = new CsvReader("E:\\16172\\ST\\lab\\inputgit.csv", ',',
	        		 Charset.forName("GBK"));
	          int num = 0;
	          r.readHeaders();
	          while(r.readRecord()){
	              obj[num] = new Object[]{r.get(0), r.get(2)};
	              num++;
	          }
	          return Arrays.asList(obj);
  }
  
  @Test
  public void testLab2() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys(this.id);
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys(this.pw);
    new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Ů");
    driver.findElement(By.id("submit")).click();
    assertEquals(this.git, driver.findElement(By.xpath("//tbody[@id='table-main']/tr[3]/td[2]")).getText());
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
