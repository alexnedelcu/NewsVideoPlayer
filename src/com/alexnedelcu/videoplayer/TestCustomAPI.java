package com.alexnedelcu.videoplayer;

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

public class TestCustomAPI {

	public static void main(String[] args) {
		String searchText = "football";
		String key = "AIzaSyAMWIXeB5a6oWb4SFqZJ2--C8JJekRayBI";
		String cx = "005508658984809288923:ypml9ppzazk";

		HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {

			@Override
			public void initialize(HttpRequest request) throws IOException {

			}
		};
		JsonFactory jsonFactory = new JacksonFactory();

		Customsearch custom = new Customsearch(new NetHttpTransport(),
				jsonFactory, httpRequestInitializer);

		Customsearch.Cse.List list;


		Search result;
		try {
			list = custom.cse().list("football");
			list.setCx(cx);
			list.setKey(key);
			
			result = list.execute();
			List listResult = (List) result.getItems();
			Result first = (Result) listResult.get(0);
			System.out.println(first.getHtmlTitle());;
		} catch (IOException e) {
			e.printStackTrace();
		
		}

	}
}
