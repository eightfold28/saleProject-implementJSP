# Tugas 2 IF3110 Pengembangan Aplikasi Berbasis Web

Melakukan *upgrade* Website Marketplace sederhana pada Tugas 1 dengan mengaplikasikan **arsitektur web service REST dan SOAP**.

**Luangkan waktu untuk membaca spek ini sampai selesai. Kerjakan hal yang perlu saja.**

### Tujuan Pembuatan Tugas

Diharapkan dengan tugas ini anda dapat mengerti:
* Produce dan Consume REST API
* Mengimplementasikan service Single Sign-On (SSO) sederhana
* Produce dan Consume Web Services dengan protokol SOAP
* Membuat web application dengan menggunakan JSP yang akan memanggil web services dengan SOAP dan REST.

## Anggota Tim
Nama Kelompok   : Wakasta
Anggota         : 13513016 Raka Nurul Fikri
                  13514028 Dharma Kurnia Septialoka
                  13514056 Hishshah Ghassani
                  13514090 Candra Ramsi

### Arsitektur Umum Server
![Gambar Arsitektur Umum Server](http://gitlab.informatika.org/IF3110_WebBasedDevelopment_2016/TugasBesar2_JavaAndWebService/raw/3747ba2499396d04f742a589a024876964383159/arsitektur_umum.png)

### Penjelasan
1. Basis Data Sistem
Basis data yang digunakan tidak jauh berbeda dengan basis data dari tugas sebelumnya. Terdapat 4 tabel, yaitu user, item, likes, dan purchase. 
Tabel user berisi semua data-data user yang dimasukkan saat registrasi, yaitu Full Name, Username, E-mail, Password, Full Address, Postal Code, Phone Number, dan ditambah ID dari user tersebut berupa integer. 
Tabel item berisi detail produk yang dijual yang dimasukkan saat penambahan produk, yaitu nama item, deskripsi item, harga item, gambar item. Selain itu, terdapat juga kolom yang menunjukkan berapa kali item tersebut dibeli (item_purchases), kolom yang menunjukkan pemilik item tersebut (item_owner, didapat dari ID user yang memasukkan item tersebut), kolom yang menunjukkan waktu item tersebut ditambahkan (time_added), dan kolom yang menunjukkan apakah item tersebut sudah dihapus atau belum (isDeleted).
Tabel likes berisi ID item yang di-like dan ID user yang me-like.
Tabel purchase berisi data user yang dimasukkan saat membeli barang, yaitu ID user, nama, alamat, postal code, nomor telepon, jumlah, dan tanggal order. Pada tabel ini tentu saja juga disimpan ID item yang dibeli. Selain itu, nama dan harga item juga disimpan agar data yang ditampilkan pada halaman sales dan purchase tetap walaupun data item diedit oleh ownernya. 

2. Shared Session
Shared Session dengan menggunakan token yang di-generate saat log in dan register (karena setelah register akan langsung log in). Satu user memiliki satu token dan apapun yang akan dilakukan oleh user tersebut akan dilakukan berdasarkan token (selama token tersebut tidak expired).

3. Pembangkitan token dan expire time
Token dibangkitkan saat login dan register.
Sedangkan expire time, 

4. Kelebihan dan kelemahan dari arsitektur aplikasi
Kelebihan:
- Keamanan lebih dijamin karena perubahan state user dilakukan dengan pemanggilan REST terlebih dahulu, lalu REST akan men-generate token unik untuk user (cookies)
- Modularitasnya lebih baik sehingga perbaikan dan pengujian lebih mudah. Pada aplikasi monolitik, semua kode berada dalam satu server sehingga program tidak modular.
Kekurangan:
- Implementasi lebih sulit 
- REST cenderung lebih lambat dari aplikasi monolitik

### Pembagian Tugas
REST :
1. Generate token : 13514056
2. Validasi token : 13514056
3. Log in : 13514056
4. Register : 13514056
5. Log out : 13514056

SOAP :
1. Add Product : 13514028
2. Delete product : 13514028
3. Update product : 13514028
4. Your product : 13514028
5. Catalog : 13514028
6. Like : 13514028
7. Purchase : 13514028
8. Sales history : 13514028
9. Purchase history : 13514028

Web app (JSP) :
1. Halaman login : 13514090
2. Halaman register : 13514090
3. Halaman catalog : 13514090
4. Halaman add/edit product : 13514090
5. Halaman your product : 13514090
6. Halaman sales history : 13514090
7. Halaman purchase history : 13514090

## About

Asisten IF3110 2016

Adin | Chairuni | David | Natan | Nilta | Tifani | Wawan | William

Dosen : Yudistira Dwi Wardhana | Riza Satria Perdana
