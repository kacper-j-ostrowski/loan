package pl.ostrowski.loan.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.response.LoanAcceptedResponse;
import pl.ostrowski.loan.response.LoanExtendedResponse;
import pl.ostrowski.loan.response.LoanResponse;
import pl.ostrowski.loan.response.LoanRejectedResponse;
import pl.ostrowski.loan.service.LoanService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class LoanController {

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loan")
    public ResponseEntity<LoanResponse> applyForLoan(@RequestBody @Valid @NotNull LoanRequestDto loanRequestDto) {
        log.info("Requested for new Loan: {}", loanRequestDto);
        LoanDto loanDto;
        try {
            loanDto = loanService.applyForLoan(loanRequestDto);
        } catch (LoanValidationException e) {
            log.info("New Loan rejected with reason: {}", e.getMessage());
            return ResponseEntity.ok(new LoanRejectedResponse(e.getMessage()));
        }
        log.info("New Loan accepted: {}", loanRequestDto);
        return ResponseEntity.ok(new LoanAcceptedResponse(loanDto));
    }


    @PutMapping("/loan/{loanId}")
    public ResponseEntity extendLoan(@NotNull @PathVariable Long loanId) {
        log.info("Requested extension for Loan with id: {}", loanId);
        LoanDto loanDto;
        try {
            loanDto = loanService.extendLoanByDefaultPeriod(loanId);
            log.info("Requested extension for Loan with id: {} approved", loanId);
        } catch (LoanExtensionValidationException e) {
            log.info("Loan with id: {} rejected because: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (LoanNotFound e) {
            log.info("Loan with id: {} not found", loanId);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(new LoanExtendedResponse(loanDto.getNumberOfExtensions(), loanDto.getDueDate()));
    }
}
