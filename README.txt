Scope: Implement Restful webservices for a URL-Shortener-Service.

Technologies used: 
Java 7
Maven 3
Jersey-server
Tomcat server8.5
MongodB 3.4
Eclipse IDE


To compile and run:
1) Install Java 7, MongoDB.
2) configure MongoDB locally and start mongo service. (If you choose cloud-based DB, use the database end point and credentials to load DB)
3) run maven build(clean install)
4) To shorten a web URL : make a POST request on http://localhost:8080/urlshortener/webapi/shortly/
	with Header {Content-Type : application/json} and
	payload { link: "YOUR WEB URL GOES HERE"}
5) To redirect to Web URL using shortened url, which is an integer value:
	Use : "http://localhost:8080/urlshortener/shortly/{YOUR-SHORTENED-ID}
6) To see the listings of how many times a shortened URL is accessed 
	Use : http://localhost:8080/urlshortener/webapi/shortly/listing/{YOUR-SHORTENED-ID}
	
For example,

method : POST  
address: http://localhost:8080/urlshortener/webapi/shortly/ 
data: { "link":"https://www.youtube.com" }

reponse: { "shortLink": "http://localhost:8080/urlshortener/shortly/694797224" }

Here shortened URL : 694797224

To access: "http://localhost:8080/urlshortener/shortly/694797224

To see usage: http://localhost:8080/urlshortener/webapi/shortly/listing/694797224



