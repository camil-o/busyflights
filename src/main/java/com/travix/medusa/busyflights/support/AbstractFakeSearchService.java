package com.travix.medusa.busyflights.support;

import com.travix.medusa.busyflights.service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Camilo Silva
 */
public abstract class AbstractFakeSearchService<F, T> implements SearchService<F, T> {

    protected static final int MAX_RESULTS = 5;
    private final IataService iataService;

    public AbstractFakeSearchService(IataService iataService) {
        this.iataService = iataService;
    }

    @Override
    public List<T> search(F request) {
        List<T> response = new ArrayList<>();

        for (int i = 0; i < getRandomInt(1, MAX_RESULTS); i++) {
            response.addAll(createResponse(request));
        }

        return response;
    }

    protected abstract List<T> createResponse(F request);

    protected int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public IataService getIataService() {
        return iataService;
    }
}
