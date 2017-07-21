package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Camilo Silva
 */
public class CrazyAirAdapter implements SearchService<BusyFlightsRequest, BusyFlightsResponse> {

    public static final String SUPPLIER_NAME = "CrazyAir";
    private final SearchService<CrazyAirRequest, CrazyAirResponse> service;

    public CrazyAirAdapter(SearchService<CrazyAirRequest, CrazyAirResponse> service) {
        this.service = service;
    }

    @Override
    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        CrazyAirRequest crazyAirRequest = convert(request);

        List<CrazyAirResponse> crazyAirResponse = service.search(crazyAirRequest);

        return crazyAirResponse.stream().map(this::convert).collect(Collectors.toList());
    }

    private CrazyAirRequest convert(BusyFlightsRequest busyFlightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();

        crazyAirRequest.setOrigin(busyFlightsRequest.getOrigin());
        crazyAirRequest.setDestination(busyFlightsRequest.getDestination());
        crazyAirRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(busyFlightsRequest.getReturnDate());
        crazyAirRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());

        return crazyAirRequest;
    }

    private BusyFlightsResponse convert(CrazyAirResponse crazyAirResponse) {
        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();

        busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
        busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());

        busyFlightsResponse.setDepartureDate(
                transformDateFormat(crazyAirResponse.getDepartureDate()));
        busyFlightsResponse.setArrivalDate(
                transformDateFormat(crazyAirResponse.getArrivalDate()));

        busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
        busyFlightsResponse.setFare(crazyAirResponse.getPrice());
        busyFlightsResponse.setSupplier(SUPPLIER_NAME);

        return busyFlightsResponse;
    }

    private String transformDateFormat(String crazyAirDate) {
        LocalDateTime dateTime = LocalDateTime.parse(crazyAirDate);
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
