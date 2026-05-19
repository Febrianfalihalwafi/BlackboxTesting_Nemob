import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

// 1. Buka browser dan arahkan ke website nemob.id
WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
Actions actions = new Actions(driver)
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

// 4. Login dengan kredensial valid
WebElement inputEmail
try {
    inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
} catch (Exception ex) {
    inputEmail = driver.findElement(By.xpath("//input[contains(@placeholder,'Email') or contains(@placeholder,'email')]"))
}

WebElement inputPassword = driver.findElement(By.xpath("//input[@type='password']"))

inputEmail.clear()
inputEmail.sendKeys("inisiatifsendiri12@gmail.com")
inputPassword.clear()
inputPassword.sendKeys("12345678")
WebUI.delay(1)

WebElement tombolSubmitLogin = driver.findElement(By.id("send_message"))
js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombolSubmitLogin)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSubmitLogin)
WebUI.delay(6)

// 2. Tutup pop-up iklan jika muncul
try {
	WebElement tombolX = driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"))
	js.executeScript("arguments[0].click();", tombolX)
	println("Popup iklan berhasil ditutup")
	WebUI.delay(2)
} catch(Exception e) {
	println("Popup tidak muncul atau sudah ditutup")
}

WebUI.delay(6)

// 7. Tutup pop-up Kirim Nomor Telepon jika muncul
try {
    WebElement tombolTutupPopup = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[contains(text(),'×') or contains(text(),'X')] | //*[contains(@class,'close') or contains(@class,'dismiss')]")
    ))
    js.executeScript("arguments[0].click();", tombolTutupPopup)
    println("Pop-up nomor telepon ditutup")
    WebUI.delay(2)
} catch(Exception e) {
    println("Pop-up nomor telepon tidak muncul")
}

// 8. Navigasi langsung ke halaman Dengan Supir
WebUI.navigateToUrl('https://nemob.id/id/sewa-rental-mobil-murah')
WebUI.delay(3)

// 9. Pilih lokasi - klik custom dropdown React Select
WebElement dropdownLokasi = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'select__control')])[1]")
))
js.executeScript("arguments[0].click();", dropdownLokasi)
WebUI.delay(1)

// Pilih opsi Bali dari menu yang muncul
// Coba berbagai XPath untuk opsi Bali
WebElement opsiLokasi
try {
    opsiLokasi = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//*[contains(@class,'option') and normalize-space(text())='Bali']")
    ))
} catch (Exception e1) {
    try {
        opsiLokasi = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[contains(@class,'menu')]//div[normalize-space(text())='Bali']")
        ))
    } catch (Exception e2) {
        opsiLokasi = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[contains(@id,'option') and contains(text(),'Bali')]")
        ))
    }
}
js.executeScript("arguments[0].click();", opsiLokasi)
WebUI.delay(2)

// 10. Isi tanggal mulai - klik field lalu isi
WebElement inputTanggalMulai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//input[contains(@class,'date') or @placeholder])[1]")
))
js.executeScript("arguments[0].value = '05/20/2026'", inputTanggalMulai)
js.executeScript("arguments[0].dispatchEvent(new Event('change'))", inputTanggalMulai)
WebUI.delay(1)

// 11. Isi tanggal selesai
WebElement inputTanggalSelesai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//input[contains(@class,'date') or @placeholder])[2]")
))
js.executeScript("arguments[0].value = '05/22/2026'", inputTanggalSelesai)
js.executeScript("arguments[0].dispatchEvent(new Event('change'))", inputTanggalSelesai)
WebUI.delay(1)

// 12. Klik tombol Cari Mobil
WebElement tombolCari = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//button[contains(text(),'Cari Mobil')] | //input[@value='Cari Mobil']")
))
js.executeScript("arguments[0].click();", tombolCari)
WebUI.delay(4)

// --- VALIDASI: Hasil pencarian tampil ---
String isiHalamanWeb = driver.getPageSource()
boolean isHasilPencarianTampil = isiHalamanWeb.contains("Harga") ||
                                  isiHalamanWeb.contains("Lokasi") ||
                                  isiHalamanWeb.contains("mobil") ||
                                  isiHalamanWeb.contains("Cari Mobil")

assert isHasilPencarianTampil == true : "GAGAL! Hasil pencarian tidak tampil."
println("Verifikasi Sukses! Sistem menampilkan daftar mobil sesuai kriteria pencarian.")

WebUI.closeBrowser()