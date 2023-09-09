package com.insurance.exception;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 404) {
			// Return a custom exception for 404 (Not Found) responses
			return new NotFoundException("Resource not found");
		}

		// Return the default Feign exception for other status codes
		return FeignException.errorStatus(methodKey, response);
	}

}
