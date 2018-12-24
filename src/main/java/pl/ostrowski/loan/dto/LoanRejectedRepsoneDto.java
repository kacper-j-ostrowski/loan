package pl.ostrowski.loan.dto;

public class LoanRejectedRepsoneDto implements LoanRepsoneDto {

    private String reasonOfRejection;

    public LoanRejectedRepsoneDto(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanRepsoneDto.REJECTED;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }
}
