package com.cloudflare.urlshortener.manager;

import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cloudflare.urlshortener.constants.Constants;
import com.cloudflare.urlshortener.models.Link;
import com.cloudflare.urlshortener.models.ListingsTable;
import com.cloudflare.urlshortener.models.ShortLink;
import com.cloudflare.urlshortener.services.URLTransform;
import com.cloudflare.urlshortener.utility.URLTransformUtil;

/**
 * Implement Url_Shortening service. Accesses at "your.web.address/shortly"
 * @author hemil
 *
 */
@Path("shortly")
public class UrlShortenerController {


	/**
	 * Pass a web URL you want to shorten.
	 * @param link
	 * @return
	 * @throws UnknownHostException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shortenUrl(Link link) throws UnknownHostException{

		URLTransform urlTransform = new URLTransform();
		int id = urlTransform.getShortenedUrl(link.getLink());

		String newLink = Constants.URL_PREFIX + id;

		ShortLink shortLink = new ShortLink();
		shortLink.setShortLink(newLink);

		return Response.status(Status.OK).entity(shortLink)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.build();
	}

	/**
	 * Check out how many times the short url has been accessed.
	 * @param id
	 * @return
	 */
	@GET
	@Path("/listing/{shortUrl}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lookupListing(@PathParam("shortUrl") int id){

		URLTransform urlTransform = new URLTransform();
		ListingsTable listingsTable = urlTransform.getListings(id);

		return Response.status(Status.OK).entity(listingsTable).build();
	}



}
