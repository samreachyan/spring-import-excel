package com.sakcode.consumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "BILLER_BILL_DETAILS")
public class BillerBillDetail {

    @Column(name = "BILLER_CODE")
    private String billerCode;

    @Id
    @Column(name = "BILL_NUMBER")
    private String billNumber;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "BILL_DUE_DATE")
    private String billDueDate;

    @Column(name = "BILL_CURRENCY")
    private String billCurrency;

    public BillerBillDetail(String billNumber, String customerName, String billCurrency) {
        this.billerCode = billCurrency == null || billCurrency.equalsIgnoreCase("USD") ? "4401" : "4402";
        this.billDueDate = null;
        this.billNumber = billNumber;
        this.customerName = customerName;
        this.billCurrency = billCurrency;
    }

    public BillerBillDetail(String billerCode, String billNumber, String customerName, String billDueDate, String billCurrency) {
        this.billerCode = billerCode;
        this.billNumber = billNumber;
        this.customerName = customerName;
        this.billDueDate = billDueDate;
        this.billCurrency = billCurrency;
    }

    public BillerBillDetail() {

    }
}
