import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

// 1. Buka browser dan arahkan ke website nemob.id
WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver

WebUI.delay(3)

// --- LANGKAH SILANG IKLAN POP-UP ---
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
    // Jalur alternatif jika hover native Selenium terhambat
    js.executeScript("var evObj = document.createEvent('MouseEvents'); evObj.initEvent('mouseenter', true, false); arguments[0].dispatchEvent(evObj);", menu)
    WebUI.delay(1)
}

// --- AKSI UTAMA: KLIK SUBMENU 'LEPAS KUNCI' ---
// 1. Cari elemen submenu "Lepas Kunci" di dalam kontainer dropdown
WebElement submenuLepasKunci = driver.findElement(By.xpath("//span[contains(text(),'Lepas Kunci')] | //a[contains(.,'Lepas Kunci')]"))

// 2. Klik submenu menggunakan JavaScript Click agar terhindar dari tumpang tindih layout
js.executeScript("arguments[0].click();", submenuLepasKunci)

// 3. Beri jeda waktu 3 detik agar proses loading halaman baru selesai seutuhnya
WebUI.delay(3)

// --- VERIFIKASI URL HALAMAN SEWA MOBIL LEPAS KUNCI ---
String urlSaatIni = driver.getCurrentUrl()
String urlDiharapkan = "https://nemob.id/id/sewa-rental-mobil-murah-lepas-kunci"

// Melakukan pengecekan kecocokan URL aktif dengan kriteria sukses dari dokumen Excel
assert urlSaatIni.equals(urlDiharapkan)
println("Verifikasi Sukses! Pengguna berhasil diarahkan ke halaman: " + urlSaatIni)

// Tutup browser
WebUI.closeBrowser()