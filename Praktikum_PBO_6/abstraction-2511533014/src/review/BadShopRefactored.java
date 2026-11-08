
package review;

import java.util.ArrayList;
import java.util.List;

// 1. Interface dipisahkan berdasarkan tanggung jawab.
interface Payable {
    boolean pay(long amount);
}

interface Refundable {
    boolean refund(long amount);
}

interface Shippable {
    long ship(int weightGrams);
}

interface InvoicePrintable {
    String printInvoice();
}

// 2. GiftVoucher hanya mengimplementasikan interface
// yang sesuai dengan kemampuannya.
class GiftVoucher implements Payable, InvoicePrintable {

    private double balance = 100_000.0;

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "sisa Rp" + (long) balance;
    }
}

// 3 dan 4. Courier menggunakan abstract method.
// Jenis pengiriman menggunakan enum agar tidak bergantung
// pada penulisan String.
enum CourierType {
    REGULAR,
    EXPRESS,
    SAME_DAY
}

abstract class Courier {

    public abstract long cost(CourierType type, int kilograms);
}

class JneCourier extends Courier {

    @Override
    public long cost(CourierType type, int kilograms) {

        if (type == null) {
            throw new IllegalArgumentException(
                "Jenis pengiriman tidak boleh kosong"
            );
        }

        if (kilograms <= 0) {
            throw new IllegalArgumentException(
                "Berat pengiriman harus lebih dari 0"
            );
        }

        switch (type) {
            case REGULAR:
                return kilograms * 10_000L;

            case EXPRESS:
                return kilograms * 18_000L;

            case SAME_DAY:
                return kilograms * 30_000L;

            default:
                throw new IllegalArgumentException(
                    "Jenis pengiriman tidak dikenal"
                );
        }
    }
}

// Class Cart untuk menyimpan daftar barang.
class Cart {

    private final List<Item> items = new ArrayList<>();

    // 5. Item diubah menjadi static nested class.
    static class Item {

        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {

            if (name == null || name.isBlank()
                    || price < 0 || quantity <= 0) {
                throw new IllegalArgumentException(
                    "Data barang tidak valid"
                );
            }

            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {

        if (item == null) {
            throw new IllegalArgumentException(
                "Item tidak boleh null"
            );
        }

        items.add(item);
    }

    long total() {

        long total = 0;

        for (Item item : items) {
            total += item.subtotal();
        }

        return total;
    }
}

// 6. Address dibuat immutable.
final class Address {

    private final String street;
    private final String city;

    Address(String street, String city) {

        if (street == null || street.isBlank()
                || city == null || city.isBlank()) {
            throw new IllegalArgumentException(
                "Alamat tidak boleh kosong"
            );
        }

        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {

    private final String id;
    private final Address address;

    // 7. Constructor menerima Address immutable.
    SimpleOrder(String id, Address address) {

        if (id == null || id.isBlank() || address == null) {
            throw new IllegalArgumentException(
                "Data pesanan tidak valid"
            );
        }

        this.id = id;
        this.address = address;
    }

    // Getter aman karena Address bersifat immutable.
    Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

// 8. Discount menggunakan abstract method,
// bukan default method dengan instanceof.
interface Discount {

    long apply(long price);
}

class MemberDiscount implements Discount {

    @Override
    public long apply(long price) {

        return price - (price * 5 / 100);
    }
}

class FlashSaleDiscount implements Discount {

    @Override
    public long apply(long price) {

        return price - (price * 30 / 100);
    }
}

// Class utama program.
public class BadShopRefactored {

    public static void main(String[] args) {

        // Menghitung total keranjang.
        Cart cart = new Cart();

        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));

        System.out.println("Total cart: " + cart.total());

        // Menghitung ongkos kirim.
        Courier courier = new JneCourier();

        System.out.println(
            "Ongkir Ekspres 2 kg: "
            + courier.cost(CourierType.EXPRESS, 2)
        );

        // Membuat dua pesanan dengan alamat berbeda.
        Address home = new Address(
            "Jl. Sudirman 10",
            "Padang"
        );

        SimpleOrder first = new SimpleOrder("ORD-1", home);

        SimpleOrder second = new SimpleOrder(
            "ORD-2",
            new Address("Jl. Sudirman 10", "Bukittinggi")
        );

        System.out.println(first);
        System.out.println(second);

        // Menghitung harga setelah diskon.
        Discount flashSale = new FlashSaleDiscount();

        System.out.println(
            "Harga flash sale: "
            + flashSale.apply(100_000)
        );

        // Melakukan pembayaran menggunakan voucher.
        GiftVoucher voucher = new GiftVoucher();

        Payable payment = voucher;
        InvoicePrintable invoice = voucher;

        System.out.println(
            payment.pay(30_000)
            + " | "
            + invoice.printInvoice()
        );
    }
}
