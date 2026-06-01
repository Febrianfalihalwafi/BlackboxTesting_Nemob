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

// ======================================================
// Step 1: Pilih Lokasi - Jakarta
// ======================================================
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

// ======================================================
// Step 2: Isi Tanggal Mulai
// ======================================================
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

// ======================================================
// Step 3: Isi Tanggal Selesai
// ======================================================
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

// ======================================================
// Step 4: Klik tombol Cari Mobil
// ======================================================
WebElement tombolCari = wait.until(ExpectedConditions.elementToBeClickable(
    By.id("send_message")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolCari)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolCari)
WebUI.delay(5)

// ======================================================
// Step 5: Klik tombol "Sewa Mobil" pada kartu mobil pertama
// ======================================================
WebElement tombolSewaMobil = wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("(//a[contains(@class,'btn-main') and contains(text(),'Sewa Mobil')])[1]")
))
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolSewaMobil)
WebUI.delay(2)
js.executeScript("arguments[0].click();", tombolSewaMobil)
WebUI.delay(5)

// ======================================================
// Step 6 & 7: Klik tombol "Pesan" di halaman detail kendaraan
// ======================================================
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

// ======================================================
// Step 8: Verifikasi diarahkan ke halaman login
// ======================================================
String urlSetelahPesan = driver.getCurrentUrl()
assert urlSetelahPesan.contains("login") || urlSetelahPesan.contains("sign-in") || driver.getPageSource().contains("Login") \
    : "GAGAL! Seharusnya diarahkan ke halaman login terlebih dahulu."
println("INFO: Diarahkan ke halaman login.")

// ======================================================
// Step 9: Login dengan akun valid
// ======================================================

// -- Input Email --
WebElement inputEmail
try {
    inputEmail = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']"))
    )
} catch (Exception e) {
    inputEmail = driver.findElement(
        By.xpath("//input[@name='email'] | //input[contains(@placeholder,'Email')]")
    )
}
inputEmail.clear()
inputEmail.sendKeys("inisiatifsendiri12@gmail.com")

// -- Input Password --
WebElement inputPassword
try {
    inputPassword = driver.findElement(By.xpath("//input[@type='password']"))
} catch (Exception e) {
    inputPassword = driver.findElement(By.xpath("//input[contains(@placeholder,'Password')]"))
}
inputPassword.clear()
inputPassword.sendKeys("12345678")
WebUI.delay(2)

// -- Klik Tombol Login --
WebElement tombolSubmitLogin = wait.until(
    ExpectedConditions.presenceOfElementLocated(By.id("send_message"))
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolSubmitLogin)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolSubmitLogin)
println("Login diklik")
WebUI.delay(6)

// ======================================================
// Tutup Popup Kirim Nomor Telepon (jika muncul)
// ======================================================
try {
    WebElement tombolClose = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[@id='btn-closephonenumber-submit-modal']")
        )
    )
    WebUI.delay(2)
    js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolClose)
    WebUI.delay(1)
    js.executeScript("arguments[0].click();", tombolClose)
    WebUI.delay(2)
    js.executeScript("""
        document.querySelectorAll('.modal-backdrop').forEach(e=>e.remove());
        document.body.classList.remove('modal-open');
        document.body.style='';
    """)
    println("Popup nomor telepon berhasil ditutup")
} catch (Exception e) {
    println("Popup nomor telepon tidak muncul / gagal ditutup")
}

// ======================================================
// STEP 10: ISI SEMUA FIELD WAJIB (KECUALI KTP & SIM)
// ======================================================

// -----------------------------------------------
// DATA PRIBADI - INPUT ALAMAT
// inspect: id="address", name="address"
// -----------------------------------------------
WebElement inputAlamat = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("address"))
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", inputAlamat)
WebUI.delay(1)
inputAlamat.clear()
inputAlamat.sendKeys("Jl Testing No 123 Jakarta")
WebUI.delay(1)

// ======================================================
// DATA KERABAT DEKAT
// ======================================================

