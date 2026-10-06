package com.millie.financemanager.service;

import com.millie.financemanager.dto.InstallmentRequestDto;
import com.millie.financemanager.entity.Installment;
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
    void shouldReturnUpdatedPaidInstallment() {
        Installment mockInstallment = new Installment();

        mockInstallment.setId(1L);
        mockInstallment.setStatus(UNPAID);

        when(installmentRepository.findById(1L)).thenReturn(Optional.of(mockInstallment));
        when(installmentRepository.save(mockInstallment)).thenReturn(mockInstallment);

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(PAID);

        Installment result = installmentService.updateInstallmentStatus(1L, requestDto);

        Assertions.assertEquals(PAID, result.getStatus());
        Assertions.assertEquals(LocalDate.now(), result.getPaymentDate());}

}
