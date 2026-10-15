package com.millie.financemanager.controller;

import com.millie.financemanager.appConfig;
import com.millie.financemanager.dto.InstallmentRequestDto;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.enums.Status;
import com.millie.financemanager.service.InstallmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstallmentController.class)
@Import(appConfig.class)
public class InstallmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InstallmentService installmentService;

    //refatorar testes com o uso de parametrize para reduzir redudância

    @Test
    void shouldSuccessfullyReturnUpdatedInstallmentStatus() throws Exception {

        InstallmentRequestDto requestDto = new InstallmentRequestDto();
        requestDto.setStatus(Status.PAID);

        Installment installment = new Installment();
        installment.setId(1L);
        installment.setDueDate(LocalDate.now().plusMonths(1));
        installment.setInstallmentNumber(1);
        installment.setPaymentDate(LocalDate.now());
        installment.setStatus(Status.PAID);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        when(installmentService.updateInstallmentStatus(eq(1L), any(InstallmentRequestDto.class))).thenReturn(installment);

        mockMvc.perform(patch("/installment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.PAID.name()));

    }

    @Test
    void shouldThrowExceptionIfEmptyStatus() throws Exception {

        Installment installment = new Installment();
        installment.setId(1L);
        installment.setDueDate(LocalDate.now().plusMonths(1));
        installment.setInstallmentNumber(1);
        installment.setPaymentDate(LocalDate.now());
        installment.setStatus(Status.PAID);

        String jsonBody = """
                {
                
                }
                """;

        when(installmentService.updateInstallmentStatus(eq(1L), any(InstallmentRequestDto.class))).thenReturn(installment);

        mockMvc.perform(patch("/installment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());

    }
}
