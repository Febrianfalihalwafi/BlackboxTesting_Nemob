// Import library WebUI bawaan Katalon untuk kontrol browser
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Import By dan WebDriver dari Selenium (dipakai jika perlu cari elemen HTML)
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

// Import DriverFactory untuk mengambil instance browser yang sedang berjalan
import com.kms.katalon.core.webui.driver.DriverFactory

// Buka browser baru (kosong)
WebUI.openBrowser('')

// Catat waktu SEBELUM membuka halaman (dalam milidetik sejak 1 Jan 1970)
long start = System.currentTimeMillis()

// Navigasi ke URL target — INI yang diukur waktunya
WebUI.navigateToUrl("https://nemob.id/id")

// Catat waktu SESUDAH halaman selesai dimuat
long end = System.currentTimeMillis()

// Hitung selisih waktu: (akhir - awal) dibagi 1000 → dikonversi ke DETIK
long load = (end - start) / 1000

// Tampilkan hasil waktu loading di Console Katalon
println(load)

// ASSERTION: pastikan waktu loading kurang dari 10 detik
// Jika >= 10 detik → test FAILED, jika < 10 detik → test PASSED
assert load < 10

// Tutup browser setelah pengujian selesai
WebUI.closeBrowser()