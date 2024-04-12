//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.controller;

import com.navercorp.pinpoint.testapp.service.remote.RemoteService;
import com.navercorp.pinpoint.testapp.util.Description;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/httpclient4"})
public class HttpClient4Controller {
	private static final String GOOGLE_GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/json";
	private static final String EPL_LEAGUE_TABLE_URL = "http://api.football-data.org/v1/soccerseasons/398/leagueTable";
	private static final String DEFAULT_GET_GEOCODE_ADDRESS = "Gyeonggi-do, Seongnam-si, Bundang-gu, Jeongja-dong, 178-1";
	@Autowired
	@Qualifier("httpRemoteService")
	RemoteService remoteService;

	public HttpClient4Controller() {
	}

	@RequestMapping({"/getGeoCode"})
	@ResponseBody
	@Description("HTTP GET to http://maps.googleapis.com/maps/api/geocode/json")
	public Map<String, Object> getGeoCode(@RequestParam(defaultValue = "Gyeonggi-do, Seongnam-si, Bundang-gu, Jeongja-dong, 178-1",required = false) String address) throws Exception {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("address", address);
		params.add("sensor", "false");
		return (Map)this.remoteService.get("http://maps.googleapis.com/maps/api/geocode/json", params, Map.class);
	}

	@RequestMapping({"/getEPLLeagueTable"})
	@ResponseBody
	@Description("HTTP GET to http://api.football-data.org/v1/soccerseasons/398/leagueTable")
	public Map<String, Object> getEplLeagueTable() throws Exception {
		return (Map)this.remoteService.get("http://api.football-data.org/v1/soccerseasons/398/leagueTable", Map.class);
	}
}
