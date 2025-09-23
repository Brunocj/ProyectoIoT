package com.example.appventure.Usuario.Model;

import androidx.annotation.NonNull;
//ESTO ES UN MODEL, LUEGO HAY QUE MOVERLO
public class PaymentMethod {
    private final String id;
    private final String brand;        // p.ej. "ChachaCard"
    private final String pan;          // solo token/bin+last4 si es posible
    private final String cardholder;
    private final int expMonth;
    private final int expYear;
    private boolean isDefault;

    public PaymentMethod(@NonNull String id, String brand, String pan,
                         String cardholder, int expMonth, int expYear) {
        this.id = id; this.brand = brand; this.pan = pan;
        this.cardholder = cardholder; this.expMonth = expMonth; this.expYear = expYear;
    }

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getPan() { return pan; }
    public String getCardholder() { return cardholder; }
    public int getExpMonth() { return expMonth; }
    public int getExpYear() { return expYear; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }

    public String maskedNumber() {
        String last4 = pan.length() >= 4 ? pan.substring(pan.length()-4) : pan;
        return "•••• – •••• – •••• – " + last4;
    }

    public String expString() {
        return String.format("%02d/%02d", expMonth, expYear);
    }
}
