package com.alexnedelcu.videoplayer.controllers;

import java.awt.event.ActionEvent;

import com.alexnedelcu.videoplayer.SourceManager;
import com.alexnedelcu.videoplayer.views.MainView;
import com.alexnedelcu.videoplayer.views.NewSourceView;

public class CtrlEnterNewSource extends Ctrl {

	
	public void handleMainViewEnterButtonPressed(ActionEvent e) {
		NewSourceView.getInstance().open();
	}
	
	public void addNewSource(String name, String url) {
		SourceManager.getInstance().addAvailableSource(name, url);
		NewSourceView.getInstance().close();
	}
}
