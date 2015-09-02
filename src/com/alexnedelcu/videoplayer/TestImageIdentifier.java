package com.alexnedelcu.videoplayer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestImageIdentifier {
	public static void main (String [] args) {
		String url="http://www.cnn.com/2015/08/25/world/cnnphotos-bucharest-romania-train-tunnels/";
//		url="http://www.google.com";
		try {
			URL urlObj =  new URL(url);
			String urlImage=null;
			
			/*
			 * Retrieve the thumbnail of the news article
			 */
			Document doc = Jsoup.connect(url).get();
			Elements newsHeadlines = doc.select("meta[name=twitter:image]");
			if(!newsHeadlines.isEmpty())
				if (isImage(newsHeadlines.attr("content")))
					urlImage=newsHeadlines.attr("content");

			newsHeadlines = doc.select("meta[property=twitter:image]");
			if(!newsHeadlines.isEmpty() && urlImage == null)
				if (isImage(newsHeadlines.attr("content")))
					urlImage=newsHeadlines.attr("content");

			newsHeadlines = doc.select("meta[name=og:image]");
			if(!newsHeadlines.isEmpty() && urlImage == null)
				if (isImage(newsHeadlines.attr("content")))
					urlImage=newsHeadlines.attr("content");
			
			newsHeadlines = doc.select("meta[property=og:image]");
			if(!newsHeadlines.isEmpty() && urlImage == null)
				if (isImage(newsHeadlines.attr("content")))
					urlImage=newsHeadlines.attr("content");
			
			
			
			if(urlImage == null) System.out.println("No image found");
			else System.out.println("Image found");

			if (urlImage!=null) {
				System.out.println("URL: " + url);
				System.out.print("Image URL ");
				System.out.println(urlImage);
				System.out.println("isImage: "+isImage(urlImage));
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public static boolean isImage(String urlString) {
		HttpURLConnection urlConnection;
//		String urlString = "http://www.youtube.com/v/oHg5SJYRHA0";
		try {
		    urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
		    urlConnection.setInstanceFollowRedirects(true);
		    HttpURLConnection.setFollowRedirects(true);

		    int status = urlConnection.getResponseCode();
		    if (status >= 300 && status <= 307) {
		        urlString = urlConnection.getHeaderField("Location");
		        urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
		        System.out.println("Redirect to URL : " + urlString);
		    }
		    String contentType = urlConnection.getHeaderField("Content-Type");
		    if (contentType.startsWith("image/")) {
		        return true;
		    } else if (contentType.equals("application/x-shockwave-flash")) {
		        //do something with a video
		        //} else ...
		    }
		    
		    return false;
//		    System.out.println(contentType);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return false;
	}
}
