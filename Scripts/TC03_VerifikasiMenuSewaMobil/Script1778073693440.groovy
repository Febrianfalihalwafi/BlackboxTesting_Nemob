import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

WebUI.openBrowser('')
WebUI.navigateToUrl('https://nemob.id/id')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver
WebUI.delay(3)

// --- TUTUP POPUP IKLAN ---
try {
    WebElement tombolX = driver.findElement(By.xpath(
        "//*[name()='svg' and contains(@class,'yarl__icon')]/parent::*"
    ))
    js.executeScript("arguments[0].click();", tombolX)
    println("✓ Popup berhasil ditutup")
    WebUI.delay(2)
} catch (Exception e) {
    println("- Popup tidak muncul atau sudah ditutup")
}

// --- HOVER MENU SEWA MOBIL ---
WebElement menu = driver.findElement(By.xpath(
    "//ul[@id='mainmenu']//a[@class='menu-item' and contains(.,'Sewa Mobil')]"
))
js.executeScript("arguments[0].scrollIntoView(true);", menu)
WebUI.delay(1)

try {
    Actions actions = new Actions(driver)
    actions.moveToElement(menu).build().perform()
    WebUI.delay(1)
} catch (Exception e) {
    js.executeScript(
        "var evObj = document.createEvent('MouseEvents');" +
        "evObj.initEvent('mouseenter', true, false);" +
        "arguments[0].dispatchEvent(evObj);", menu
    )
    WebUI.delay(1)
}

// --- VERIFIKASI SEMUA ITEM DROPDOWN (sesuai inspect element) ---
// Semua item adalah <a class="menu-item"> di dalam ul > li
List<String> expectedItems = [
    'Dengan Supir',
    'Lepas Kunci',
    'Nemob Untuk Bisnis',
    'Mobil Spesial',
    'Perusahaan'
]

List<String> notFound = []

for (String item : expectedItems) {
    try {
        WebElement submenu = driver.findElement(By.xpath(
            "//ul[@id='mainmenu']//ul//a[@class='menu-item' and contains(.,'" + item + "')]"
        ))
        assert submenu.isDisplayed(), "Item '" + item + "' tidak tampil"
        println("✓ Dropdown item tampil: " + item)
    } catch (Exception e) {
        println("✗ Dropdown item TIDAK ditemukan: " + item)
        notFound.add(item)
    }
}

// --- HASIL AKHIR ---
if (notFound.isEmpty()) {
    println("=== TC03 PASSED: Semua item dropdown berhasil diverifikasi ===")
} else {
    println("=== TC03 FAILED: Item tidak ditemukan: " + notFound.join(", ") + " ===")
    assert false, "Beberapa item dropdown tidak tampil: " + notFound.join(", ")
}

WebUI.closeBrowser()