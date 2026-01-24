package fedelesh.flowersalon.domain.specification;

import java.util.function.Predicate;

@FunctionalInterface
public interface Specification<T> {

    /**
     * Перевіряє, чи задовольняє сутність цю специфікацію.
     */
    boolean isSatisfiedBy(T entity);

    /**
     * Комбінує специфікації через AND.
     */
    default Specification<T> and(Specification<T> other) {
        return entity -> this.isSatisfiedBy(entity) && other.isSatisfiedBy(entity);
    }

    /**
     * Комбінує специфікації через OR.
     */
    default Specification<T> or(Specification<T> other) {
        return entity -> this.isSatisfiedBy(entity) || other.isSatisfiedBy(entity);
    }

    /**
     * Інвертує специфікацію (NOT).
     */
    default Specification<T> not() {
        return entity -> !this.isSatisfiedBy(entity);
    }

    /**
     * Конвертує в Predicate для використання зі Stream API.
     */
    default Predicate<T> toPredicate() {
        return this::isSatisfiedBy;
    }
}
