package com.cloudflare.urlshortener.DAO;

import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Database Connection class. Maintain connection to MongoDB Server.
 * @author hemil
 *
 */
public class DBConnectionManager {

	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public DBConnectionManager() throws UnknownHostException {
		
		mongoClient = new MongoClient("localhost",27017);
		db = mongoClient.getDatabase("Cloudflare-URLShortener");
		collection = db.getCollection("ShortToLong");
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}
	
	public void close(){
		mongoClient.close();
	}
	
}
