package com.millie.financemanager.repository;

import com.millie.financemanager.entity.Category;
import com.millie.financemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findExpenseByCategory(Category category);
}
