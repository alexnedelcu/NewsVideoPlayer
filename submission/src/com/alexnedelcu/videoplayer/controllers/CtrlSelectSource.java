package com.alexnedelcu.videoplayer.controllers;

import com.alexnedelcu.videoplayer.Source;
import com.alexnedelcu.videoplayer.SourceManager;

public class CtrlSelectSource extends Ctrl {
	public void selectSource(String name) {
		Source s = SourceManager.getInstance().getAvailableSourceByName(name);
		
		if (s != null) {
			SourceManager.getInstance().addSelectedSource(s);
			SourceManager.getInstance().removeAvailableSource(s);
		}
	}
	
	public void unselectSource(String name) {
		Source s = SourceManager.getInstance().getSelectedSourceByName(name);
		if (s != null) {
			SourceManager.getInstance().addAvailableSource(s);
			SourceManager.getInstance().removeSelectedSource(s);
		}
	}
}
