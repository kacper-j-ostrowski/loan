package pl.ostrowski.loan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanResponseDto;
import pl.ostrowski.loan.dto.LoanRejectedResponseDto;
import pl.ostrowski.loan.dto.mapper.LoanToLoanDtoResponseMapper;
import pl.ostrowski.loan.service.LoanService;
import pl.ostrowski.loan.validators.loanapplication.LoanValidator;

import java.util.Optional;

@RestController
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loan")
    @Transactional
    public ResponseEntity<LoanResponseDto> applyForLoan(@RequestBody Loan loan) {
        Optional<String> validationResult = LoanValidator.validate(loan);
        if(validationResult.isPresent()) {
            logger.info("Loan rejected because {}", validationResult.get());
            return ResponseEntity.ok(new LoanRejectedResponseDto(validationResult.get()));
        }
        loanService.calculateDueAmountForLoan(loan);
        Long loanId = loanService.saveLoan(loan);
        logger.info("Loan accepted with new Id {}", loanId);
        return ResponseEntity.ok(LoanToLoanDtoResponseMapper.mapToAcceptedResponse(loan));
    }


    @PutMapping("/loan")
    public String extendLoan(Long loanId) {
        logger.info("Requested extension for Loan with id: {}", loanId);

        return "extended";
    }

}
