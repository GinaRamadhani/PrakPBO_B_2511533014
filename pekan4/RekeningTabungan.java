package pekan4;

public class RekeningTabungan extends Rekening  {
	
	//Atribut spesifik yang hanya dimiliki oleh tabungan
	private double sukuBunga;
	
	// Construktor Subclass
	public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		// super() memanggil construktor kelas induk (Rekening). WAJIB berada di baris pertama!
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}
	
	public void tambahBungaAkhirBulan() {
		// menghitung bunga
		// mengapa bisa mengakses saldo secara langsung dari class RekeningTabungan ?
			double nominalBunga = saldo * (sukuBunga / 100);
			saldo += nominalBunga;
			
			// Mencatat riwayat transaksi
			String idTrx = "TRX-B" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
			
			System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
	}
}
