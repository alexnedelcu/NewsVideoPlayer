package com.alexnedelcu.videoplayer.search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.HTMLHighlighter;

public class NewsArticle {
	private String title,  url, imgUrl=null;
	private String content;
	NewsArticleManager newsManager;
	Thread loadingThread;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Thread load() {
		loadingThread = new Thread(new Runnable () {

			@Override
			public void run() {
//				try {
					imgUrl = extractImage(url);
					
					
					/*
					 * Retrieve the content of the news article
					 */

				    // choose from a set of useful BoilerpipeExtractors...
//				    final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//				     final BoilerpipeExtractor extractor = CommonExtractors.DEFAULT_EXTRACTOR;
				    // final BoilerpipeExtractor extractor = CommonExtractors.CANOLA_EXTRACTOR;
//				    final BoilerpipeExtractor extractor = CommonExtractors.LARGEST_CONTENT_EXTRACTOR;

				    // choose the operation mode (i.e., highlighting or extraction)
//				    final HTMLHighlighter hh = HTMLHighlighter.newHighlightingInstance();
				    // final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();

//				    PrintWriter out;
//				    
//					out = new PrintWriter("/tmp/highlighted.html", "UTF-8");
//				    out.println("<base href=\"" + url + "\" >");
//				    out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
//				    out.println(hh.process(urlObj, extractor));
//				    out.close();
				    
//				    content = hh.process(urlObj, extractor);
				    
//				    System.out.println(url);
//				    System.out.println(content);
//				    System.out.println("\n\n\n\n\n\n\n\n\n\n\n");

//				    System.out.println("Now open file:///tmp/highlighted.html in your web browser");

//					} catch (FileNotFoundException
//							| UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (BoilerpipeProcessingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (SAXException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			}
		});
		loadingThread.start();
		return loadingThread;
	}

	public String getThumbUrl() {
		if  (imgUrl == null)
			return "http://www.vineyard2door.com/images/empty_thumbnail.gif";
		
		return imgUrl;
	}
	
	private String extractImage(String url) {
//		String url="http://www.philly.com/philly/sports/eagles/20150901_Riley_Cooper_all_but_invisible_in_preseason.html";
//		url="http://www.google.com";
		String urlImage=null;
		try {
			URL urlObj =  new URL(url);

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
		return urlImage;
	}

	public boolean isImage(String urlString) {
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
	
	public String getContent() {
		if (content == null) {
			if (!loadingThread.isAlive())  loadingThread.start();
			try {
				loadingThread.join(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (content == null) content = "Article is not available";
		}
		return content;
	}

}
