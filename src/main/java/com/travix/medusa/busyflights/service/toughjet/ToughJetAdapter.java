package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Camilo Silva
 */
public class ToughJetAdapter implements SearchService<BusyFlightsRequest, BusyFlightsResponse> {

    public static final String SUPPLIER_NAME = "ToughJet";
    private final SearchService<ToughJetRequest, ToughJetResponse> service;

    public ToughJetAdapter(SearchService<ToughJetRequest, ToughJetResponse> service) {
        this.service = service;
    }

    @Override
    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        ToughJetRequest toughJetRequest = convert(request);

        List<ToughJetResponse> toughJetResponse = service.search(toughJetRequest);

        return toughJetResponse.stream().map(this::convert).collect(Collectors.toList());
    }

    private ToughJetRequest convert(BusyFlightsRequest request) {
        ToughJetRequest toughJetRequest = new ToughJetRequest();

        toughJetRequest.setFrom(request.getOrigin());
        toughJetRequest.setTo(request.getDestination());
        toughJetRequest.setOutboundDate(request.getDepartureDate());
        toughJetRequest.setInboundDate(request.getReturnDate());
        toughJetRequest.setNumberOfAdults(request.getNumberOfPassengers());

        return toughJetRequest;
    }

    private BusyFlightsResponse convert(ToughJetResponse toughJetResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();

        busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
        busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
        busyFlightsResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
        busyFlightsResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
        busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
        busyFlightsResponse.setFare(calculateFare(toughJetResponse));
        busyFlightsResponse.setSupplier(SUPPLIER_NAME);

        return busyFlightsResponse;
    }

    private double calculateFare(ToughJetResponse response) {
        return response.getBasePrice() + response.getTax() - response.getDiscount();
    }
}