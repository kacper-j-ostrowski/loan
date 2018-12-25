package pl.ostrowski.loan.exception;

public class LoanNotFound extends Exception {
    public LoanNotFound(Long id) {
        super("Loan with id: " + id + " not found.");
    }
}
