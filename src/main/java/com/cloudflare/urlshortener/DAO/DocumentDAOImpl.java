package com.cloudflare.urlshortener.DAO;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.cloudflare.urlshortener.models.ListingsTable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.AggregationOutput;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

/**
 * Implement DAO Services.
 * @author hemil
 *
 */
public class DocumentDAOImpl implements DocumentDAO{

	/**
	 * Insert the shortUrl and LongUrl mapping into database.
	 */
	@Override
	public void insertData(String shortUrl, String longUrl) {
		// TODO Auto-generated method stub
		DBConnectionManager connectionManager = null;
		try {
			connectionManager = new DBConnectionManager();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MongoCollection<Document> coll =	connectionManager.getCollection();

		if(!(longUrl.startsWith("http", 0))){
			longUrl = "http://" + longUrl;
		}

		Document doc = new Document();
		doc.append("shortUrl", shortUrl);
		doc.append("longUrl", longUrl);

		coll.insertOne(doc);
		connectionManager.close();
	}

	/**
	 * Check if longUrl already exisits!
	 */
	@Override
	public int checkRecords(String longUrl) {

		DBConnectionManager connectionManager = null;
		try {
			connectionManager = new DBConnectionManager();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MongoCollection<Document> coll = connectionManager.getCollection();

		FindIterable<Document> doc = coll.find(Filters.eq("longUrl",longUrl))
				.sort(Sorts.orderBy(Sorts.descending("shortUrl")));

		for(Document d : doc){
			System.out.println("** "+d.toString());
		}

		int id = 0;

		if(doc.first() == null) id = -1;
		else id = Integer.parseInt(doc.first().get("shortUrl").toString());

		connectionManager.close();

		return id;
	}

	/**
	 * Find LongUrl of a shortened Url.
	 */
	
	@Override
	public String findLongUrl(int id) {
		DBConnectionManager connectionManager = null;
		try {
			connectionManager = new DBConnectionManager();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MongoCollection<Document> collection = connectionManager.getCollection();

		FindIterable<Document> document = collection.find(Filters.eq("shortUrl",""+id));

		String value = "";

		if(document.first() == null) value = "Not found";
		else {
			String objectId = document.first().get("_id").toString();
			value = document.first().getString("longUrl");

			updateTimestamp(collection,id,value);
		}


		System.out.println("Long URL returned:: "+value);
		return value;
	}

	/**
	 * Update how many times shortUrl has been accessed
	 */
	public void updateTimestamp(MongoCollection<Document> collection,int id, String longUrl) {

		Bson filter = Filters.and(Filters.eq("shortUrl",id), Filters.eq("longUrl", longUrl));

		Bson update = Updates.addToSet("lastIndex",new Document("Timestamp",new Date().getTime()));

		UpdateOptions options = new UpdateOptions().upsert(true);

		collection.updateOne(filter, update, options);
	}

	/**
	 * Get the table of how many times ShortUrl has been accessed within
	 * last 24 hours, last 7 days and overall time.
	 * @param id
	 * @return
	 */
	public ListingsTable getTableListings(int id) {
		// TODO Auto-generated method stub
		ListingsTable listingsTable = new ListingsTable();

		DBConnectionManager connectionManager = null;
		try {
			connectionManager = new DBConnectionManager();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MongoCollection<Document> collection = connectionManager.getCollection();

		long today = new Date().getTime();
		long lastday = today - (24*60*60*1000);
		long lastweek = today - (7 * 24*60*60*1000);

		listingsTable.setLast_day_access(findTotalAccess(id, today, lastday ));
		listingsTable.setLast_week_access(findTotalAccess(id, today, lastweek));
		listingsTable.setAll_time_access(findTotalAccess(id, today, Long.MAX_VALUE));

		return listingsTable;
	}

	private int findTotalAccess(int id, long start, long end){

		DBConnectionManager connectionManager = null;
		try {
			connectionManager = new DBConnectionManager();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MongoCollection<Document> collection = connectionManager.getCollection();

		Document match = Document.parse("{ $match: {'shortUrl': "+id+" }}");
		Document project_today = Document.parse("{$project:{shortUrl:1,lastIndex:{$filter:{input:'$lastIndex',as:'lastIndex',cond:{$gte:['$$lastIndex.Timestamp',"+start+"]}}}}}");
		List<Document> pipeline_today = Arrays.asList(project_today);
		AggregateIterable<Document> iterable_today = collection.aggregate(pipeline_today);
		ArrayList<Document> list = new ArrayList<>();

		for(Document doc : iterable_today){
			if( doc.get("lastIndex") != null)
				list =  (ArrayList<Document>) doc.get("lastIndex");
		}
		System.out.println(list.size());
		return list.size();
	}

}
