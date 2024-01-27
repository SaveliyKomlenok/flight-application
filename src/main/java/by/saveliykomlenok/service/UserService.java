package by.saveliykomlenok.service;

import by.saveliykomlenok.dao.UserDao;
import by.saveliykomlenok.dto.CreateUserDto;
import by.saveliykomlenok.exception.ValidationException;
import by.saveliykomlenok.mapper.CreateUserMapper;
import by.saveliykomlenok.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getINSTANCE();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getINSTANCE();

    public Long create(CreateUserDto createUserDto){
        var validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }

    public static UserService getINSTANCE() {
        return INSTANCE;
    }
}
