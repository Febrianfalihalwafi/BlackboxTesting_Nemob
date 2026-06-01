import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id/sewa-rental-mobil-murah-lepas-kunci')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15))
WebUI.delay(3)

// Step 1: Pilih Lokasi - Jakarta
WebElement dropdownLokasi = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'select__control')])[1]")
))
dropdownLokasi.click()
WebUI.delay(1)
driver.findElement(By.xpath("(//div[contains(@class,'select__control')])[1]//input")).sendKeys("Jakarta")
WebUI.delay(2)
wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//div[contains(@class,'select__option')]")
)).click()
WebUI.delay(1)

// Step 2: Isi Tanggal Mulai
WebElement inputTanggalMulai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[1]")
))
inputTanggalMulai.click()
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalMulai.sendKeys("06/22/2026")
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.TAB)
WebUI.delay(1)

// Step 3: Isi Tanggal Selesai
WebElement inputTanggalSelesai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[2]")
))
inputTanggalSelesai.click()
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalSelesai.sendKeys("06/26/2026")
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.TAB)
WebUI.delay(2)
js.executeScript("document.activeElement.blur();")
WebUI.delay(1)

// Step 4: Klik tombol Cari Mobil
WebElement tombolCari = wait.until(ExpectedConditions.elementToBeClickable(
    By.id("send_message")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolCari)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolCari)
WebUI.delay(5)

// Step 5: Klik tombol "Sewa Mobil" pada kartu mobil pertama
WebElement tombolSewaMobil = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//a[contains(@class,'btn-main') and contains(text(),'Sewa Mobil')])[1]")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolSewaMobil)
WebUI.delay(2)
js.executeScript("arguments[0].click();", tombolSewaMobil)
WebUI.delay(5)

// Step 6: Sudah di halaman Detail Kendaraan - langsung klik tombol "Pesan"
WebElement tombolPesan = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath(
        "//button[normalize-space(text())='Pesan'] | " +
        "//a[normalize-space(text())='Pesan'] | " +
        "//button[contains(@class,'btn') and contains(text(),'Pesan')] | " +
        "//a[contains(@class,'btn') and contains(text(),'Pesan')]"
    )
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolPesan)
WebUI.delay(2)
js.executeScript("arguments[0].click();", tombolPesan)
WebUI.delay(5)

// Step 7: Validasi - User diarahkan ke form pemesanan atau halaman login
String currentUrl = driver.getCurrentUrl()
String pageSource = driver.getPageSource()

boolean diarahkanKeLogin = currentUrl.contains("login") || currentUrl.contains("sign-in") ||
                            pageSource.contains("Masuk") || pageSource.contains("Login")

boolean diarahkanKePemesanan = currentUrl.contains("pesan") || currentUrl.contains("booking") ||
                                currentUrl.contains("order") || currentUrl.contains("checkout") ||
                                pageSource.contains("Form Pemesanan") || pageSource.contains("Data Pemesan")

if (diarahkanKeLogin) {
    println("INFO: User belum login - Diarahkan ke halaman login. Alur pemesanan berjalan dengan benar.")
    assert diarahkanKeLogin == true : "GAGAL! User tidak diarahkan ke halaman login."
} else if (diarahkanKePemesanan) {
    println("SUKSES: User sudah login - Diarahkan ke form pemesanan. Alur pemesanan dimulai.")
    assert diarahkanKePemesanan == true : "GAGAL! User tidak diarahkan ke form pemesanan."
} else {
    assert false : "GAGAL! Tombol Pesan tidak menghasilkan respons yang diharapkan. URL saat ini: " + currentUrl
}

WebUI.closeBrowser()