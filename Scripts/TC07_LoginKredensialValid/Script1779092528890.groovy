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

// --- SILANG IKLAN POP-UP ---
try {
    WebElement tombolX = driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"))
    js.executeScript("arguments[0].click();", tombolX)
    println("Popup iklan berhasil ditutup")
    WebUI.delay(2)
} catch(Exception e) {
    println("Popup tidak muncul atau sudah ditutup")
}

// --- BUKA FORM LOGIN VIA NAVBAR ---
try {
    WebElement tombolMasukNavbar = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'Masuk')] | //button[contains(text(),'Masuk')]")
    ))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
} catch (Exception e) {
    println("Tombol Masuk via text tidak ditemukan, coba via href...")
    WebElement tombolMasukNavbar = driver.findElement(By.xpath("//a[contains(@href,'login')] | //a[contains(@href,'masuk')]"))
    js.executeScript("arguments[0].click();", tombolMasukNavbar)
}
WebUI.delay(4)

// --- MENCARI ELEMEN INPUT FORM LOGIN ---
WebElement inputEmail
try {
    inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
} catch (Exception ex) {
    try {
        inputEmail = driver.findElement(By.xpath("//input[contains(@placeholder,'Email') or contains(@placeholder,'email')]"))
    } catch (Exception ex2) {
        inputEmail = driver.findElement(By.xpath("//input[@name='email' or @id='email']"))
    }
}

WebElement inputPassword
try {
    inputPassword = driver.findElement(By.xpath("//input[@type='password']"))
} catch (Exception ex) {
    inputPassword = driver.findElement(By.xpath("//input[contains(@placeholder,'Password') or contains(@placeholder,'Sandi') or contains(@placeholder,'password')]"))
}

// --- AKSI UTAMA: MENGISI DATA & LOGIN ---
inputEmail.clear()
inputEmail.sendKeys("inisiatifsendiri12@gmail.com")
inputPassword.clear()
inputPassword.sendKeys("12345678")
WebUI.delay(1)

WebElement tombolSubmitLogin = driver.findElement(By.id("send_message"))

// Scroll ke tombol lalu klik
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombolSubmitLogin)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSubmitLogin)

// Jeda 6 detik agar autentikasi selesai
WebUI.delay(6)

// --- VALIDASI LOGIN ---
String isiHalamanWeb = driver.getPageSource()
boolean isLoginSukses = isiHalamanWeb.contains("Keluar") || 
                        isiHalamanWeb.contains("Logout") || 
                        isiHalamanWeb.contains("inisiatifsendiri12") ||
                        isiHalamanWeb.contains("dashboard") ||
                        isiHalamanWeb.contains("Dashboard")

assert isLoginSukses == true : "Login GAGAL! Halaman tidak menunjukkan indikasi login berhasil."
println("Verifikasi Sukses! User berhasil login ke dalam akun.")

// Tutup browser
WebUI.closeBrowser()