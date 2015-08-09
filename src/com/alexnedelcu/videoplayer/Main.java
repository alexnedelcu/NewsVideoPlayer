package com.alexnedelcu.videoplayer;

import com.alexnedelcu.videoplayer.views.MainView;

public class Main {

	static MainView mainView;
	public static void main(String argv[]) {
		mainView = MainView.getInstance();
		mainView.display();
	}
}
