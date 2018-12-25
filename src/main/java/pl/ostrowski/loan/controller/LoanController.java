package pl.ostrowski.loan.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.response.LoanExtendedResponse;
import pl.ostrowski.loan.response.LoanResponse;
import pl.ostrowski.loan.response.LoanRejectedResponse;
import pl.ostrowski.loan.service.LoanService;
import pl.ostrowski.loan.validators.loanapplication.LoanValidator;
import pl.ostrowski.loan.response.mapper.LoanToLoanResponseMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Log4j2
@Transactional
@RestController
@RequestMapping("/api/v1")
public class LoanController {

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loan")
    public ResponseEntity<LoanResponse> applyForLoan(@RequestBody @Valid @NotNull LoanDto loan) {
//        Optional<String> validationResult = LoanValidator.validate(loan);
//        if(validationResult.isPresent()) {
//            log.info("Loan rejected because {}", validationResult.get());
//            return ResponseEntity.ok(new LoanRejectedResponse(validationResult.get()));
//        }
//        loanService.calculateDueAmountForLoan(loan);
//        Long loanId = loanService.saveLoan(loan);
//        log.info("Loan accepted with new Id {}", loanId);
        return ResponseEntity.ok(new LoanRejectedResponse(""));
    }


    @PutMapping("/loan/{loanId}")
    public ResponseEntity extendLoan(@NotNull @PathVariable Long loanId) {
        log.info("Requested extension for Loan with id: {}", loanId);
        return loanService.extendLoanByDefaultPeriod(loanId).map(l -> {
            log.info("Requested extension for Loan with id: {} approved", loanId);
            loanService.saveLoan(l);
            return ResponseEntity.ok(new LoanExtendedResponse(l.getExtensionCounter(), l.getDueDate()));
        }).orElseGet(() -> {
            log.info("Loan with id: {} not found", loanId);
            return ResponseEntity.notFound().build();
        });
    }

}
