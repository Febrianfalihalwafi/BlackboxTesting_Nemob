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

// Step 6 & 7: Di halaman detail kendaraan - klik tombol "Pesan"
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

// Step 8: Diarahkan ke halaman login
String urlSetelahPesan = driver.getCurrentUrl()
assert urlSetelahPesan.contains("login") || urlSetelahPesan.contains("sign-in") || driver.getPageSource().contains("Login") \
    : "GAGAL! Seharusnya diarahkan ke halaman login terlebih dahulu."
println("INFO: Diarahkan ke halaman login.")

// Step 9: Login dengan akun valid

WebElement inputEmail
try{
    inputEmail = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@type='email']")
        )
    )
}catch(Exception e){
    inputEmail = driver.findElement(
        By.xpath("//input[@name='email'] | //input[contains(@placeholder,'Email')]")
    )
}

inputEmail.clear()
inputEmail.sendKeys("inisiatifsendiri12@gmail.com")


WebElement inputPassword
try{
    inputPassword = driver.findElement(
        By.xpath("//input[@type='password']")
    )
}catch(Exception e){
    inputPassword = driver.findElement(
        By.xpath("//input[contains(@placeholder,'Password')]")
    )
}

inputPassword.clear()
inputPassword.sendKeys("12345678")

WebUI.delay(2)


// ===== INI BAGIAN TOMBOL LOGIN DARI SCRIPT YANG BERHASIL =====

WebElement tombolSubmitLogin = wait.until(
    ExpectedConditions.presenceOfElementLocated(
        By.id("send_message")
    )
)

// scroll
js.executeScript(
    "arguments[0].scrollIntoView({block:'center'});",
    tombolSubmitLogin
)

WebUI.delay(1)

// klik JS
js.executeScript(
    "arguments[0].click();",
    tombolSubmitLogin
)

println("Login diklik")

WebUI.delay(6)

// ===== TUTUP POPUP KIRIM NOMOR TELEPON =====
try {

    WebElement tombolClose = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[@id='btn-closephonenumber-submit-modal']")
        )
    )

    WebUI.delay(2)

    // scroll ke tombol X
    js.executeScript(
        "arguments[0].scrollIntoView({block:'center'});",
        tombolClose
    )

    WebUI.delay(1)

    // klik tombol X
    js.executeScript(
        "arguments[0].click();",
        tombolClose
    )

    WebUI.delay(2)

    // paksa hapus modal jika masih ada
    js.executeScript("""
        document.querySelectorAll('.modal-backdrop').forEach(e=>e.remove());
        document.body.classList.remove('modal-open');
        document.body.style='';
    """)

    println("Popup nomor telepon berhasil ditutup")

} catch(Exception e){

    println("Popup nomor telepon tidak muncul / gagal ditutup")

}

// Step 10: Biarkan semua field form pemesanan KOSONG - tidak diisi apapun
println("INFO: Form pemesanan dibiarkan kosong, tidak mengisi field apapun.")
WebUI.delay(2)

// Step 11: Klik tombol "Pesan Sekarang" dengan form kosong
WebElement tombolPesanSekarang = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath(
        "//input[@id='send_message' and @value='Pesan sekarang'] | " +
        "//input[@type='submit' and contains(@value,'Pesan')] | " +
        "//button[contains(text(),'Pesan Sekarang')] | " +
        "//button[@type='submit']"
    )
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolPesanSekarang)
WebUI.delay(2)
js.executeScript("arguments[0].click();", tombolPesanSekarang)
WebUI.delay(3)

// Validasi: Sistem harus menampilkan pesan error pada field wajib
String pageSourceAfter = driver.getPageSource()

boolean adaPesanError = 
		pageSourceAfter.contains("Field is required") ||
        pageSourceAfter.contains("Please complete and check your form again")

assert adaPesanError : "GAGAL! Pesan error validasi field wajib tidak muncul saat form kosong."

println("SUKSES: Sistem menampilkan pesan error pada field wajib. Form kosong tidak dapat diproses.")

WebUI.closeBrowser()