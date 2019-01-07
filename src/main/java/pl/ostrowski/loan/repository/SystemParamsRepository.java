package pl.ostrowski.loan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ostrowski.loan.domain.SystemParameter;

@Repository
public interface SystemParamsRepository extends CrudRepository<SystemParameter, String> {
}
