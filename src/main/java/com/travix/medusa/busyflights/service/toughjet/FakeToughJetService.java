package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * @author Camilo Silva
 */
public class FakeToughJetService implements SearchService<ToughJetRequest, ToughJetResponse> {

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

        ZonedDateTime departureDate = ZonedDateTime.now();

        response.setOutboundDateTime(departureDate.format(DateTimeFormatter.ISO_INSTANT));
        response.setInboundDateTime(departureDate.plusHours(4).format(DateTimeFormatter.ISO_INSTANT));

        return response;
    }
}
