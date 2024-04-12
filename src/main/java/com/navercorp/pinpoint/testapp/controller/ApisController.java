//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.navercorp.pinpoint.testapp.controller;

import com.navercorp.pinpoint.testapp.util.Description;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller("apisController")
public class ApisController {
	private final RequestMappingHandlerMapping handlerMapping;
	private final Map<String, SortedSet<RequestMappedUri>> apiMappings;

	@Autowired
	public ApisController(RequestMappingHandlerMapping handlerMapping) {
		this.apiMappings = new TreeMap(String.CASE_INSENSITIVE_ORDER);
		this.handlerMapping = handlerMapping;
	}

	@PostConstruct
	private void initApiMappings() {
		Map<RequestMappingInfo, HandlerMethod> requestMappedHandlers = this.handlerMapping.getHandlerMethods();
		Iterator var2 = requestMappedHandlers.entrySet().iterator();

		while(var2.hasNext()) {
			Map.Entry<RequestMappingInfo, HandlerMethod> requestMappedHandlerEntry = (Map.Entry)var2.next();
			RequestMappingInfo requestMappingInfo = (RequestMappingInfo)requestMappedHandlerEntry.getKey();
			HandlerMethod handlerMethod = (HandlerMethod)requestMappedHandlerEntry.getValue();
			Class<?> handlerMethodBeanClazz = handlerMethod.getBeanType();
			if (handlerMethodBeanClazz != this.getClass()) {
				String controllerName = handlerMethodBeanClazz.getSimpleName();
				Set<String> mappedRequests = requestMappingInfo.getPatternsCondition().getPatterns();
				SortedSet<RequestMappedUri> alreadyMappedRequests = (SortedSet)this.apiMappings.get(controllerName);
				if (alreadyMappedRequests == null) {
					alreadyMappedRequests = new TreeSet(ApisController.RequestMappedUri.MAPPED_URI_ORDER);
					this.apiMappings.put(controllerName, alreadyMappedRequests);
				}

				((SortedSet)alreadyMappedRequests).addAll(this.createRequestMappedApis(handlerMethod, mappedRequests));
			}
		}

	}

	private Set<RequestMappedUri> createRequestMappedApis(HandlerMethod handlerMethod, Set<String> mappedUris) {
		if (CollectionUtils.isEmpty(mappedUris)) {
			return Collections.emptySet();
		} else {
			Set<RequestMappedUri> requestMappedUris = new HashSet(mappedUris.size());
			Description description = (Description)handlerMethod.getMethodAnnotation(Description.class);
			Iterator var5 = mappedUris.iterator();

			while(var5.hasNext()) {
				String mappedUri = (String)var5.next();
				requestMappedUris.add(new RequestMappedUri(mappedUri, description));
			}

			return requestMappedUris;
		}
	}

	@RequestMapping(
		value = {"/index.html", "/apis"},
		method = {RequestMethod.GET}
	)
	public String apis(Model model) {
		model.addAttribute("apiMappings", this.apiMappings);
		return "apis";
	}

	public static class RequestMappedUri {
		private final String mappedUri;
		private final String description;
		private static final Comparator<RequestMappedUri> MAPPED_URI_ORDER = new Comparator<RequestMappedUri>() {
			public int compare(RequestMappedUri arg0, RequestMappedUri arg1) {
				if (arg0 == null && arg1 == null) {
					return 0;
				} else if (arg1 == null) {
					return -1;
				} else {
					return arg0 == null ? 1 : String.CASE_INSENSITIVE_ORDER.compare(arg0.mappedUri, arg1.mappedUri);
				}
			}
		};

		private RequestMappedUri(String mappedUri, Description description) {
			if (mappedUri == null) {
				throw new IllegalArgumentException("mappedUri must not be null");
			} else {
				this.mappedUri = mappedUri;
				this.description = description == null ? "" : description.value();
			}
		}

		public String getMappedUri() {
			return this.mappedUri;
		}

		public String getDescription() {
			return this.description;
		}

		public int hashCode() {
			int prime = 0;
			int result = 1;
			result = 31 * result + (this.description == null ? 0 : this.description.hashCode());
			result = 31 * result + (this.mappedUri == null ? 0 : this.mappedUri.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null) {
				return false;
			} else if (this.getClass() != obj.getClass()) {
				return false;
			} else {
				RequestMappedUri other = (RequestMappedUri)obj;
				if (this.description == null) {
					if (other.description != null) {
						return false;
					}
				} else if (!this.description.equals(other.description)) {
					return false;
				}

				if (this.mappedUri == null) {
					if (other.mappedUri != null) {
						return false;
					}
				} else if (!this.mappedUri.equals(other.mappedUri)) {
					return false;
				}

				return true;
			}
		}

		public String toString() {
			return "RequestMappedUri [mappedUri=" + this.mappedUri + ", description=" + this.description + "]";
		}
	}
}
