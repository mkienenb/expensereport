package com.nelkinda.training;

import java.io.PrintStream;
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
        ExpensesCalculation expensesCalculation = calculateExpenses(expenses);
        parameterizedPrintReport(expenses, new Date(), System.out, expensesCalculation);
    }

    public void parameterizedPrintReport(List<Expense> expenses, Date reportDate, PrintStream reportPrintStream, ExpensesCalculation expensesCalculation) {

        reportPrintStream.println("Expenses " + reportDate);
        for (Expense expense : expenses) {
            String expenseName = expense.type.reportName();
            String mealOverExpensesMarker = expense.isOverMealExpenseAmount() ? "X" : " ";
            reportPrintStream.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
        }
        reportPrintStream.println("Meal expenses: " + expensesCalculation.mealExpenses);
        reportPrintStream.println("Total expenses: " + expensesCalculation.total);
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
