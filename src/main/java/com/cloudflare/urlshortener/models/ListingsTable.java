package com.cloudflare.urlshortener.models;

/**
 * Model for the Listing table of shortUrl.
 * @author hemil
 *
 */
public class ListingsTable {

	String shortUrl;
	String longUrl;
	int last_day_access;
	int last_week_access;
	int all_time_access;
	
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public int getLast_day_access() {
		return last_day_access;
	}
	public void setLast_day_access(int last_day_access) {
		this.last_day_access = last_day_access;
	}
	public int getLast_week_access() {
		return last_week_access;
	}
	public void setLast_week_access(int last_week_access) {
		this.last_week_access = last_week_access;
	}
	public int getAll_time_access() {
		return all_time_access;
	}
	public void setAll_time_access(int all_time_access) {
		this.all_time_access = all_time_access;
	}
	
	
}
