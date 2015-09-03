package com.alexnedelcu.videoplayer.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alexnedelcu.videoplayer.SourceManager;
import com.alexnedelcu.videoplayer.views.MainView;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class SearchEngine {
	final String key = "AIzaSyAMWIXeB5a6oWb4SFqZJ2--C8JJekRayBI";
	final String engineID = "005508658984809288923:ypml9ppzazk";
	SearchEngine instance;
	
	static HttpRequestInitializer httpRequestInitializer;
	static JsonFactory jsonFactory;
	static Customsearch customSearch;
	
	
	public SearchEngine () {
		if (httpRequestInitializer == null) {

		httpRequestInitializer = new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {
				
			}
		};
		jsonFactory = new JacksonFactory();

		customSearch = new Customsearch(new NetHttpTransport(),
				jsonFactory, httpRequestInitializer);

		}
		
	}
	
	public List<NewsArticle> search(String term) {

		Customsearch.Cse.List list;
		Search result;
		ArrayList<Thread> loadingThreads = new ArrayList<Thread>();
		
		try {
			list = customSearch.cse().list(term);
//			list.setNum((long)20);
			list.setCx(engineID);
			list.setKey(key);
			
			result = list.execute();
			List<Result> listResult = (List<Result>) result.getItems();

			if(result.getItems().size() > 0)
				MainView.getInstance().webBrowser.navigate(listResult.get(0).getLink());
			
			for (int i=0; i<listResult.size(); i++) {
				
				
				NewsArticle na = new NewsArticle();
				na.setTitle(listResult.get(i).getTitle());
				na.setUrl(listResult.get(i).getLink());
				loadingThreads.add(na.load());
				
				
				NewsArticleManager.getInstance().addNewsArticle(na);
			}

			// Wait until all the threads load all the info about these articles
			for (int i=0; i<loadingThreads.size(); i++) 
				loadingThreads.get(i).join();
			
			
			List<NewsArticle> articles = NewsArticleManager.getInstance().getNewsArticles();
			return articles;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
