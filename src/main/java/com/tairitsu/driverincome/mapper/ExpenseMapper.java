package com.tairitsu.driverincome.mapper;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import com.tairitsu.driverincome.entity.Expense;

public class ExpenseMapper {
    public static Expense mapToExpense(ExpenseDTORequest req){
        Expense expense = new Expense();
        expense.setAmount(req.getAmount());
        expense.setTypeOfExpense(req.getTypeOfExpense());
        expense.setExpenseDate(req.getExpenseDate());
        expense.setNote(req.getNote());
        return expense;
    }

    public static ExpenseDTOResponse mapToExpenseDTOResponse(Expense expense){
        return new ExpenseDTOResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getTypeOfExpense(),
                expense.getExpenseDate(),
                expense.getNote()
        );
    }

    public static void updateExpenseFromRequest(ExpenseDTORequest req, Expense expense){
        expense.setAmount(req.getAmount());
        expense.setTypeOfExpense(req.getTypeOfExpense());
        expense.setExpenseDate(req.getExpenseDate());
        String note = req.getNote();
        String normalizedNote = (note != null && note.isBlank()) ? null : note;
        expense.setNote(normalizedNote);
    }
}
