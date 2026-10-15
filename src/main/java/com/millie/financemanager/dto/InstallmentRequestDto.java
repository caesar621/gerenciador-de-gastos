package com.millie.financemanager.dto;

import com.millie.financemanager.enums.Status;
import jakarta.validation.constraints.NotNull;

public class InstallmentRequestDto {

    @NotNull
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
