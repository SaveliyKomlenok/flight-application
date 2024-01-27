package by.saveliykomlenok.validator;

import by.saveliykomlenok.dto.CreateUserDto;
import by.saveliykomlenok.entity.Gender;
import by.saveliykomlenok.entity.Role;
import by.saveliykomlenok.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();

        if(userDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if(userDto.getEmail().isEmpty()){
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if(userDto.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        if(Role.find(userDto.getRole()).isEmpty()){
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if(Gender.find(userDto.getGender()).isEmpty()){
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }

        return validationResult;
    }

    public static CreateUserValidator getINSTANCE() {
        return INSTANCE;
    }
}
