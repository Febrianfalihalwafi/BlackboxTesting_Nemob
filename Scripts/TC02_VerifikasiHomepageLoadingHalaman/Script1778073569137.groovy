import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

// Membuka browser Chrome baru dalam keadaan kosong (belum ada halaman)
WebUI.openBrowser('')

// Mencatat waktu SEBELUM navigasi dimulai, dalam satuan milidetik
// System.currentTimeMillis() mengambil waktu saat ini dari sistem operasi
long start = System.currentTimeMillis()

// Perintah navigasi ke URL target — bagian INI yang diukur waktunya
WebUI.navigateToUrl("https://nemob.id/id")

// Mencatat waktu SESUDAH halaman selesai dimuat, dalam satuan milidetik
long end = System.currentTimeMillis()

// Menghitung selisih waktu antara sebelum dan sesudah navigasi
// Dibagi 1000.0 (bukan 1000) agar hasilnya berupa angka desimal yang akurat
// Contoh: jika selisih = 5214ms → load = 5.214 detik
// Jika dibagi 1000 (integer), hasilnya dibulatkan → 5 (tidak akurat)
double load = (end - start) / 1000.0

// Menampilkan hasil waktu loading di Console Katalon agar bisa dilihat saat test berjalan
println("Waktu loading: " + load + " detik")

// ASSERTION (Validasi utama test ini):
// Memastikan waktu loading KURANG DARI 10 detik
// Jika load >= 10 detik → test otomatis FAILED dan menampilkan pesan error
// Jika load < 10 detik  → test PASSED, halaman dianggap normal
assert load < 10, "Loading melebihi batas normal! Waktu aktual: " + load + " detik"

// Menampilkan pesan konfirmasi jika test berhasil
println("✓ Halaman dimuat dalam waktu normal: " + load + " detik")

// Menutup browser setelah pengujian selesai untuk membebaskan memori
WebUI.closeBrowser()