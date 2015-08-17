package com.alexnedelcu.videoplayer.search;

import java.io.IOException;
import java.util.List;

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
	
	HttpRequestInitializer httpRequestInitializer;
	JsonFactory jsonFactory;
	Customsearch customSearch;
	List list;
	
	public SearchEngine () {

		httpRequestInitializer = new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {

			}
		};
		jsonFactory = new JacksonFactory();

		customSearch = new Customsearch(new NetHttpTransport(),
				jsonFactory, httpRequestInitializer);

		
	}
	
	public List<Result> search(String term) {

		Customsearch.Cse.List list;
		Search result;
		
		try {
			list = customSearch.cse().list(term);
			list.setCx(engineID);
			list.setKey(key);
			
			result = list.execute();
			List listResult = (List) result.getItems();

			return listResult;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
