//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.service.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class HttpRemoteService implements RemoteService {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public HttpRemoteService() {
	}

	public <R> R get(String url, Class<R> responseType) throws Exception {
		HttpUriRequest httpMethod = this.createGet(url, new LinkedMultiValueMap());
		return this.execute(httpMethod, responseType);
	}

	public <R> R get(String url, MultiValueMap<String, String> params, Class<R> responseType) throws Exception {
		HttpUriRequest httpMethod = this.createGet(url, params);
		return this.execute(httpMethod, responseType);
	}

	public <R> R post(String url, Class<R> responseType) throws Exception {
		HttpUriRequest httpMethod = this.createPost(url, new LinkedMultiValueMap());
		return this.execute(httpMethod, responseType);
	}

	public <R> R post(String url, MultiValueMap<String, String> params, Class<R> responseType) throws Exception {
		HttpUriRequest httpMethod = this.createPost(url, params);
		return this.execute(httpMethod, responseType);
	}

	private HttpGet createGet(String url, MultiValueMap<String, String> params) throws URISyntaxException {
		URIBuilder uri = new URIBuilder(url);
		Iterator var4 = params.entrySet().iterator();

		while(var4.hasNext()) {
			Map.Entry<String, List<String>> entry = (Map.Entry)var4.next();
			String key = (String)entry.getKey();
			Iterator var7 = ((List)entry.getValue()).iterator();

			while(var7.hasNext()) {
				String value = (String)var7.next();
				uri.addParameter(key, value);
			}
		}

		return new HttpGet(uri.build());
	}

	private HttpPost createPost(String url, MultiValueMap<String, String> params) throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList();
		Iterator var5 = params.entrySet().iterator();

		while(var5.hasNext()) {
			Map.Entry<String, List<String>> entry = (Map.Entry)var5.next();
			String key = (String)entry.getKey();
			Iterator var8 = ((List)entry.getValue()).iterator();

			while(var8.hasNext()) {
				String value = (String)var8.next();
				nvps.add(new BasicNameValuePair(key, value));
			}
		}

		post.setEntity(new UrlEncodedFormEntity(nvps));
		return post;
	}

	private <R> R execute(HttpUriRequest httpMethod, Class<R> responseType) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpMethod);
		HttpEntity entity = response.getEntity();
		return objectMapper.readValue(entity.getContent(), responseType);
	}
}
