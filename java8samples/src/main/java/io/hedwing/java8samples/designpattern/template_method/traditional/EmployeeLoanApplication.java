package io.hedwing.java8samples.designpattern.template_method.traditional;

// BEGIN EmployeeLoanApplication
public class EmployeeLoanApplication extends PersonalLoanApplication {

    @Override
    protected void checkIncomeHistory() {
        // They work for us!
    }

}
// END EmployeeLoanApplication
