import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
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
    println("Popup iklan berhasil ditutup")
    WebUI.delay(2)
}
catch(Exception e) {
    println("Popup tidak muncul atau sudah ditutup")
}

// --- PROSES KLIK TOMBOL 'MASUK' DI NAVBAR ---
// 1. Cari tombol "Masuk" di navbar
WebElement tombolMasuk = driver.findElement(By.xpath("//a[contains(text(),'Masuk')] | //button[contains(text(),'Masuk')]"))

// 2. Klik tombol Masuk menggunakan JavaScript click agar pop-up login muncul
js.executeScript("arguments[0].click();", tombolMasuk)

// 3. Jeda waktu 4 detik memberikan waktu agar form login selesai dimuat sempurna
WebUI.delay(4)


// --- BAGIAN UTAMA YANG DIPERBAIKI (PENCARIAN ELEMEN FORM) ---

// 4. Cari elemen input Email dengan penanganan cadangan jika XPath utama gagal
WebElement inputEmail
try {
    inputEmail = driver.findElement(By.xpath("//input[@type='email']"))
} catch (Exception ex) {
    inputEmail = driver.findElement(By.xpath("//input[contains(@placeholder,'Email') or contains(@placeholder,'email')]"))
}

// 5. Cari elemen input Password dengan penanganan cadangan jika XPath utama gagal
WebElement inputPassword
try {
    inputPassword = driver.findElement(By.xpath("//input[@type='password']"))
} catch (Exception ex) {
    inputPassword = driver.findElement(By.xpath("//input[contains(@placeholder,'Password') or contains(@placeholder,'Sandi')]"))
}

// 6. Validasi/Assertion untuk memastikan kedua kolom input tersebut tampil nyata di layar
assert inputEmail.isDisplayed()
assert inputPassword.isDisplayed()

println("Verifikasi Sukses! Form input email dan password login berhasil ditampilkan di layar.")

// 7. Tutup browser setelah pengujian sukses selesai
WebUI.closeBrowser()