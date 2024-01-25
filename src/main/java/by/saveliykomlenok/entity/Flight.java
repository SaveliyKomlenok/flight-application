package by.saveliykomlenok.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private Long id;
    private String flight_no;
    private LocalDateTime departureDate;
    private Long departureAirportCode;
    private LocalDateTime arrivalDate;
    private Long arrivalAirportCode;
    private Long aircraftId;
    private FlightStatus status;
}
