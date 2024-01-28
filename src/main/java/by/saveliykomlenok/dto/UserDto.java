package by.saveliykomlenok.dto;

import by.saveliykomlenok.entity.Gender;
import by.saveliykomlenok.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Long id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;
}
