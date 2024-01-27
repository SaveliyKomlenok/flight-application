package by.saveliykomlenok.service;

import by.saveliykomlenok.dao.FlightDao;
import by.saveliykomlenok.dto.FlightDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    public List<FlightDto> findAll(){
        return flightDao.findAll().stream().map(flight ->
                new FlightDto(flight.getId(), "%s - %s - %s".formatted(
                        flight.getArrivalAirportCode(),
                        flight.getDepartureAirportCode(),
                        flight.getStatus()
                ))).collect(Collectors.toList());
    }

    public static FlightService getINSTANCE() {
        return INSTANCE;
    }
}
