package pl.ostrowski.loan.dto;

public class LoanRejectedResponseDto implements LoanResponseDto {

    private String reasonOfRejection;

    public LoanRejectedResponseDto(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanResponseDto.REJECTED;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }
}
