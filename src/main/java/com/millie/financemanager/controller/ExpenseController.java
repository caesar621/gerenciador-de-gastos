package com.millie.financemanager.controller;

import com.millie.financemanager.dto.ExpenseRequestDto;
import com.millie.financemanager.dto.ExpenseResponseDto;
import com.millie.financemanager.entity.Expense;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.millie.financemanager.service.ExpenseService;

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
    public ExpenseResponseDto listExpenses(@PathVariable long expenseId) {
        return convertToDto(expenseService.getExpenseById(expenseId));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody ExpenseRequestDto expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(expenseService.createExpense(expense)));
    }

    private ExpenseResponseDto convertToDto(Expense expense) {
        return modelMapper.map(expense, ExpenseResponseDto.class);
    }
}
