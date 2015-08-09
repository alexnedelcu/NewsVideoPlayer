package com.alexnedelcu.videoplayer.controllers;

import com.alexnedelcu.videoplayer.Source;
import com.alexnedelcu.videoplayer.SourceManager;

public class CtrlSelectSource {
	public void selectSource(String name) {
		Source s = SourceManager.getInstance().getAvailableSourceByName(name);
		if (s != null) {
			SourceManager.getInstance().addSelectedSource(s);
		}
	}
	
	public void unselectSource(Source s) {
		System.out.println(s.getName());
	}
}
