package com.travix.medusa.busyflights.service.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.SearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Camilo Silva
 */
public class AggregateBusyFlightService implements SearchService<BusyFlightsRequest, BusyFlightsResponse> {

    private List<SearchService<BusyFlightsRequest, BusyFlightsResponse>> aggregates = new ArrayList<>();

    @Override
    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        List<BusyFlightsResponse> response = new ArrayList<>();

        for(SearchService<BusyFlightsRequest, BusyFlightsResponse> service : aggregates) {
            response.addAll(service.search(request));
        }

        return response;
    }

    public void addAggregate(SearchService<BusyFlightsRequest, BusyFlightsResponse> service) {
        aggregates.add(service);
    }
}
