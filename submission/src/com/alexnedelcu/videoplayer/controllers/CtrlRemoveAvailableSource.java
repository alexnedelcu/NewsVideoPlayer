package com.alexnedelcu.videoplayer.controllers;

import com.alexnedelcu.videoplayer.Source;
import com.alexnedelcu.videoplayer.SourceManager;

public class CtrlRemoveAvailableSource extends Ctrl {
	public void removeSrc(String name) {
		Source s = SourceManager.getInstance().getAvailableSourceByName(name);
		if (s != null) {
			SourceManager.getInstance().removeAvailableSource(s);
		}
	}
}
