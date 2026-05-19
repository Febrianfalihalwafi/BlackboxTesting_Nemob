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

// 4. Klik tombol Daftar di halaman login
try {
    WebElement tombolDaftar = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'Daftar') or contains(text(),'daftar') or contains(text(),'Register')]")
    ))
    js.executeScript("arguments[0].click();", tombolDaftar)
} catch (Exception e) {
    WebElement tombolDaftar = driver.findElement(By.xpath("//a[contains(@href,'register') or contains(@href,'daftar')]"))
    js.executeScript("arguments[0].click();", tombolDaftar)
}
WebUI.delay(3)

// --- VALIDASI: Halaman registrasi tampil dengan form lengkap ---
String isiHalamanWeb = driver.getPageSource()
boolean isHalamanRegistrasiTampil = isiHalamanWeb.contains("Nama Depan") &&
                                    isiHalamanWeb.contains("Nama Belakang") &&
                                    isiHalamanWeb.contains("Masukan email") &&
                                    isiHalamanWeb.contains("Masukan kata sandi") &&
                                    isiHalamanWeb.contains("Konfirmasi kata sandi") &&
									isiHalamanWeb.contains("+62")

assert isHalamanRegistrasiTampil == true : "GAGAL! Halaman registrasi tidak menampilkan form lengkap."
println("Verifikasi Sukses! Halaman registrasi menampilkan form pendaftaran lengkap.")

// Tutup browser
WebUI.closeBrowser()