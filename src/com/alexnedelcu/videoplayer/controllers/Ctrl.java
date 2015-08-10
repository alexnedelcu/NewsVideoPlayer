package com.alexnedelcu.videoplayer.controllers;
import java.awt.event.ActionEvent;

import com.alexnedelcu.videoplayer.SourceManager;
import com.alexnedelcu.videoplayer.views.MainView;


public abstract class Ctrl {
	String searchPhrase;
	SourceManager sourceManager = SourceManager.getInstance();
	MainView renderer = MainView.getInstance();
	
	
	
}
