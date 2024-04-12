//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.service.remote;

import org.springframework.util.MultiValueMap;

public interface RemoteService {
	<R> R get(String var1, Class<R> var2) throws Exception;

	<R> R get(String var1, MultiValueMap<String, String> var2, Class<R> var3) throws Exception;

	<R> R post(String var1, Class<R> var2) throws Exception;

	<R> R post(String var1, MultiValueMap<String, String> var2, Class<R> var3) throws Exception;
}
