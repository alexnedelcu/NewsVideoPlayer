package com.alexnedelcu.videoplayer;

import com.alexnedelcu.videoplayer.search.SearchEngine;
import com.alexnedelcu.videoplayer.views.MainView;

public class Main {

	static MainView mainView;
	public static void main(String argv[]) {
		new SearchEngine();
		
		mainView = MainView.getInstance();
		mainView.display();
	}
}
