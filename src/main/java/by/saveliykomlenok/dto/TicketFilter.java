package by.saveliykomlenok.dto;

public record TicketFilter (String passengerName,
                            String seatNo,
                            int limit,
                            int offSet){
}
