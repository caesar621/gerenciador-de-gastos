package com.millie.financemanager.controller;

import com.millie.financemanager.appConfig;
import com.millie.financemanager.dto.ExpenseRequestDto;
import com.millie.financemanager.entity.Expense;
import com.millie.financemanager.enums.PaymentType;
import com.millie.financemanager.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ExpenseController.class)
@Import(appConfig.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    void shouldReturnExpenseById() throws Exception {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setName("Aluguel");
        expense.setTotalValue(new BigDecimal("3500.00"));
        expense.setPaymentType(PaymentType.CREDIT);

        when(expenseService.getExpenseById(1L)).thenReturn(expense);

        mockMvc.perform(get("/expense/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void shouldReturnAllExpenses() throws Exception {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setName("Aluguel");
        expense.setTotalValue(new BigDecimal("3500.00"));
        expense.setPaymentType(PaymentType.CREDIT);

        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);

        when(expenseService.getExpenses()).thenReturn(expenseList);

        mockMvc.perform(get("/expense"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].name").value("Aluguel"));

    }

    @Test
    void shouldReturnSuccessfullyCreatedExpense() throws Exception {

        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        requestDto.setTotalValue(new BigDecimal("3500.00"));
        requestDto.setNumberOfInstallments(12);
        requestDto.setName("Aluguel");
        requestDto.setPaymentType(PaymentType.CREDIT);

        Expense expense = new Expense();
        expense.setName("Aluguel");
        expense.setTotalValue(new BigDecimal("3500.00"));
        expense.setPaymentType(PaymentType.CREDIT);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        when(expenseService.createExpense(any(ExpenseRequestDto.class))).thenReturn(expense);

        mockMvc.perform(post("/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Aluguel"));
    }

    @Test
    void shouldThrowExceptionIfInvalidPaymentType() throws Exception {

        String jsonBody =  """
    {
        "name": "Aluguel",
        "totalValue": 3500.00,
        "numberOfInstallments": 12,
        "paymentType": "PIX"
    }
    """;

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors").value("Invalid value PIX for field paymentType. Accepted values: CREDIT, CASH"));

    }
}
