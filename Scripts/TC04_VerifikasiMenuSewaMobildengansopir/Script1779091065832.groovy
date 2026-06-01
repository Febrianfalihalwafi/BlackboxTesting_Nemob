import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

// 1. Buka browser dan arahkan ke website
WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver

WebUI.delay(3)

// --- SILANG IKLAN ---
try {
    WebElement tombolX = driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"))
    js.executeScript("arguments[0].click();", tombolX)
    println("Popup berhasil ditutup")
    WebUI.delay(2)
}
catch(Exception e) {
    println("Popup tidak muncul atau sudah ditutup")
}

// --- PROSES HOVER MENU SEWA MOBIL ---
WebElement menu = driver.findElement(By.xpath("//a[contains(., 'Sewa Mobil')]"))
js.executeScript("arguments[0].scrollIntoView(true);", menu)
WebUI.delay(1)

try {
    Actions actions = new Actions(driver)
    actions.moveToElement(menu).build().perform()
    WebUI.delay(1)
} 
catch (Exception e) {
    js.executeScript("var evObj = document.createEvent('MouseEvents'); evObj.initEvent('mouseenter', true, false); arguments[0].dispatchEvent(evObj);", menu)
    WebUI.delay(1)
}

// --- AKSI BARU: KLIK SUBMENU 'DENGAN SUPIR' ---
// 1. Cari elemen submenu "Dengan Supir"
WebElement submenu = driver.findElement(By.xpath("//span[contains(text(),'Dengan Supir')] | //a[contains(.,'Dengan Supir')]"))

// 2. Klik menggunakan JavaScript Click
js.executeScript("arguments[0].click();", submenu)

// 3. Beri jeda waktu agar browser selesai melakukan loading perpindahan halaman
WebUI.delay(3)

// --- VERIFIKASI URL HALAMAN SEWA MOBIL ---
String urlSaatIni = driver.getCurrentUrl()
String urlDiharapkan = "https://nemob.id/id/sewa-rental-mobil-murah"

assert urlSaatIni.equals(urlDiharapkan)
println("Verifikasi Berhasil! Pengguna diarahkan ke halaman: " + urlSaatIni)

// Tutup browser
WebUI.closeBrowser()