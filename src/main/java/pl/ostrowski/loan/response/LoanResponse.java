package pl.ostrowski.loan.response;

public interface LoanResponse {
    static String ACCEPTED = "Accepted";
    static String REJECTED = "Rejected";
    static String EXTENDED = "Extended";
    String getStatusOfLoan();
}
