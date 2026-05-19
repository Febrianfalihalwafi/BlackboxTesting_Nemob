import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

// 1. Buka homepage
WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver

WebUI.delay(3)

// --- LANGKAH SILANG IKLAN ---
try {
    // Klik tombol X popup
    WebElement tombolX = driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"))
    js.executeScript("arguments[0].click();", tombolX)
    println("Popup berhasil ditutup")
    WebUI.delay(2)
}
catch(Exception e) {
    println("Popup tidak muncul atau sudah ditutup")
}
// -------------------------------------


// --- BAGIAN YANG DIGANTI (ALUR DROPDOWN & HOVER) ---

// 1. Cari menu "Sewa Mobil" khusus yang berupa Link (tag <a>) di navbar agar tidak salah klik
WebElement menu = driver.findElement(By.xpath("//a[contains(., 'Sewa Mobil')]"))

// 2. Pastikan halaman ter-scroll ke arah menu navbar tersebut
js.executeScript("arguments[0].scrollIntoView(true);", menu)
WebUI.delay(1)

// 3. Lakukan hover (mengarahkan kursor) menggunakan Actions bawaan Selenium
try {
    Actions actions = new Actions(driver)
    actions.moveToElement(menu).build().perform()
    WebUI.delay(1)
} 
catch (Exception e) {
    // Jika hover biasa gagal, paksa browser membuka dropdown lewat simulasi event JavaScript
    js.executeScript("var evObj = document.createEvent('MouseEvents'); evObj.initEvent('mouseenter', true, false); arguments[0].dispatchEvent(evObj);", menu)
    WebUI.delay(1)
}

// 4. Cari Submenu "Dengan Supir" di dalam dropdown yang berhasil muncul
WebElement submenu = driver.findElement(By.xpath("//span[contains(text(),'Dengan Supir')] | //a[contains(.,'Dengan Supir')]"))

// 5. Validasi apakah sub-menu tersebut sudah terlihat (tampil) di layar
assert submenu.isDisplayed()
println("Dropdown berhasil tampil dan diverifikasi!")

// 6. Selesai dan tutup browser
WebUI.closeBrowser()