import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor

WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebUI.delay(3)

// --- TUTUP POPUP IKLAN ---
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

// --- KLIK TOMBOL MASUK DI NAVBAR ---
WebElement tombolMasuk = driver.findElement(By.xpath(
    "//a[contains(text(),'Masuk')] | //button[contains(text(),'Masuk')]"
))
js.executeScript("arguments[0].click();", tombolMasuk)
WebUI.delay(4)

// --- VERIFIKASI FORM LOGIN ---

// 1. Verifikasi input email tampil
WebElement inputEmail
try {
    inputEmail = driver.findElement(By.xpath("//input[@type='email']"))
} catch (Exception ex) {
    inputEmail = driver.findElement(By.xpath(
        "//input[contains(@placeholder,'Email') or contains(@placeholder,'email')]"
    ))
}
assert inputEmail.isDisplayed(), "Input email tidak tampil"
println("✓ Input email berhasil tampil")

// 2. Verifikasi input password tampil
WebElement inputPassword
try {
    inputPassword = driver.findElement(By.xpath("//input[@type='password']"))
} catch (Exception ex) {
    inputPassword = driver.findElement(By.xpath(
        "//input[contains(@placeholder,'Password') or contains(@placeholder,'Sandi')]"
    ))
}
assert inputPassword.isDisplayed(), "Input password tidak tampil"
println("✓ Input password berhasil tampil")

// 3. Verifikasi tombol login/masuk tampil
WebElement tombolLogin
try {
    tombolLogin = driver.findElement(By.id("send_message"))
} catch (Exception ex) {
    tombolLogin = driver.findElement(By.xpath(
        "//button[contains(text(),'Masuk') or contains(text(),'Login')] | " +
        "//input[@type='submit']"
    ))
}
assert tombolLogin.isDisplayed(), "Tombol login tidak tampil"
println("✓ Tombol login berhasil tampil")

println("=== TC06 PASSED: Form login tampil lengkap ===")
WebUI.closeBrowser()