package by.saveliykomlenok.validator;

public interface Validator <T>{
    ValidationResult isValid(T t);
}
