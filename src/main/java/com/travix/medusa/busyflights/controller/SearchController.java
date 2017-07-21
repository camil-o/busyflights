package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Camilo Silva
 */
@RestController
public class SearchController {

    @Autowired
    @Qualifier("aggregate")
    private SearchService<BusyFlightsRequest, BusyFlightsResponse> service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody
    List<BusyFlightsResponse> search(
            @RequestBody BusyFlightsRequest request,
            @RequestParam(value = "sortBy", defaultValue = "fare-asc") String sortBy) {

        List<BusyFlightsResponse> response = service.search(request);
        Collections.sort(response, getSortComparator(request.getOrigin(), sortBy));
        return response;
    }

    private Comparator<BusyFlightsResponse> getSortComparator(String origin, String sortBy) {
        Comparator<BusyFlightsResponse> originFirstComparator = Comparator
                .comparing(b -> !origin.equals(b.getDepartureAirportCode()));

        Comparator<BusyFlightsResponse> fareComparator = Comparator.comparingDouble(b -> b.getFare());
        if(sortBy.endsWith("desc")) {
            fareComparator = fareComparator.reversed();
        }

        return originFirstComparator.thenComparing(fareComparator);
    }
}
