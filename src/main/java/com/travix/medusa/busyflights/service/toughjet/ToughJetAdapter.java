package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Camilo Silva
 */
public class ToughJetAdapter implements SearchService<BusyFlightsRequest, BusyFlightsResponse> {

    public static final String SUPPLIER_NAME = "ToughJet";
    /**
     * Defined centrally, to allow for easy changes to the rounding mode.
     */
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * Number of decimals to retain. Also referred to as "scale".
     */
    private static final int DECIMALS = 2;

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

        busyFlightsResponse.setDepartureDate(
                transformDateFormat(toughJetResponse.getOutboundDateTime()));
        busyFlightsResponse.setArrivalDate(
                transformDateFormat(toughJetResponse.getInboundDateTime()));

        busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
        busyFlightsResponse.setFare(calculateFare(toughJetResponse));

        busyFlightsResponse.setSupplier(SUPPLIER_NAME);

        return busyFlightsResponse;
    }

    private String transformDateFormat(String toughJetDate) {
        ZonedDateTime dateTime = ZonedDateTime.parse(toughJetDate);
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    private double calculateFare(ToughJetResponse toughJetResponse) {
        BigDecimal basePrice = BigDecimal.valueOf(toughJetResponse.getBasePrice());
        BigDecimal discountPercentage = BigDecimal.valueOf(toughJetResponse.getDiscount());
        BigDecimal tax = BigDecimal.valueOf(toughJetResponse.getTax());

        return basePrice.multiply(BigDecimal.ONE.subtract(discountPercentage))
                .add(tax)
                .setScale(DECIMALS, ROUNDING_MODE)
                .doubleValue();
    }
}