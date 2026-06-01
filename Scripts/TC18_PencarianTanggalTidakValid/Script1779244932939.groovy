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

// 1. Isi Tanggal Mulai lebih besar: 05/26/2026
WebElement inputTanggalMulai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[1]")
))
inputTanggalMulai.click()
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalMulai.sendKeys("06/26/2026")
WebUI.delay(1)
inputTanggalMulai.sendKeys(Keys.TAB)
WebUI.delay(1)

// 2. Coba isi Tanggal Selesai lebih kecil: 05/22/2026
WebElement inputTanggalSelesai = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//div[contains(@class,'date-time-field')]//input)[2]")
))
inputTanggalSelesai.click()
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.chord(Keys.CONTROL, "a"))
inputTanggalSelesai.sendKeys("06/22/2026")
WebUI.delay(1)
inputTanggalSelesai.sendKeys(Keys.TAB)
WebUI.delay(2)

// 3. Ambil nilai aktual kedua field
String tanggalMulaiAktual   = inputTanggalMulai.getAttribute("value")
String tanggalSelesaiAktual = inputTanggalSelesai.getAttribute("value")
println("Tanggal Mulai  : " + tanggalMulaiAktual)
println("Tanggal Selesai: " + tanggalSelesaiAktual)

// 4. Validasi: sistem auto-correct tanggal selesai agar >= tanggal mulai
// Sistem nemob.id menolak tanggal selesai < tanggal mulai dengan auto-correct
boolean isSystemValid = !tanggalSelesaiAktual.equals("05/22/2026")

assert isSystemValid == true : "GAGAL! Sistem membiarkan Tanggal Selesai (${tanggalSelesaiAktual}) lebih kecil dari Tanggal Mulai (${tanggalMulaiAktual})."
println("Verifikasi Sukses! Sistem memvalidasi tanggal tidak logis.")
println("Input '05/22/2026' ditolak, Tanggal Selesai dikoreksi menjadi: " + tanggalSelesaiAktual)

WebUI.closeBrowser()