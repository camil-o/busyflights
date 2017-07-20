package com.travix.medusa.busyflights.service.toughjet;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.SearchService;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Camilo Silva
 */
public class ToughJetService implements SearchService<ToughJetRequest, ToughJetResponse> {

    private final RestTemplate restTemplate;
    private final String restUrl;

    public ToughJetService(RestTemplate restTemplate, String restUrl) {
        this.restTemplate = restTemplate;
        this.restUrl = restUrl;
    }

    @Override
    public List<ToughJetResponse> search(ToughJetRequest request) {
        return restTemplate.getForObject(restUrl, List.class, request);
    }
}
