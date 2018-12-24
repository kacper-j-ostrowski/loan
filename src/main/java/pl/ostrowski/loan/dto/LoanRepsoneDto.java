package pl.ostrowski.loan.dto;

public interface LoanRepsoneDto {
    static String ACCEPTED = "Accepted";
    static String REJECTED = "Rejected";
    String getStatusOfLoan();
}
