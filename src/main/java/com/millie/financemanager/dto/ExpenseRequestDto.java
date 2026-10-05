package com.millie.financemanager.dto;

import com.millie.financemanager.enums.PaymentType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDto {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2) //revisar e corrigir aqui o número máximo de digitos para a coluna de valor total de despesa
    private BigDecimal totalValue;
    
    private PaymentType paymentType;

    private Long categoryId;

    @Min(value = 1)
    private int numberOfInstallments;

    private LocalDate firstDueDate;


//    constructors
    public ExpenseRequestDto() {}

//    getters & setters
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getFirstDueDate() {
        return firstDueDate;
    }

    public void setFirstDueDate(LocalDate firstDueDate) {
        this.firstDueDate = firstDueDate;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }
}
