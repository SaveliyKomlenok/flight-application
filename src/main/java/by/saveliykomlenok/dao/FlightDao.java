package by.saveliykomlenok.dao;

import by.saveliykomlenok.entity.Flight;
import by.saveliykomlenok.entity.FlightStatus;
import by.saveliykomlenok.exception.DaoException;
import by.saveliykomlenok.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Flight, Long> {
    private final static FlightDao INSTANCE = new FlightDao();

    private final static String SAVE_SQL = """
            INSERT INTO flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM flight
            WHERE id_flight = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id_flight, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status
            FROM flight
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id_flight = ?
            """;

    private final static String UPDATE_SQL = """
            UPDATE flight
            SET flight_no = ?,
                departure_date = ?,
                departure_airport_code = ?,
                arrival_date = ?,
                arrival_airport_code = ?,
                aircraft_id = ?,
                status = ?
            WHERE id_flight = ?
            """;

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(buildFlight(result));
            }
            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(result.getLong("id_flight"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getLong("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getLong("arrival_airport_code"),
                result.getLong("aircraft_id"),
                FlightStatus.valueOf(result.getString("status")));
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            Flight flight = null;

            var result = statement.executeQuery();
            if(result.next()){
                flight = buildFlight(result);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            createUpdateFlight(flight, statement);

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                flight.setId(keys.getLong("id_flight"));
            }

            return flight;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public boolean update(Flight flight) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)){
            createUpdateFlight(flight, statement);
            statement.setLong(8, flight.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static void createUpdateFlight(Flight flight, PreparedStatement statement) throws SQLException {
        statement.setString(1, flight.getFlight_no());
        statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
        statement.setLong(3, flight.getDepartureAirportCode());
        statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
        statement.setLong(5, flight.getArrivalAirportCode());
        statement.setLong(6, flight.getAircraftId());
        statement.setString(7, flight.getStatus().toString());
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    private FlightDao() {

    }
}
