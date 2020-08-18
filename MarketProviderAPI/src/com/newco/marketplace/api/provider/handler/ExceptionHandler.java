package com.newco.marketplace.api.provider.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.exceptions.ProviderAPIException;

public class ExceptionHandler implements ExceptionMapper {

	private Logger logger = Logger.getLogger(ExceptionHandler.class);

	public Response toResponse(Throwable exception) {
		Response.Status status;

		status = Response.Status.INTERNAL_SERVER_ERROR;

		logger.error("Exception caught in ExceptionHandler: "
				+ exception.getMessage());
		exception.printStackTrace();// Not sure we need to have this. Adding for more details

		ProviderAPIException mobileAPIException = new ProviderAPIException(
				"Internal error occurred.");

		return Response.status(status).header("Exception",
				mobileAPIException.getMessage()).build();
	}
}