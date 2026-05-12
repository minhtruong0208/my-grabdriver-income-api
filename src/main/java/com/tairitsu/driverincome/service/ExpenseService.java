package com.tairitsu.driverincome.service;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExpenseService {
    ExpenseDTOResponse createExpense(ExpenseDTORequest req);
    void deleteExpense(Long id);
    ExpenseDTOResponse getExpense(Long id);
    List<ExpenseDTOResponse> getAllExpense();
    ExpenseDTOResponse updateExpense(Long id, ExpenseDTORequest req);
    Page<ExpenseDTOResponse> getExpenses(int page, int size);
}
