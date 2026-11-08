package shop.app;
import java.util.List;
import shop.StudentInfo;
import shop.model.Product;
import shop.model.PhysicalProduct;
import shop.model.DigitalProduct;
import shop.payment.PaymentMethod;
import shop.payment.EWalletPayment;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.shipping.ShippingService;
import shop.shipping.ExpressShipping;
import shop.service.CheckoutService;
import shop.notification.CustomerContact;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.EmailChannel;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;

public class TugasApp {
    public static void main(String[] args) {
        System.out.println("Tugas oleh Gina Ramadhani (2511533014)");
        // ==========================================
        // T1: GRAND TOTAL DAN POLICY PEMBAYARAN
        // ==========================================
        // Membuat produk
        Product coffee = new PhysicalProduct(
                "KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);
        Product rendang = new PhysicalProduct(
                "RDG-002", "Rendang Kemasan 250g", 120_000, 400);
        Product ebook = new DigitalProduct(
                "EBK-003", "E-book Resep Kue Nusantara", 50_000,
                "https://toko-umkm.example/unduh/ebk-003");

        // Membuat keranjang
        List<Product> cart = List.of(coffee, rendang, ebook);

        // Membuat objek checkout dan kurir
        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        // Membuat tiga metode pembayaran
        EWalletPayment wallet = new EWalletPayment("DompetKu", 300_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1_000_000);

        // Menampilkan grand total
        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");

        System.out.println("E-Wallet DompetKu : Rp"
                + checkout.grandTotal(cart, express, wallet)
                + " (admin Rp0)");

        System.out.println("Transfer Bank Nagari : Rp"
                + checkout.grandTotal(cart, express, transfer)
                + " (admin Rp4500)");

        System.out.println("Kartu Kredit Siti Rahma : Rp"
                + checkout.grandTotal(cart, express, card)
                + " (admin Rp5360)");

        // Menampilkan kebijakan pembayaran
        System.out.println();
        System.out.println("Kebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet     : " + wallet.policy());
        // ==========================================
        // T2: NOTIFIKASI STATUS PESANAN
        // ==========================================

        System.out.println("\n== T2: Notifikasi status pesanan ==");

        // Membuat layanan notifikasi
        NotificationService notificationService = new NotificationService();

        // Mendaftarkan kanal Email, SMS, dan WhatsApp
        notificationService.register(new EmailChannel());
        notificationService.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        notificationService.register(new WhatsAppChannel());

        // Anonymous class sebagai kanal log konsol
        NotificationChannel logChannel = new NotificationChannel() {

            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        };

        // Mendaftarkan kanal log
        notificationService.register(logChannel);

        // Membuat dua data pelanggan
        CustomerContact siti = new CustomerContact(
                "Siti Rahma",
                "siti@example.com",
                "081234567890"
        );

        CustomerContact budi = new CustomerContact(
                "Budi",
                "",
                "+6281298765432"
        );

        // Membuat pesan panjang lebih dari 200 karakter
        String message = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. "
                + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
                + "Terima kasih telah berbelanja produk UMKM Sumatera Barat "
                + "di toko kami! Semoga produk yang diterima sesuai harapan "
                + "dan memberikan pengalaman belanja yang menyenangkan.";

        // Mengirim notifikasi kepada Siti
        int sentToSiti = notificationService.broadcast(siti, message);

        System.out.println("Terkirim ke Siti Rahma lewat "
                + sentToSiti + " kanal");

        // Mengirim notifikasi kepada Budi
        int sentToBudi = notificationService.broadcast(budi, message);

        System.out.println("Terkirim ke Budi lewat "
                + sentToBudi + " kanal");
    }
}
