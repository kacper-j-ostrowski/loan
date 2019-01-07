package pl.ostrowski.loan.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ostrowski.loan.domain.SystemParameter;

import java.util.Optional;

@Repository
public interface SystemParamsRepository extends CrudRepository<SystemParameter, String> {

    @Cacheable("settings")
    @Override
    Optional<SystemParameter> findById(String s);
}
