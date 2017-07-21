package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
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
public class FakeCrazyAirService extends AbstractFakeSearchService<CrazyAirRequest, CrazyAirResponse> {

    private static final int MIN_PRICE = 95;
    private static final int MAX_OVERPRICE = 20;

    public FakeCrazyAirService(IataService iataService) {
        super(iataService);
    }

    @Override
    protected List<CrazyAirResponse> createResponse(CrazyAirRequest request) {
        List<CrazyAirResponse> response = new ArrayList<>(2);
        response.add(createFlight(request.getOrigin(), request.getDestination(), request.getDepartureDate()));
        response.add(createFlight(request.getDestination(), request.getOrigin(), request.getReturnDate()));
        return response;
    }

    private CrazyAirResponse createFlight(String from, String to, String date) {
        CrazyAirResponse response = new CrazyAirResponse();

        response.setAirline("Fake Airlines");
        response.setPrice(MIN_PRICE + getRandomInt(1, MAX_OVERPRICE));
        response.setCabinclass("E");
        response.setDepartureAirportCode(from);
        response.setDestinationAirportCode(to);

        LocalDate departureDate = LocalDate.parse(date);
        ZonedDateTime departureDateTime = departureDate.atStartOfDay(getIataService().getZoneId(from))
                .plusHours(getRandomInt(1, 12));

        response.setDepartureDate(departureDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setArrivalDate(departureDateTime
                .plusHours(4)
                .withZoneSameInstant(getIataService().getZoneId(to))
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return response;
    }
}
