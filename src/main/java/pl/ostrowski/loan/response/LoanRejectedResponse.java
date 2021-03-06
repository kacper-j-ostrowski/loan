package pl.ostrowski.loan.response;

public class LoanRejectedResponse implements LoanResponse {

    private String reasonOfRejection;

    public LoanRejectedResponse(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanResponseCodes.REJECTED;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }
}
