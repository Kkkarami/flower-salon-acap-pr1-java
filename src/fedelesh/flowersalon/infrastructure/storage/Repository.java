package fedelesh.flowersalon.infrastructure.storage;

import fedelesh.flowersalon.domain.Entity;
import fedelesh.flowersalon.domain.specification.Specification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T extends Entity> {

    T save(T entity);

    Optional<T> findById(UUID id);

    Optional<T> findOne(Specification<T> spec);

    List<T> findAll(Specification<T> spec);

    boolean deleteById(UUID id);

    boolean delete(T entity);

    boolean existsById(UUID id);

    long count(Specification<T> spec);

    boolean exists(Specification<T> spec);
}
