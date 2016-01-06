package io.hedwing.java8samples.designpattern.template_method.traditional;


import io.hedwing.java8samples.designpattern.template_method.ApplicationDenied;

// BEGIN LoanApplication
public abstract class LoanApplication {

    public void checkLoanApplication() throws ApplicationDenied {
        checkIdentity();
        checkCreditHistory();
        checkIncomeHistory();
        reportFindings();
    }

    protected abstract void checkIdentity() throws ApplicationDenied;

    protected abstract void checkIncomeHistory() throws ApplicationDenied;

    protected abstract void checkCreditHistory() throws ApplicationDenied;

    private void reportFindings() {
// END LoanApplication

    }



}
