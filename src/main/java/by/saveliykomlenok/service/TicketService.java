package by.saveliykomlenok.service;

import by.saveliykomlenok.dao.TicketDao;
import by.saveliykomlenok.dto.TicketDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketDto> findAllByFlightId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId).stream().map(ticket ->
                new TicketDto(ticket.getId(),
                        ticket.getFlightId(),
                        ticket.getSeatNo()
                )).collect(Collectors.toList());
    }

    public static TicketService getINSTANCE() {
        return INSTANCE;
    }
}
