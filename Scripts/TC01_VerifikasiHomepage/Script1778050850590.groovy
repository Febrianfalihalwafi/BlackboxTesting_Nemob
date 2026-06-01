import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl("https://nemob.id/id")
WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver

// --- SILANG IKLAN ---
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

// ============================================
// STEP 2: VALIDASI ELEMEN HOMEPAGE
// ============================================

// 1. LOGO
try {
    boolean logoFound = driver.findElement(
        By.cssSelector("div#logo img.logo-1")
    ).isDisplayed()
    assert logoFound, "Logo tidak tampil"
    println("✓ Logo berhasil tampil")
} catch (Exception e) {
    println("✗ Logo tidak ditemukan: " + e.message)
    assert false
}

// 2. MENU NAVIGASI
try {
    boolean navFound = driver.findElement(
        By.cssSelector("ul#mainmenu")
    ).isDisplayed()
    assert navFound, "Menu navigasi tidak tampil"
    println("✓ Menu navigasi berhasil tampil")
} catch (Exception e) {
    println("✗ Menu navigasi tidak ditemukan: " + e.message)
    assert false
}

// 3. ITEM MENU "Sewa Mobil"
try {
    boolean menuItemFound = driver.findElement(
        By.xpath("//ul[@id='mainmenu']//a[contains(@class,'menu-item') and contains(.,'Sewa Mobil')]")
    ).isDisplayed()
    assert menuItemFound, "Menu 'Sewa Mobil' tidak tampil"
    println("✓ Menu 'Sewa Mobil' tampil di navigasi")
} catch (Exception e) {
    println("✗ Menu 'Sewa Mobil' tidak ditemukan: " + e.message)
    assert false
}

// 4. BANNER
try {
    boolean bannerFound = driver.findElement(
        By.cssSelector("div.home-fullscreen h2.mb-2")
    ).isDisplayed()
    assert bannerFound, "Banner tidak tampil"
    println("✓ Banner berhasil tampil")
} catch (Exception e) {
    println("✗ Banner tidak ditemukan: " + e.message)
    assert false
}

// 5. TEKS BANNER
try {
    String bannerText = driver.findElement(
        By.cssSelector("div.home-fullscreen h2.mb-2")
    ).getText()
    assert bannerText.toLowerCase().contains("jelajahi"),
        "Teks banner tidak sesuai: " + bannerText
    println("✓ Teks banner sesuai: " + bannerText)
} catch (Exception e) {
    println("✗ Teks banner tidak sesuai: " + e.message)
    assert false
}

// 6. KONTEN UTAMA
try {
    boolean contentFound = driver.findElement(
        By.cssSelector("div#content")
    ).isDisplayed()
    assert contentFound, "Konten utama tidak tampil"
    println("✓ Konten utama berhasil tampil")
} catch (Exception e) {
    println("✗ Konten utama tidak ditemukan: " + e.message)
    assert false
}

println("=== TC01 PASSED: Semua elemen homepage terverifikasi ===")
WebUI.closeBrowser()