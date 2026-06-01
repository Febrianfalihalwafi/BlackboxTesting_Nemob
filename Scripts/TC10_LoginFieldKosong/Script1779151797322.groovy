import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
WebUI.delay(3)

// 2. Tutup pop-up iklan
try {
    WebElement tombolX = driver.findElement(By.xpath(
        "//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"
    ))
    js.executeScript("arguments[0].click();", tombolX)
    println("✓ Popup iklan berhasil ditutup")
    WebUI.delay(2)
} catch (Exception e) {
    println("- Popup tidak muncul atau sudah ditutup")
}

// 3. Buka form login via navbar
try {
    WebElement tombolMasukNavbar = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'Masuk')] | //button[contains(text(),'Masuk')]")
    ))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
} catch (Exception e) {
    WebElement tombolMasukNavbar = driver.findElement(By.xpath(
        "//a[contains(@href,'login')] | //a[contains(@href,'masuk')]"
    ))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
}
WebUI.delay(4)

// 4. Biarkan email dan password kosong, langsung klik login
WebElement tombolSubmitLogin = driver.findElement(By.id("send_message"))
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombolSubmitLogin)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSubmitLogin)
WebUI.delay(3)

// --- VALIDASI: Pesan error sesuai tampilan website ---
String isiHalamanWeb = driver.getPageSource()
boolean isPesanEmailMuncul = isiHalamanWeb.contains("Email is required!")
boolean isPesanPasswordMuncul = isiHalamanWeb.contains("Password is required!")

assert isPesanEmailMuncul, "GAGAL! Pesan 'Email is required!' tidak muncul."
println("✓ Pesan validasi email muncul: Email is required!")

assert isPesanPasswordMuncul, "GAGAL! Pesan 'Password is required!' tidak muncul."
println("✓ Pesan validasi password muncul: Password is required!")

println("=== TC10 PASSED: Validasi field kosong berhasil diverifikasi ===")
WebUI.closeBrowser()