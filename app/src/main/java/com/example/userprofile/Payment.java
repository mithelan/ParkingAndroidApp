package com.example.userprofile;


import java.text.SimpleDateFormat;

public class Payment {

    private Long cardNo;
    private String expiry;
    private String cvv;


    public Payment() {

    }

    public static boolean isValid(final String cardNo){
        return cardNo != null
                && cardNo.length() == 16 ;
    }

    public static boolean isValid2(final String cvv){
        return cvv != null
                && cvv.length() == 3 ;
    }


    public static boolean isValid1(final String expiry){
        return expiry != null && expiry.matches("(0[1-9]|1[0-2])/[0-9]{2}");
    }

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}