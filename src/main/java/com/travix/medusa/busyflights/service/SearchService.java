package com.travix.medusa.busyflights.service;

import java.util.List;

/**
 * @author Camilo Silva
 */
public interface SearchService<F, T> {

    List<T> search(F request);
}
