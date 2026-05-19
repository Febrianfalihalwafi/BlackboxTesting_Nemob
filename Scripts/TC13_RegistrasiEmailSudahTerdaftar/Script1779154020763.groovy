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

// 4. Klik tombol Daftar
try {
    WebElement tombolDaftar = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//a[contains(text(),'Daftar') or contains(text(),'daftar')]")
    ))
    js.executeScript("arguments[0].click();", tombolDaftar)
} catch (Exception e) {
    WebElement tombolDaftar = driver.findElement(By.xpath("//a[contains(@href,'register')]"))
    js.executeScript("arguments[0].click();", tombolDaftar)
}
WebUI.delay(3)

// 5. Isi form dengan email yang sudah terdaftar
driver.findElement(By.xpath("//input[contains(@placeholder,'Nama Depan')]")).sendKeys("Test")
driver.findElement(By.xpath("//input[contains(@placeholder,'Nama Belakang')]")).sendKeys("User")
driver.findElement(By.xpath("//input[contains(@placeholder,'email') or contains(@placeholder,'Email')]")).sendKeys("inisiatifsendiri12@gmail.com")
driver.findElement(By.xpath("//input[contains(@placeholder,'kata sandi') and not(contains(@placeholder,'Konfirmasi'))]")).sendKeys("Test12345!")
driver.findElement(By.xpath("//input[contains(@placeholder,'Konfirmasi')]")).sendKeys("Test12345!")
driver.findElement(By.xpath("//input[@type='tel']")).sendKeys("81234567890")

WebUI.delay(1)

// 6. Klik tombol Sign Up
WebElement tombolSignUp = driver.findElement(By.xpath("//button[contains(text(),'Sign Up')] | //input[@type='submit']"))
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombolSignUp)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSignUp)

WebUI.delay(3)

// --- VALIDASI: Pesan error email sudah terdaftar harus muncul ---
String isiHalamanWeb = driver.getPageSource()
boolean isPesanErrorMuncul = isiHalamanWeb.contains("User is already exist") ||
                          
assert isPesanErrorMuncul == true : "GAGAL! Sistem seharusnya menolak email yang sudah terdaftar."
println("Verifikasi Sukses! Sistem menampilkan pesan error untuk email sudah terdaftar.")

// Tutup browser
WebUI.closeBrowser()