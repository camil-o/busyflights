package com.travix.medusa.busyflights.service;

import java.time.ZoneId;

/**
 * @author Camilo Silva
 */
public interface IataService {

    ZoneId getZoneId(String iataCode);
}
