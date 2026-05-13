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

/**
 * Service implementation for managing expense records.
 * <p>This service provides:
 * <ul>
 *     <li>Expense CRUD operations</li>
 *     <li>DTO and entity mapping</li>
 *     <li>Pagination support</li>
 * </ul>
 */
@SuppressWarnings("unused")
@Service
public class ExpenseServiceImplement implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    public  ExpenseServiceImplement(ExpenseRepository expenseRepository) { this.expenseRepository = expenseRepository;}
    /**
     * Creates a new expense record.
     * @param req request object containing expense information
     * @return created expense response
     */
    @Override
    public ExpenseDTOResponse createExpense(ExpenseDTORequest req) {
        Expense expense = ExpenseMapper.mapToExpense(req);
        Expense saved = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDTOResponse(saved);
    }
    /**
     * Deletes an expense by its identifier.
     * @param id expense identifier
     * @throws ResourceNotFoundException if the expense does not exist
     */
    @Override
    public void deleteExpense(Long id) {
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Expense not found with id " + id);
        }
        expenseRepository.deleteById(id);
    }
    /**
     * Retrieves an expense by its identifier.
     * @param id expense identifier
     * @return expense response
     * @throws ResourceNotFoundException if the expense does not exist
     */
    @Override
    public ExpenseDTOResponse getExpense(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
        return  ExpenseMapper.mapToExpenseDTOResponse(expense);
    }
    /**
     * Retrieves all expense records.
     * @return list of expense responses
     */
    @Override
    public List<ExpenseDTOResponse> getAllExpense() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(ExpenseMapper::mapToExpenseDTOResponse).toList();
    }
    /**
     * Updates an existing expense.
     * @param id expense identifier
     * @param req updated expense information
     * @return updated expense response
     * @throws ResourceNotFoundException if the expense does not exist
     */
    @Override
    public ExpenseDTOResponse updateExpense(Long id, ExpenseDTORequest req) {
        Expense existedExpense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
        ExpenseMapper.updateExpenseFromRequest(req, existedExpense);
        Expense updatedExpense = expenseRepository.save(existedExpense);
        return ExpenseMapper.mapToExpenseDTOResponse(updatedExpense);
    }
    /**
     * Retrieves paginated expense records.
     * @param page zero-based page index
     * @param size number of records per page
     * @return paginated expense responses
     */
    @Override
    public Page<ExpenseDTOResponse> getExpenses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> expenses = expenseRepository.findAll(pageable);
        return expenses.map(ExpenseMapper::mapToExpenseDTOResponse);
    }
}
