package com.millie.financemanager.service;

import com.millie.financemanager.dto.ExpenseRequestDto;
import com.millie.financemanager.entity.Category;
import com.millie.financemanager.entity.Expense;
import com.millie.financemanager.entity.Installment;
import com.millie.financemanager.enums.Status;
import com.millie.financemanager.exception.NotFoundException;
import com.millie.financemanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.millie.financemanager.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(() -> new NotFoundException(Expense.class, expenseId));
    }

    public Expense createExpense(ExpenseRequestDto expense) {

        Expense newExpense = new Expense();
        Long categoryId = expense.getCategoryId();

        if (categoryId != null) {
            Category category = categoryRepository.findById(expense.getCategoryId()).orElseThrow(() -> new NotFoundException(Category.class, categoryId));
            newExpense.setCategory(category);
        }

        newExpense.setName(expense.getName());
        newExpense.setPaymentType(expense.getPaymentType());
        newExpense.setTotalValue(expense.getTotalValue());

        ArrayList<Installment> installmentsList = new ArrayList<Installment>();

        float installmentValue = expense.getTotalValue() / expense.getNumberOfInstallments();

        LocalDate firstDueDate = expense.getFirstDueDate() != null ? expense.getFirstDueDate() : LocalDate.now();

        for (int i = 0; i < expense.getNumberOfInstallments(); i++) {
            Installment installment = new Installment();
            installment.setExpense(newExpense);
            installment.setInstallmentValue(installmentValue);
            installment.setInstallmentNumber(i+1);
            installment.setDueDate(firstDueDate.plusMonths(i));
            installmentsList.add(installment);
            installment.setStatus(Status.UNPAID);
        }

        newExpense.setInstallment(installmentsList);
        return expenseRepository.save(newExpense);
    }

    public void deleteExpenseById(long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}
