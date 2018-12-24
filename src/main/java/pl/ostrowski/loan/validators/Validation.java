package pl.ostrowski.loan.validators;

// based on https://dzone.com/articles/server-side-validator-using-functional-interfaces
@FunctionalInterface
public interface Validation <K> {
    GenericValidationResult test(K param);

    default Validation <K> and(Validation <K> other) {
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return !result.isValid() ? result : other.test(param);
        };
    }

    default Validation <K> or(Validation <K> other) {
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return result.isValid() ? result : other.test(param);
        };
    }
}