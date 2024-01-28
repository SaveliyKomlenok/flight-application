package by.saveliykomlenok.dao;

import by.saveliykomlenok.entity.Gender;
import by.saveliykomlenok.entity.Role;
import by.saveliykomlenok.entity.User;
import by.saveliykomlenok.exception.DaoException;
import by.saveliykomlenok.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<User, Long> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender) 
            VALUES (?, ?, ?, ?, ?, ?) 
            """;

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * FROM users WHERE email = ? AND password = ?;
            """;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);

            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = buildEntity(result);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id_user"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .build();
    }

    @Override
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setDate(2, Date.valueOf(user.getBirthday()));
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());
            statement.setString(6, user.getGender().name());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong("id_user"));
            }

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    public static UserDao getINSTANCE() {
        return INSTANCE;
    }
}
