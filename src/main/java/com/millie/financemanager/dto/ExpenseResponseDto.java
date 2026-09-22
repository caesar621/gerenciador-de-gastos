package com.millie.financemanager.dto;

import com.millie.financemanager.entity.Category;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.enums.PaymentType;

import java.util.List;

public class ExpenseResponseDto {

    private long id;
    private String name;
    private float totalValue;
    private PaymentType paymentType;
    private Category category;
    private List<Installment> installment;

//    constructors
    public ExpenseResponseDto() {}

//    getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Installment> getInstallment() {
        return installment;
    }

    public void setInstallment(List<Installment> installment) {
        this.installment = installment;
    }
}
