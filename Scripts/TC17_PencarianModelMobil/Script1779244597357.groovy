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
WebUI.navigateToUrl('https://nemob.id/id/sewa-rental-mobil-murah')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15))
WebUI.delay(3)

// 1. Pilih Lokasi
WebElement dropdownLokasi = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'select__control')])[1]")
))
dropdownLokasi.click()
WebUI.delay(1)
driver.findElement(By.xpath("(//div[contains(@class,'select__control')])[1]//input")).sendKeys("Bali")
WebUI.delay(2)
wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//div[contains(@class,'select__option')]")
)).click()
WebUI.delay(1)

// 2. Pilih Model Mobil - dropdown kedua (index 2)
WebElement dropdownModel = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'select__control')])[2]")
))
dropdownModel.click()
WebUI.delay(1)
driver.findElement(By.xpath("(//div[contains(@class,'select__control')])[2]//input")).sendKeys("Hiace")
WebUI.delay(2)
wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//div[contains(@class,'select__option')]")
)).click()
WebUI.delay(1)

// 3. Isi Tanggal Mulai
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

// 4. Isi Tanggal Selesai
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

// 5. Klik tombol Cari Mobil
WebElement tombolCari = wait.until(ExpectedConditions.elementToBeClickable(
    By.id("send_message")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolCari)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolCari)
WebUI.delay(5)


// 6. Validasi
String isiHalaman = driver.getPageSource()

boolean isFilterBerhasil =
        isiHalaman.toLowerCase().contains("hiace")

assert isFilterBerhasil :
       "GAGAL! Sistem tidak menampilkan mobil Hiace sesuai filter."

println("Verifikasi Sukses! Filter jenis mobil Hiace berfungsi.")

WebUI.closeBrowser()