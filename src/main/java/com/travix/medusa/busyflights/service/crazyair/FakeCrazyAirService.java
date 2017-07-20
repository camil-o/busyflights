package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.SearchService;

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

    @Override
    public List<CrazyAirResponse> search(CrazyAirRequest request) {
        List<CrazyAirResponse> response = new ArrayList<>();

        for (int i = 0; i < getRandom(); i++) {
            response.add(createResponse(request));
        }

        return response;
    }

    private CrazyAirResponse createResponse(CrazyAirRequest request) {
        CrazyAirResponse response = new CrazyAirResponse();

        response.setAirline("Fake Airlines");
        response.setPrice(500);
        response.setCabinclass("E");
        response.setDepartureAirportCode(request.getOrigin());
        response.setDestinationAirportCode(request.getDestination());

        LocalDateTime departureDate = LocalDateTime.now();

        response.setDepartureDate(departureDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setArrivalDate(departureDate.plusHours(4).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return response;
    }

    private int getRandom() {
        return ThreadLocalRandom.current().nextInt(1, MAX_RESULTS + 1);
    }
}
