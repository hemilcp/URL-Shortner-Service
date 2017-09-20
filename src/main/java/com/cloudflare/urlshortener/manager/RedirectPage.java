package com.cloudflare.urlshortener.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudflare.urlshortener.services.URLTransform;

/**
 * Another servlet that sits behind '/shortly' domain. Any request made on that domain
 * with a shortUrl postfix would redirect to the LongUrl page.
 * @author hemil
 *
 */
public class RedirectPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RedirectPage(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String shortUrl = request.getRequestURI().toString();
		System.out.println(shortUrl);

		try{
			int str = Integer.parseInt(shortUrl.split("/")[3]);
			System.out.println(str);
			URLTransform transform = new URLTransform();

			String longUrl = transform.redirectToLongUrl(str);
			response.sendRedirect(longUrl);
		}catch(Exception e){
			String re = shortUrl.substring(shortUrl.lastIndexOf("/")+1,shortUrl.length());
			response.sendRedirect("https://"+re);
		}
	}



}
