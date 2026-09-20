package pekan2;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null; // Objek belum diinisialisasi (null)
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
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
					
					Rekening rekeningBaru = new Rekening(no, nama, saldo);
				    daftarRekening.add(rekeningBaru);
				    akunAktif = rekeningBaru;
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
					 if (akunAktif == null) {
					System.out.println("Error: Silakan pilih atau buka rekening terlebih dahulu!");
					 } else {
						 System.out.print("Masukkan nominal tarik tunai: ");
	                        double tarik = input.nextDouble();

	                        akunAktif.tarikTunai(tarik);
	                    }
	                    break;
						
				case 4:
					if (akunAktif == null) {
						System.out.println("Error: Anda belum membuka rekening!");
					} else {
						akunAktif.cekInformasi();
					}
					break;
					
				case 5:
					System.out.print("Masukkan No Rekening yang ingin digunakan: ");
                    String nomorCari = input.nextLine();
                    akunAktif = null;                   
                    for (Rekening rekening : daftarRekening) {
                        if (rekening.nomorRekening.equals(nomorCari)) {
                            akunAktif = rekening;
                            break;
                        }
                    }
                    if (akunAktif != null) {
                        System.out.println("Akun berhasil dipilih.");
                        System.out.println("Pemilik: "
                                + akunAktif.namaPemilik);
                    } else {
                        System.out.println("Rekening tidak ditemukan.");
                    }
                    break;
                    
				case 6:
					 akunAktif.cetakMutasi();
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
