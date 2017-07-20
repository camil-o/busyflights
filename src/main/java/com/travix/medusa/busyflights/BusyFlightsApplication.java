package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.IataService;
import com.travix.medusa.busyflights.service.SearchService;
import com.travix.medusa.busyflights.service.busyflights.AggregateBusyFlightService;
import com.travix.medusa.busyflights.service.busyflights.FakeIataService;
import com.travix.medusa.busyflights.service.crazyair.CrazyAirAdapter;
import com.travix.medusa.busyflights.service.crazyair.FakeCrazyAirService;
import com.travix.medusa.busyflights.service.toughjet.FakeToughJetService;
import com.travix.medusa.busyflights.service.toughjet.ToughJetAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusyFlightsApplication.class, args);
	}

	@Bean(name = "aggregate")
	public SearchService<BusyFlightsRequest, BusyFlightsResponse> searchService() {
		AggregateBusyFlightService service = new AggregateBusyFlightService();
		service.addAggregate(crazyAirAdapter());
		service.addAggregate(toughJetAdapter());
		return service;
	}

	@Bean
	public SearchService<BusyFlightsRequest, BusyFlightsResponse> crazyAirAdapter() {
		return new CrazyAirAdapter(new FakeCrazyAirService(), iataService());
	}

	@Bean
	public SearchService<BusyFlightsRequest, BusyFlightsResponse> toughJetAdapter() {
		return new ToughJetAdapter(new FakeToughJetService(iataService()));
	}

	@Bean
	public IataService iataService() {
		return new FakeIataService();
	}
}
