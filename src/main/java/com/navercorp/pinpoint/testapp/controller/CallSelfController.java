//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.controller;

import com.navercorp.pinpoint.testapp.service.remote.RemoteService;
import com.navercorp.pinpoint.testapp.util.Description;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/callSelf"})
public class CallSelfController {
	private static final String GET_CURRENT_TIMESTAMP_PATH = "/getCurrentTimestamp";
	private static final String GET_GEO_CODE_PATH = "/httpclient4/getGeoCode";
	private static final String GET_TWITTER_URL_COUNT_PATH = "/httpclient4/getTwitterUrlCount";
	private static final String DEFAULT_LOCAL_IP = "127.0.0.1";
	private static final String LOCAL_IP = getLocalHostIp();
	@Autowired
	@Qualifier("httpRemoteService")
	RemoteService remoteService;

	public CallSelfController() {
	}

	private static String getLocalHostIp() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			return localHost.getHostAddress();
		} catch (UnknownHostException var1) {
			return "127.0.0.1";
		}
	}

	@RequestMapping({"/getCurrentTimestamp"})
	@ResponseBody
	@Description("Calls self for /getCurrentTimestamp over HTTP.")
	public Map<String, Object> getCurrentTimeStamp(HttpServletRequest request) throws Exception {
		String url = createTargetUrl(request, "/getCurrentTimestamp");
		Map<String, Object> response = (Map)this.remoteService.get(url, Map.class);
		return response;
	}

	@RequestMapping({"/httpclient4/getGeoCode"})
	@ResponseBody
	@Description("Calls self for /httpclient4/getGeoCode over HTTP.")
	public Map<String, Object> httpClient4GetGeoCode(HttpServletRequest request) throws Exception {
		String url = createTargetUrl(request, "/httpclient4/getGeoCode");
		Map<String, Object> response = (Map)this.remoteService.get(url, Map.class);
		return response;
	}

	@RequestMapping({"/httpclient4/getTwitterUrlCount"})
	@ResponseBody
	@Description("Calls self for /httpclient4/getTwitterUrlCount over HTTP.")
	public Map<String, Object> httpClient4GetTwitterUrlCount(HttpServletRequest request) throws Exception {
		String url = createTargetUrl(request, "/httpclient4/getTwitterUrlCount");
		// Map<String, Object> response = (Map)this.remoteService.get(url, Map.class);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("getCurrentTimestamp", System.currentTimeMillis());
		return response;
	}

	private static final String createTargetUrl(HttpServletRequest request, String path) throws URISyntaxException {
		return (new URIBuilder()).setScheme("http").setHost(LOCAL_IP).setPort(request.getLocalPort()).setPath(path + ".pinpoint").build().toString();
	}
}
