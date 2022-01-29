package com.nelkinda.training;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

enum ExpenseCategory {
    MEAL, OTHER
}
enum ExpenseType {
    DINNER (ExpenseCategory.MEAL, 5000),
    BREAKFAST (ExpenseCategory.MEAL, 1000),
    CAR_RENTAL (ExpenseCategory.OTHER, Integer.MAX_VALUE)
    ;

    private final ExpenseCategory expenseCategory;
    private final int overExpenseAmount;

    private ExpenseType(ExpenseCategory expenseCategory, int overExpenseAmount) {
        this.expenseCategory = expenseCategory;
        this.overExpenseAmount = overExpenseAmount;
    }

    public boolean isMealExpense() {
        return ExpenseCategory.MEAL == expenseCategory;
    }

    public int getOverExpenseAmount() {
        return overExpenseAmount;
    }
}

class Expense {
    ExpenseType type;
    int amount;
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        parameterizedPrintReport(expenses, new Date(), System.out);
    }

    public void parameterizedPrintReport(List<Expense> expenses, Date reportDate, PrintStream reportPrintStream) {
        int total = 0;
        int mealExpenses = 0;

        reportPrintStream.println("Expenses " + reportDate);

        for (Expense expense : expenses) {
            if (expense.type.isMealExpense()) {
                mealExpenses += expense.amount;
            }

            String expenseName = "";
            switch (expense.type) {
                case DINNER:
                    expenseName = "Dinner";
                    break;
                case BREAKFAST:
                    expenseName = "Breakfast";
                    break;
                case CAR_RENTAL:
                    expenseName = "Car Rental";
                    break;
            }

            String mealOverExpensesMarker = expense.type.isMealExpense() && expense.amount > expense.type.getOverExpenseAmount() ? "X" : " ";

            reportPrintStream.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        reportPrintStream.println("Meal expenses: " + mealExpenses);
        reportPrintStream.println("Total expenses: " + total);
    }
}
