package com.cloudflare.urlshortener.DAO;

import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public interface DocumentDAO {

	void insertData(String shortUrl, String longUrl);
	int checkRecords(String longUrl);
	String findLongUrl(int id);
	void updateTimestamp(MongoCollection<Document> collection,int id, String longUrl);
}
