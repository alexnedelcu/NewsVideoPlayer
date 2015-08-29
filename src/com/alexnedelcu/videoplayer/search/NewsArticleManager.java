package com.alexnedelcu.videoplayer.search;

import java.util.ArrayList;
import java.util.List;

public class NewsArticleManager {
	private static NewsArticleManager instance;
	List<NewsArticle> list;
	
	NewsArticleManager() {
		list = new ArrayList<NewsArticle>();
	}
	
	public static NewsArticleManager getInstance () {
		if (instance == null) {
			instance = new NewsArticleManager();
		}
		return instance;
	}
	
	public void addNewsArticle (NewsArticle article) {
		list.add(article);
	}
}
