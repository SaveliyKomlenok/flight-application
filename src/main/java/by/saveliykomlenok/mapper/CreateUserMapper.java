package by.saveliykomlenok.mapper;

import by.saveliykomlenok.dto.CreateUserDto;
import by.saveliykomlenok.entity.Gender;
import by.saveliykomlenok.entity.Role;
import by.saveliykomlenok.entity.User;
import by.saveliykomlenok.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormatter.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .gender(Gender.valueOf(createUserDto.getGender()))
                .build();
    }

    public static CreateUserMapper getINSTANCE() {
        return INSTANCE;
    }
}
