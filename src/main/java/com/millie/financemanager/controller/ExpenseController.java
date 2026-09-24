package com.millie.financemanager.controller;

import com.millie.financemanager.dto.ExpenseRequestDto;
import com.millie.financemanager.dto.ExpenseResponseDto;
import com.millie.financemanager.entity.Expense;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.millie.financemanager.service.ExpenseService;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    public ExpenseController(ModelMapper modelMapper, ExpenseService expenseService) {
        this.modelMapper = modelMapper;
        this.expenseService = expenseService;
    }

    @GetMapping("/{expenseId}")
    @ResponseBody
    public ResponseEntity<ExpenseResponseDto> listExpense(@PathVariable long expenseId) {
        ExpenseResponseDto expense = convertToDto(expenseService.getExpenseById(expenseId));
        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> listExpenses() {
        List<ExpenseResponseDto> expensesList = expenseService.getExpenses().stream().map(expense -> convertToDto(expense)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(expensesList);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expense) {
        ExpenseResponseDto newExpense = convertToDto(expenseService.createExpense(expense));
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable long expenseId) {
        expenseService.deleteExpenseById(expenseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ExpenseResponseDto convertToDto(Expense expense) {
        return modelMapper.map(expense, ExpenseResponseDto.class);
    }
}
