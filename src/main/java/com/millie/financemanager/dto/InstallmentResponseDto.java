package com.millie.financemanager.dto;

import com.millie.financemanager.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InstallmentResponseDto {

    private long id;
    private int installmentNumber;
    private BigDecimal installmentValue;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private Status status;

    public InstallmentResponseDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public BigDecimal getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(BigDecimal installmentValue) {
        this.installmentValue = installmentValue;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public  LocalDate getPaymentDate() { return paymentDate; }

    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
