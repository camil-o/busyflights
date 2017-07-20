package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.OrderUtils;
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
        Collections.sort(response, getComparator(sortBy));
        return response;
    }

    private Comparator<BusyFlightsResponse> getComparator(String sortBy) {
        Comparator<BusyFlightsResponse> fareCompartor = Comparator.comparingDouble(b -> b.getFare());
        return sortBy.endsWith("desc") ? fareCompartor.reversed() : fareCompartor;
    }
}
