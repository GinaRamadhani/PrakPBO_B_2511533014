package pekan1;
import java.util.Scanner;
public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
	}
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println(" Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("nO. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir : Rp" + saldo);
		System.out.println("---------------------");
		
	}
			public static void main(String[] args) {
				Scanner input = new Scanner(System.in);
				Rekening akunAktif = null; // Objek belum diinisialisasi (null)
				boolean isRunning = true;
				
				System.out.println("=== SISTEM PERBANKAN MINI ===");
				
				while (isRunning) {
					System.out.println("\nMenu Utama:");
					System.out.println("1. Buka Rekening Baru");
					System.out.println("2. Setor Tunai");
					System.out.println("3. Tarik Tunai");
					System.out.println("4. Cek Informasi Rekening");
					System.out.println("0. Keluar");
					System.out.println("Pilih menu: ");
					
					int pilihan = input.nextInt();
					input.nextLine(); // Membersihkan buffer enter
					
					switch (pilihan) {
						case 1:
							System.out.print("Masukkan No Rekening: ");
							String no = input.nextLine();
							System.out.print("Masukkan Nama Pemilik: ");
							String nama = input.nextLine();
							System.out.print("Masukkan Saldo Awal: ");
							double saldo = input.nextDouble();
							
							// Instalasi Obkect/ Menjalankan Construktor
							akunAktif = new Rekening(no, nama, saldo);
							break;
							
						case 2:
							if (akunAktif == null) {
								System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
							} else {
								System.out.print("Masukkan nominal setor: ");
								double setor = input.nextDouble();
								akunAktif.setorTunai(setor); // Memanggil Behavior / method
							}
							break;
							
						case 3:
							System.out.println("Fitur ini akan kerjakan sebagai Tugas Mandiri.");
							break;
							
						case 4:
							if (akunAktif == null) {
								System.out.println("Error: Anda belum membuka rekening!");
							} else {
								akunAktif.cekInformasi();
							}
							break;
							
						case 0:
							isRunning = false;
							System.out.println("Sistem ditutup. Terima Kasih!");
							break;
							
						default:
							System.out.println("Pilihan tidak valid!");
						
					}
				}
				input.close();
			}

		}

	

