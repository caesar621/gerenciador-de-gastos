package com.millie.financemanager.dto;

import com.millie.financemanager.enums.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseResponseDto {

    private long id;
    private String name;
    private BigDecimal totalValue;
    private PaymentType paymentType;
    private CategoryResponseDto category;
    private List<InstallmentResponseDto> installment;

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

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }

    public List<InstallmentResponseDto> getInstallment() {
        return installment;
    }

    public void setInstallment(List<InstallmentResponseDto> installment) {
        this.installment = installment;
    }
}
