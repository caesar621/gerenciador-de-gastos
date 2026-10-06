package com.millie.financemanager.service;

import com.millie.financemanager.dto.InstallmentRequestDto;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.enums.Status;
import com.millie.financemanager.exception.NotFoundException;
import com.millie.financemanager.repository.InstallmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.millie.financemanager.enums.Status.*;

@Service
public class InstallmentService {

    private final InstallmentRepository installmentRepository;

    public InstallmentService(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    public Installment updateInstallmentStatus(Long installmentId, InstallmentRequestDto installment) {
        Installment updatedInstallment = installmentRepository.findById(installmentId).orElseThrow(() -> new NotFoundException(Installment.class, installmentId));

        Status currentStatus = updatedInstallment.getStatus();

        if (currentStatus == PAID) {
            return updatedInstallment;
        }

        Status newStatus = installment.getStatus();

        LocalDate paymentDate = switch (newStatus) {
            case PAID -> LocalDate.now();
            case UNPAID -> null;
        };

        updatedInstallment.setPaymentDate(paymentDate);
        updatedInstallment.setStatus(newStatus);

        return installmentRepository.save(updatedInstallment);
    }
}