// -----------------------------------------------
// NAMA KERABAT
// inspect: id="relativefullname" (lowercase), name="relativefullname"
// -----------------------------------------------
WebElement namaKerabat = wait.until(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//input[@id='relativefullname' or @id='relativeFullname' or @name='relativefullname' or @name='relativeFullname']")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", namaKerabat)
WebUI.delay(1)
namaKerabat.clear()
namaKerabat.sendKeys("Budi Testing")
WebUI.delay(1)

// -----------------------------------------------
// NOMOR TELEPON KERABAT
// inspect image 2: class="PhoneInputInput", name="relativephonenumber", value="+62"
// PERBAIKAN v3: gunakan JS untuk set value langsung lalu trigger React onChange event
// -----------------------------------------------
WebElement teleponKerabat = wait.until(
    ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("input.PhoneInputInput[name='relativephonenumber']")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", teleponKerabat)
WebUI.delay(1)

// Set nilai via JavaScript (karena React controlled component)
js.executeScript("""
    var input = arguments[0];
    var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;
    nativeInputValueSetter.call(input, '81234567890');
    input.dispatchEvent(new Event('input', { bubbles: true }));
    input.dispatchEvent(new Event('change', { bubbles: true }));
""", teleponKerabat)

WebUI.delay(1)
println("INFO: Nomor telepon kerabat diisi via JS: 81234567890")

// -----------------------------------------------
// ALAMAT KERABAT
// inspect: id="relativeaddress", name="relativeaddress"
// -----------------------------------------------
WebElement alamatKerabat = wait.until(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//input[@id='relativeaddress' or @name='relativeaddress']")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", alamatKerabat)
WebUI.delay(1)
alamatKerabat.clear()
alamatKerabat.sendKeys("Jl Kerabat Testing Jakarta")
WebUI.delay(1)

// -----------------------------------------------
// PILIH HUBUNGAN (dropdown Kerabat)
// inspect image 4: react-select-4-input di dalam select__control
// -----------------------------------------------
WebElement dropdownHubungan = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//input[@id='react-select-4-input']/ancestor::div[contains(@class,'select__control')]")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", dropdownHubungan)
WebUI.delay(1)
dropdownHubungan.click()
WebUI.delay(1)

WebElement pilihAyah = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(@class,'select__option') and normalize-space(text())='Ayah']")
    )
)
pilihAyah.click()
WebUI.delay(1)

// -----------------------------------------------
// PILIH "ANDA TAHU NEMOB DARIMANA?"
// inspect image 5: react-select-5-input di dalam select__control
// -----------------------------------------------
WebElement dropdownInfo = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//input[@id='react-select-5-input']/ancestor::div[contains(@class,'select__control')]")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", dropdownInfo)
WebUI.delay(1)
dropdownInfo.click()
WebUI.delay(1)

WebElement pilihGoogle = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//div[contains(@class,'select__option') and normalize-space(text())='Google']")
    )
)
pilihGoogle.click()
WebUI.delay(1)

// ======================================================
// JANGAN UPLOAD KTP & SIM (sengaja dibiarkan kosong)
// ======================================================
println("INFO: KTP dan SIM sengaja tidak diupload.")
WebUI.delay(2)

// Step 12: Klik tombol 'Pesan sekarang'
WebElement tombolPesanSekarang = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@id='submit']//input[@id='send_message']")
    )
)
js.executeScript("arguments[0].scrollIntoView({block:'center'});", tombolPesanSekarang)
WebUI.delay(1)
js.executeScript("arguments[0].click();", tombolPesanSekarang)
WebUI.delay(3)

// ======================================================
// SCROLL KE BAGIAN KTP & SIM UNTUK MELIHAT ERROR
// ======================================================
WebUI.delay(2)

try {
    // Cari tombol upload KTP untuk scroll ke posisi yang tepat
    WebElement sectionKTP = driver.findElement(
        By.xpath(
            "//h5[contains(text(),'Unggah Kartu Identitas')] | " +
            "//h5[contains(text(),'KTP')] | " +
            "//label[contains(text(),'Unggah Kartu')] | " +
            "//button[contains(text(),'Pilih file')]"
        )
    )
    js.executeScript("arguments[0].scrollIntoView({block:'center'});", sectionKTP)
    println("INFO: Scroll ke bagian upload KTP/SIM berhasil")
} catch (Exception e) {
    js.executeScript("window.scrollTo(0, 0);")
    println("INFO: Scroll ke atas halaman (fallback)")
}

WebUI.delay(2)

// ======================================================
// VALIDASI ERROR KTP & SIM
// ======================================================

String pageSource = driver.getPageSource().toLowerCase()

boolean validasiKTP =
        pageSource.contains("id card is required")

boolean validasiSIM =
        pageSource.contains("sim is required")

println("Validasi KTP = " + validasiKTP)
println("Validasi SIM = " + validasiSIM)

assert validasiKTP :
       "GAGAL! Pesan 'ID Card is required!' tidak muncul."

assert validasiSIM :
       "GAGAL! Pesan 'SIM is required!' tidak muncul."

println("SUKSES: Validasi upload KTP dan SIM muncul.")

WebUI.closeBrowser()