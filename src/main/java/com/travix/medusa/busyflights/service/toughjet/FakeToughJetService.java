package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.support.AbstractFakeSearchService;
import com.travix.medusa.busyflights.support.IataService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Camilo Silva
 */
public class FakeToughJetService extends AbstractFakeSearchService<ToughJetRequest, ToughJetResponse> {

    private static final int MIN_PRICE = 95;
    private static final int MAX_OVERPRICE = 20;

    public FakeToughJetService(IataService iataService) {
        super(iataService);
    }

    @Override
    protected List<ToughJetResponse> createResponse(ToughJetRequest request) {
        List<ToughJetResponse> response = new ArrayList<>(2);
        response.add(createFlight(request.getFrom(), request.getTo(), request.getOutboundDate()));
        response.add(createFlight(request.getTo(), request.getFrom(), request.getInboundDate()));
        return response;
    }

    private ToughJetResponse createFlight(String from, String to, String date) {
        ToughJetResponse response = new ToughJetResponse();

        response.setCarrier("Lala Airlines");
        response.setBasePrice(MIN_PRICE + getRandomInt(1, MAX_OVERPRICE));
        response.setTax(7);
        response.setDiscount(0.05);
        response.setDepartureAirportName(from);
        response.setArrivalAirportName(to);

        LocalDate departureDate = LocalDate.parse(date);
        ZonedDateTime departureDateTime = departureDate.atStartOfDay(getIataService().getZoneId(from))
                .plusHours(getRandomInt(1, 12));

        response.setOutboundDateTime(departureDateTime.format(DateTimeFormatter.ISO_INSTANT));
        response.setInboundDateTime(departureDateTime
                .plusHours(4)
                .withZoneSameInstant(getIataService().getZoneId(to))
                .format(DateTimeFormatter.ISO_INSTANT));

        return response;
    }
}
