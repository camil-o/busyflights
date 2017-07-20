package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody List<BusyFlightsResponse> search(
            @RequestBody BusyFlightsRequest request) {
        return service.search(request);
    }
}
