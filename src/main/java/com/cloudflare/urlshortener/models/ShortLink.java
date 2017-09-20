package com.cloudflare.urlshortener.models;


/**
 * Model class for the shortUrl response sent back to user.
 * @author hemil
 *
 */
public class ShortLink {

	String shortLink;

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
	
}
