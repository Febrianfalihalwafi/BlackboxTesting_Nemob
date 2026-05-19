import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

// 1. Buka browser dan arahkan ke website nemob.id
WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

WebUI.delay(3)

// 2. Tutup pop-up iklan jika muncul
try {
    WebElement tombolX = driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"))
    js.executeScript("arguments[0].click();", tombolX)
    println("Popup iklan berhasil ditutup")
    WebUI.delay(2)
} catch(Exception e) {
    println("Popup tidak muncul atau sudah ditutup")
}

// 3. Buka form login via navbar
try {
    WebElement tombolMasukNavbar = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'Masuk')] | //button[contains(text(),'Masuk')]")
    ))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
} catch (Exception e) {
    WebElement tombolMasukNavbar = driver.findElement(By.xpath("//a[contains(@href,'login')] | //a[contains(@href,'masuk')]"))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
}
WebUI.delay(4)

// 4. Masukkan email valid
WebElement inputEmail = driver.findElement(By.xpath("//input[contains(@placeholder,'email') or contains(@placeholder,'Email')]"))
inputEmail.clear()
inputEmail.sendKeys("inisiatifsendiri12@gmail.com")

// 5. Masukkan password salah
WebElement inputPassword = driver.findElement(By.xpath("//input[@type='password']"))
inputPassword.clear()
inputPassword.sendKeys("passwordsalah123")

WebUI.delay(1)

// 6. Klik tombol login
WebElement tombolSubmitLogin = driver.findElement(By.id("send_message"))
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombolSubmitLogin)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSubmitLogin)

WebUI.delay(3)

// --- VALIDASI: Pesan error harus muncul ---
String isiHalamanWeb = driver.getPageSource()
boolean isPesanErrorMuncul = isiHalamanWeb.contains("Failure")

assert isPesanErrorMuncul == true : "GAGAL! Sistem seharusnya menampilkan pesan error untuk password salah."
println("Verifikasi Sukses! Sistem menampilkan pesan error untuk password salah.")

// Tutup browser
WebUI.closeBrowser()