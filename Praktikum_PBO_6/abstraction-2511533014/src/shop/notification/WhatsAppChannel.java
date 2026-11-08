package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "WhatsApp";
    }
    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();

        if ((phone.startsWith("08") || phone.startsWith("+62"))
                && phone.length() >= 10) {
            return phone;
        }

        return "";
    }

    @Override
    protected String prepare(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[WHATSAPP] To: " + recipient);
        System.out.println("Message: " + message);
        return true;
    }
}