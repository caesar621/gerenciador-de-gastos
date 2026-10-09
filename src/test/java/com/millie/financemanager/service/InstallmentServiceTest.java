package com.millie.financemanager.service;

import com.millie.financemanager.dto.InstallmentRequestDto;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.exception.NotFoundException;
import com.millie.financemanager.repository.InstallmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.millie.financemanager.enums.Status.PAID;
import static com.millie.financemanager.enums.Status.UNPAID;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstallmentServiceTest {

    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private InstallmentService installmentService;

    @Test
    void shouldReturnPaidInstallmentWhenPaying() {
        Installment mockInstallment = new Installment();

        mockInstallment.setId(1L);
        mockInstallment.setStatus(UNPAID);

        when(installmentRepository.findById(1L)).thenReturn(Optional.of(mockInstallment));
        when(installmentRepository.save(mockInstallment)).thenReturn(mockInstallment);

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(PAID);

        Installment result = installmentService.updateInstallmentStatus(1L, requestDto);

        Assertions.assertEquals(PAID, result.getStatus());
        Assertions.assertEquals(LocalDate.now(), result.getPaymentDate());
    }

    @Test
    void shouldReturnPaidInstallmentWithOriginalPaymentDate() {
        Installment mockInstallment = new Installment();

        LocalDate paymentDate = LocalDate.of(2026, 10, 6);

        mockInstallment.setId(1L);
        mockInstallment.setStatus(PAID);
        mockInstallment.setPaymentDate(paymentDate);

        when(installmentRepository.findById(1L)).thenReturn(Optional.of(mockInstallment));

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(PAID);

        Installment result = installmentService.updateInstallmentStatus(1L, requestDto);

        Assertions.assertEquals(PAID, result.getStatus());
        Assertions.assertEquals(paymentDate, result.getPaymentDate());
    }


    @Test
    void shouldReturnUnpaidInstallmentWhenUnpaying() {

        Installment mockInstallment = new Installment();

        mockInstallment.setId(1L);
        mockInstallment.setStatus(PAID);
        mockInstallment.setPaymentDate(LocalDate.of(2026, 10, 7));

        when(installmentRepository.findById(1L)).thenReturn(Optional.of(mockInstallment));
        when(installmentRepository.save(mockInstallment)).thenReturn(mockInstallment);

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(UNPAID);

        Installment result = installmentService.updateInstallmentStatus(1L, requestDto);

        Assertions.assertEquals(UNPAID, result.getStatus());
        Assertions.assertNull(result.getPaymentDate());

    }

    @Test
    void shouldThrowExceptionWhenInstallmentNotFound() {

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(PAID);

        Assertions.assertThrows(NotFoundException.class, () ->  installmentService.updateInstallmentStatus(1L, requestDto));
    }

}
