package com.travix.medusa.busyflights.service.crazyair;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.SearchService;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Camilo Silva
 */
public class CrazyAirService implements SearchService<CrazyAirRequest, CrazyAirResponse> {

    private final RestTemplate restTemplate;
    private final String restUrl;

    public CrazyAirService(String restUrl, RestTemplate restTemplate) {
        this.restUrl = restUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CrazyAirResponse> search(CrazyAirRequest request) {
        return restTemplate.getForObject(restUrl, List.class, request);
    }
}
