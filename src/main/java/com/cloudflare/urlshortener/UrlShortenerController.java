package com.cloudflare.urlshortener;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import utility.URLTransformUtil;

@Path("shortly")
public class UrlShortenerController {

	@Path("{uri}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shortenUrl(@PathParam("uri") String uri){
		URLTransformUtil urlTransformUtil = new URLTransformUtil();
		int id = urlTransformUtil.shortenURL(uri);
		
		return Response.status(Status.OK).entity(""+id).build();
	}
	
}
