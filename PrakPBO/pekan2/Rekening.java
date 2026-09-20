package pekan2;

import java.util.Scanner;
import java.util.ArrayList;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;

	//Implementasi Asosiasi (1-to-many)
	ArrayList<Transaksi> riwayatTransaksi;

	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;

		//Wajib mengimisialisasi ArrayList di dalam construktor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
	}

	//... (pertahankan metdod getNomorRekening() dan cekInformasi() yang sudah ada
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;

			//Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);

			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println(" Gagal: Nominal setor harus lebih dari 0!");
		}
	}

	public void tarikTunai(double nominal) {
		if (nominal < 10000) {
			System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000.");
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi. "
					+ "Saldo Anda: Rp" + saldo);
		} else {
			saldo -= nominal;

			//Merekam riwayat transaksi penarikan
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);

			System.out.println("Tarik tunai Rp" + nominal
					+ " berhasil. Saldo saat ini: Rp" + saldo);
		}
	}

	
	
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini.");
		} else {
			System.out.println("--- RIWAYAT TRANSAKSI ---");
			for (Transaksi transaksi : riwayatTransaksi) {
				transaksi.cetakDetail();
			}
			System.out.println("-------------------------");
		}
	}

	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("nO. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir : Rp" + saldo);
		System.out.println("---------------------");
	}
}