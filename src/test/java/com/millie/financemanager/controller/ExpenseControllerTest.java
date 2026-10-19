package com.millie.financemanager.controller;

import com.millie.financemanager.appConfig;
import com.millie.financemanager.dto.ExpenseRequestDto;
import com.millie.financemanager.entity.Expense;
import com.millie.financemanager.enums.PaymentType;
import com.millie.financemanager.service.ExpenseService;
import jakarta.persistence.EnumType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
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
import java.util.stream.Stream;

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
        requestDto.setTotalValue(new BigDecimal("3500000000.00"));
        requestDto.setNumberOfInstallments(360);
        requestDto.setName("Aluguel");
        requestDto.setPaymentType(PaymentType.CREDIT);

        Expense expense = new Expense();
        expense.setName("Aluguel");
        expense.setTotalValue(new BigDecimal("3500000000.00"));
        expense.setPaymentType(PaymentType.CREDIT);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        when(expenseService.createExpense(any(ExpenseRequestDto.class))).thenReturn(expense);

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Aluguel"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {""})
    void shouldThrowExceptionIfInvalidName(String input) throws Exception {

        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        requestDto.setTotalValue(new BigDecimal("1000.00"));
        requestDto.setNumberOfInstallments(1);
        requestDto.setName(input);
        requestDto.setPaymentType(PaymentType.CASH);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"-1000.00", "0.00", "10000000000.00", "100.000"})
    void shouldThrowExceptionIfInvalidTotalValue(String input) throws Exception {

        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        requestDto.setNumberOfInstallments(1);
        requestDto.setName("Aluguel");
        requestDto.setPaymentType(PaymentType.CASH);

        if (input == null || input.isBlank()) {
            requestDto.setTotalValue(null);
        } else {
            requestDto.setTotalValue(new BigDecimal(input));
        }

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 361})
    void shouldThrowExceptionIfInvalidNumberOfInstallments(int input) throws  Exception {

        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        requestDto.setTotalValue(new BigDecimal("1000.00"));
        requestDto.setNumberOfInstallments(input);
        requestDto.setName("Aluguel");
        requestDto.setPaymentType(PaymentType.CASH);

        String jsonBody = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"PIX"})
    void shouldThrowExceptionIfInvalidPaymentType(String input) throws Exception {

    String jsonBody = """
        "name": "Aluguel",
        "totalValue": "3500.00",
        "numberOfInstallments": 12,
        "paymentType": input
    """;

        mockMvc.perform(post("/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessfullyDeleteExpense() throws Exception {

        mockMvc.perform(delete("/expense/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(expenseService).deleteExpenseById(1L);
    }
}
