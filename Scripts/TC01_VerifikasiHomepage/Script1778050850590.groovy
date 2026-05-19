import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory

WebUI.openBrowser('')
WebUI.maximizeWindow()

WebUI.navigateToUrl("https://nemob.id/id")

WebDriver driver=DriverFactory.getWebDriver()

assert driver.findElement(By.tagName("body")).isDisplayed()

WebUI.closeBrowser()