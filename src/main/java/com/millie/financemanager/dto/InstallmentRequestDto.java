package com.millie.financemanager.dto;

import com.millie.financemanager.enums.Status;
public class InstallmentRequestDto {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
