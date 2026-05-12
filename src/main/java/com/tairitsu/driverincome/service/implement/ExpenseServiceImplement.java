package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import com.tairitsu.driverincome.entity.Expense;
import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import com.tairitsu.driverincome.mapper.ExpenseMapper;
import com.tairitsu.driverincome.repository.ExpenseRepository;
import com.tairitsu.driverincome.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("unused")
@Service
public class ExpenseServiceImplement implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    public  ExpenseServiceImplement(ExpenseRepository expenseRepository) { this.expenseRepository = expenseRepository;}
    @Override
    public ExpenseDTOResponse createExpense(ExpenseDTORequest req) {
        Expense expense = ExpenseMapper.mapToExpense(req);
        Expense saved = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDTOResponse(saved);
    }

    @Override
    public void deleteExpense(Long id) {
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Expense not found with id " + id);
        }
        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseDTOResponse getExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
        return  ExpenseMapper.mapToExpenseDTOResponse(expense);
    }

    @Override
    public List<ExpenseDTOResponse> getAllExpense() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(ExpenseMapper::mapToExpenseDTOResponse).toList();
    }

    @Override
    public ExpenseDTOResponse updateExpense(Long id, ExpenseDTORequest req) {
        Expense existedExpense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
        ExpenseMapper.updateExpenseFromRequest(req, existedExpense);
        Expense updatedExpense = expenseRepository.save(existedExpense);
        return ExpenseMapper.mapToExpenseDTOResponse(updatedExpense);
    }

    @Override
    public Page<ExpenseDTOResponse> getExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> expenses = expenseRepository.findAll(pageable);
        return expenses.map(ExpenseMapper::mapToExpenseDTOResponse);
    }
}
