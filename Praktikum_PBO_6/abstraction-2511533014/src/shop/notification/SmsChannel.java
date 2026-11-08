package shop.notification;
public class SmsChannel extends AbstractChannel {
    private final int maxLength;
    public SmsChannel(int maxLength) {
        if (maxLength < 3) {
            throw new IllegalArgumentException("Max length must be at least 3");
        }
        this.maxLength = maxLength;
    }
    @Override
    public String channelName() {
        return "SMS";
    }
    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        if (phone.startsWith("08")) {
            return phone;
        }
        return "";
    }
    @Override
    protected String prepare(String message) {
        if (message.length() > maxLength) {
            return message.substring(0, maxLength - 3) + "...";
        }
        return message;
    }
    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[SMS] To: " + recipient);
        System.out.println("Message: " + message);
        return true;
    }
}