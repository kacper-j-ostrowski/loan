package pl.ostrowski.loan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ostrowski.loan.domain.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
}