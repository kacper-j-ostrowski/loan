package pl.ostrowski.loan.dto;

public interface LoanResponseDto {
    static String ACCEPTED = "Accepted";
    static String REJECTED = "Rejected";
    String getStatusOfLoan();
}
