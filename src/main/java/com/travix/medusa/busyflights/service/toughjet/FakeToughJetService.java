package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.IataService;
import com.travix.medusa.busyflights.service.SearchService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Camilo Silva
 */
public class FakeToughJetService implements SearchService<ToughJetRequest, ToughJetResponse> {

    private final IataService iataService;

    public FakeToughJetService(IataService iataService) {
        this.iataService = iataService;
    }

    @Override
    public List<ToughJetResponse> search(ToughJetRequest request) {
        return Collections.singletonList(createResponse(request));
    }

    private ToughJetResponse createResponse(ToughJetRequest request) {
        ToughJetResponse response = new ToughJetResponse();

        response.setCarrier("Lala Airlines");
        response.setBasePrice(100);
        response.setTax(7);
        response.setDiscount(0.05);
        response.setDepartureAirportName(request.getFrom());
        response.setArrivalAirportName(request.getTo());

        LocalDate departureDate = LocalDate.parse(request.getOutboundDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        ZonedDateTime departureDateTime = departureDate.atStartOfDay(iataService.getZoneId(request.getOutboundDate()))
                .plusHours(getRandom(12));

        response.setOutboundDateTime(departureDateTime.format(DateTimeFormatter.ISO_INSTANT));
        response.setInboundDateTime(departureDateTime.plusHours(4).format(DateTimeFormatter.ISO_INSTANT));

        return response;
    }

    private int getRandom(int max) {
        return ThreadLocalRandom.current().nextInt(1, max + 1);
    }
}
