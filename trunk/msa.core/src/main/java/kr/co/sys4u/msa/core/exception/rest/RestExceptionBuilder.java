package kr.co.sys4u.msa.core.exception.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import kr.co.sys4u.msa.core.exception.AlertException;

public class RestExceptionBuilder {

	private List<RestExceptionContext> restExceptionInfos = new ArrayList<>();
	private HttpClientErrorException cause;
	private RestExceptionContext currentContext;

	private RestExceptionBuilder() {
		super();
	}

	public static RestExceptionBuilder create(HttpClientErrorException cause) {
		RestExceptionBuilder restExceptionBuilder = new RestExceptionBuilder();
		restExceptionBuilder.cause = cause;

		return restExceptionBuilder;
	}

	public RestExceptionBuilder when( HttpStatus httpStatus) {
		currentContext = new RestExceptionContext();
		currentContext.getHttpStatus().add(httpStatus);

		restExceptionInfos.add(currentContext);
		return this;
	}

	public RestExceptionBuilder andWhen(HttpStatus httpStatus) {
		currentContext.getHttpStatus().add(httpStatus);
		return this;
	}

	public RestExceptionBuilder thenThrow(Class<? extends AlertException> exceptionClass) {
		currentContext.setExceptionClass(exceptionClass);
		return this;
	}

	public RestExceptionBuilder message(String message) {
		currentContext.setMessage(message);
		return this;
	}

	public RestExceptionBuilder noMessage() {
		currentContext.setMessage(null);
		return this;
	}
	
	public RestExceptionBuilder redirectUrl(String redirectUrl) {
		currentContext.setRedirectUrl(redirectUrl);
		return this;
	}

	public RuntimeException build() {
		HttpStatus currentStatus = this.cause.getStatusCode();

		for (RestExceptionContext restExceptionInfo : restExceptionInfos) {
			if (restExceptionInfo.getHttpStatus().contains(currentStatus)) {
				try {
					return restExceptionInfo.getExceptionClass()
							.getConstructor(String.class, String.class, Throwable.class)
							.newInstance(restExceptionInfo.getMessage(), restExceptionInfo.getRedirectUrl(), cause);
				} catch (Exception e) {
					/* ignored */
				}

			}
		}

		return new RestException(cause.getMessage());
	}

	private static class RestExceptionContext {
		private List<HttpStatus> httpStatus = new ArrayList<>();
		private String message = "오류가 발생하였습니다.";
		private String redirectUrl = "/index/index.do";
		private Class<? extends AlertException> exceptionClass = AlertException.class;

		public List<HttpStatus> getHttpStatus() {
			return httpStatus;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}

		public Class<? extends AlertException> getExceptionClass() {
			return exceptionClass;
		}

		public void setExceptionClass(Class<? extends AlertException> exceptionClass) {
			this.exceptionClass = exceptionClass;
		}

	}

}