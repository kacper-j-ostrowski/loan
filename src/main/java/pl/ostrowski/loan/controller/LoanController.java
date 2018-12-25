package pl.ostrowski.loan.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanResponseDto;
import pl.ostrowski.loan.dto.LoanRejectedResponseDto;
import pl.ostrowski.loan.dto.mapper.LoanToLoanDtoResponseMapper;
import pl.ostrowski.loan.service.LoanService;
import pl.ostrowski.loan.validators.loanapplication.LoanValidator;

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
    public ResponseEntity<LoanResponseDto> applyForLoan(@RequestBody Loan loan) {
        Optional<String> validationResult = LoanValidator.validate(loan);
        if(validationResult.isPresent()) {
            log.info("Loan rejected because {}", validationResult.get());
            return ResponseEntity.ok(new LoanRejectedResponseDto(validationResult.get()));
        }
        loanService.calculateDueAmountForLoan(loan);
        Long loanId = loanService.saveLoan(loan);
        log.info("Loan accepted with new Id {}", loanId);
        return ResponseEntity.ok(LoanToLoanDtoResponseMapper.mapToAcceptedResponse(loan));
    }


    @PutMapping("/loan/{loanId}")
    public ResponseEntity extendLoan(@PathVariable Long loanId) {
        log.info("Requested extension for Loan with id: {}", loanId);
        return loanService.extendLoanByDefaultPeriod(loanId).map(l -> {
            log.info("Requested extension for Loan with id: {} approved", loanId);
            loanService.saveLoan(l);
            return ResponseEntity.ok("OK");
        }).orElseGet(() -> {
            log.info("Loan with id: {} not found", loanId);
            return ResponseEntity.notFound().build();
        });
    }

}
