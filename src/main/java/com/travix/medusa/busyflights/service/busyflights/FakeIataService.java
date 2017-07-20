package com.travix.medusa.busyflights.service.busyflights;

import com.travix.medusa.busyflights.service.IataService;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Camilo Silva
 */
public class FakeIataService implements IataService {

    private static final Map<String, String> DB = new HashMap<>();

    static {
        DB.put("PTY", "America/Panama");
        DB.put("BOG", "America/Bogota");
        DB.put("LAX", "America/Los_Angeles");
        DB.put("CDG", "Europe/Paris");
    }

    @Override
    public ZoneId getZoneId(String iataCode) {
        return ZoneId.of(DB.getOrDefault(iataCode, DB.get("PTY")));
    }
}
