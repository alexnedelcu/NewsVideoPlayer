package com.alexnedelcu.videoplayer.search;

import java.util.ArrayList;
import java.util.List;

public class NewsArticleManager {
	private static NewsArticleManager instance;
	List<NewsArticle> list;
	List<Command> actionsWhenChanged = new ArrayList<Command>();
	
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
//		article.setNewsManager(this;)
		list.add(article);
	}

	public List<NewsArticle> getNewsArticles() {
		return list;
	}
	
//	public void handleNewsArticleChange () {
//		for (int i=0; i<actionsWhenChanged.size(); i++) {
//			actionsWhenChanged.get(i).execute(this);
//		}
//	}
	
	
}
