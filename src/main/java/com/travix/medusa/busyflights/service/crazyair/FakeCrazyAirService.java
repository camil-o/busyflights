package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Camilo Silva
 */
public class FakeCrazyAirService implements SearchService<CrazyAirRequest, CrazyAirResponse> {

    private static final int MAX_RESULTS = 5;
    private static final int MIN_PRICE = 95;
    private static final int MAX_OVERPRICE = 20;

    @Override
    public List<CrazyAirResponse> search(CrazyAirRequest request) {
        List<CrazyAirResponse> response = new ArrayList<>();

        for (int i = 0; i < getRandom(MAX_RESULTS); i++) {
            response.add(createResponse(request));
        }

        return response;
    }

    private CrazyAirResponse createResponse(CrazyAirRequest request) {
        CrazyAirResponse response = new CrazyAirResponse();

        response.setAirline("Fake Airlines");
        response.setPrice(MIN_PRICE + getRandom(MAX_OVERPRICE));
        response.setCabinclass("E");
        response.setDepartureAirportCode(request.getOrigin());
        response.setDestinationAirportCode(request.getDestination());

        LocalDate departureDate = LocalDate.parse(request.getDepartureDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime departureDateTime = departureDate.atStartOfDay().plusHours(getRandom(12));

        response.setDepartureDate(departureDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setArrivalDate(departureDateTime.plusHours(4).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return response;
    }

    private int getRandom(int max) {
        return ThreadLocalRandom.current().nextInt(1, max + 1);
    }
}
