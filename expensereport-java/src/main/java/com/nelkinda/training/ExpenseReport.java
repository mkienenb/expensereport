package com.nelkinda.training;

import java.util.Date;
import java.util.List;

enum ExpenseCategory {
    MEAL, OTHER
}

enum ExpenseType {
    DINNER("Dinner", ExpenseCategory.MEAL, 5000),
    BREAKFAST("Breakfast", ExpenseCategory.MEAL, 1000),
    CAR_RENTAL("Car Rental", ExpenseCategory.OTHER, Integer.MAX_VALUE);

    private final ExpenseCategory expenseCategory;
    private final int overExpenseAmount;
    private final String reportName;

    ExpenseType(String name, ExpenseCategory expenseCategory, int overExpenseAmount) {
        this.expenseCategory = expenseCategory;
        this.overExpenseAmount = overExpenseAmount;
        this.reportName = name;
    }

    public boolean isMealExpense() {
        return ExpenseCategory.MEAL == expenseCategory;
    }

    public int getOverExpenseAmount() {
        return overExpenseAmount;
    }

    public String reportName() {
        return reportName;
    }

}

class Expense {
    ExpenseType type;
    int amount;

    public boolean isOverMealExpenseAmount() {
        return type.isMealExpense() && amount > type.getOverExpenseAmount();
    }
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        Date reportDate = new Date();
        String reportContent = generateReportContent(expenses, reportDate);
        System.out.println(reportContent);
    }

    public String generateReportContent(List<Expense> expenses, Date reportDate) {
        ExpensesCalculation expensesCalculation = calculateExpenses(expenses);
        String reportContent = generateReport(expenses, reportDate, expensesCalculation);
        return reportContent;
    }

    private String generateReport(List<Expense> expenses, Date reportDate, ExpensesCalculation expensesCalculation) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Expenses ");
        stringBuilder.append(reportDate);
        stringBuilder.append("\n");

        for (Expense expense : expenses) {
            String expenseName = expense.type.reportName();
            String mealOverExpensesMarker = expense.isOverMealExpenseAmount() ? "X" : " ";
            stringBuilder.append(expenseName);
            stringBuilder.append("\t");
            stringBuilder.append(expense.amount);
            stringBuilder.append("\t");
            stringBuilder.append(mealOverExpensesMarker);
            stringBuilder.append("\n");
        }
        stringBuilder.append("Meal expenses: ");
        stringBuilder.append(expensesCalculation.mealExpenses);
        stringBuilder.append("\n");

        stringBuilder.append("Total expenses: ");
        stringBuilder.append(expensesCalculation.total);

        return stringBuilder.toString();
    }

    public ExpensesCalculation calculateExpenses(List<Expense> expenses) {
        ExpensesCalculation expensesCalculation = new ExpensesCalculation();
        for (Expense expense : expenses) {
            if (expense.type.isMealExpense()) {
                expensesCalculation.mealExpenses += expense.amount;
            }
            expensesCalculation.total += expense.amount;
        }
        return expensesCalculation;
    }

    static class ExpensesCalculation {
        int total = 0;
        int mealExpenses = 0;
    }
}
