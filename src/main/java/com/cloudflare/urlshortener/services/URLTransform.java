package com.cloudflare.urlshortener.services;

import java.net.UnknownHostException;

import org.bson.Document;

import com.cloudflare.urlshortener.DAO.DBConnectionManager;
import com.cloudflare.urlshortener.DAO.DocumentDAOImpl;
import com.cloudflare.urlshortener.models.ListingsTable;
import com.cloudflare.urlshortener.utility.URLTransformUtil;
import com.mongodb.client.MongoCollection;

/**
 * Implement services for transforming the Long Url into a short one.
 * @author hemil
 *
 */
public class URLTransform {

	public int getShortenedUrl(String data) throws UnknownHostException{
		URLTransformUtil urlTransformUtil = new URLTransformUtil();
		int id = urlTransformUtil.shortenURL(data);
	
		DocumentDAOImpl daoImpl = new DocumentDAOImpl();
		
		int value = daoImpl.checkRecords(data);
		if(value != -1){
			id = value;
			++id;
		}
		
		daoImpl.insertData(""+id, data);
		
		return id;		
	}
	
	//Find the longUrl from the given shortUrl id
	public String redirectToLongUrl(int data){
	
		DocumentDAOImpl daoImpl = new DocumentDAOImpl();
		
		String longUrl = daoImpl.findLongUrl(data);
		
		System.out.println("final longUrl: "+longUrl);
		return longUrl;		
	}

	// Call the DAO for listing the shortUrl access
	public ListingsTable getListings(int id) {
		// TODO Auto-generated method stub
		DocumentDAOImpl daoImpl = new DocumentDAOImpl();
				
		ListingsTable listings = daoImpl.getTableListings(id);
		
		return listings;
	}
}
