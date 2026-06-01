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

// 1. Pilih Lokasi - Jakarta
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

// 2. Isi Tanggal Mulai
WebElement inputTanggalMulai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[1]")
))
inputTanggalMulai.click()
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalMulai.sendKeys("05/22/2026")
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.TAB)
WebUI.delay(1)

// 3. Isi Tanggal Selesai
WebElement inputTanggalSelesai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[2]")
))
inputTanggalSelesai.click()
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalSelesai.sendKeys("05/26/2026")
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.TAB)
WebUI.delay(2)

js.executeScript("document.activeElement.blur();")
WebUI.delay(1)

// 4. Klik tombol Cari Mobil
WebElement tombolCari = wait.until(ExpectedConditions.elementToBeClickable(
    By.id("send_message")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolCari)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolCari)
WebUI.delay(5)

// 5. Klik tombol Sewa Mobil pertama
WebElement tombolSewaMobil = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//a[contains(@class,'btn-main') and contains(text(),'Sewa Mobil')])[1]")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolSewaMobil)
WebUI.delay(2)
js.executeScript("arguments[0].click();", tombolSewaMobil)
WebUI.delay(5)

// 6. Klik foto mobil di halaman detail untuk buka galeri
// Foto menggunakan swiper-slide dengan class swiper-slide-car-image
WebElement fotoMobil = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'swiper-slide-car-image')]//img)[1]")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", fotoMobil)
WebUI.delay(2)
js.executeScript("arguments[0].click();", fotoMobil)
WebUI.delay(3)

// 7. Validasi galeri foto terbuka (yarl lightbox)
String isiHalaman = driver.getPageSource()
boolean isGaleriTerbuka = isiHalaman.contains("yarl") ||
                          isiHalaman.contains("yarl__slide") ||
                          isiHalaman.contains("yarl__carousel")

assert isGaleriTerbuka == true : "GAGAL! Galeri foto tidak terbuka setelah klik foto."
println("Verifikasi Sukses! Galeri foto berhasil dibuka, user dapat melihat foto lainnya.")

WebUI.closeBrowser()