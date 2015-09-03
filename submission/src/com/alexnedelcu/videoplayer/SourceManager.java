package com.alexnedelcu.videoplayer;
import java.util.ArrayList;

import com.alexnedelcu.videoplayer.views.MainView;


public class SourceManager {
	ArrayList<Source> availableSources = new ArrayList<Source>();
	ArrayList<Source> selectedSources = new ArrayList<Source>();
	static SourceManager instance;
	
	public static SourceManager getInstance() {
		if (instance==null)
			instance  = new SourceManager();
		
		return instance;
	}
	
	public void addAvailableSource(Source s){
		availableSources.add(s);
		
		// add the source to the MainView
		MainView.getInstance().addAvailableSource(s.getName(), s.getUrl());
	}
	public void addAvailableSource(String name, String url) {
		Source s = new Source(name, url);
		this.addAvailableSource(s);
	}
	public void removeAvailableSource(Source s) {
		availableSources.remove(s);
		
		// add the source to the MainView
		MainView.getInstance().removeAvailableSource(s.getName(), s.getUrl());
	}

	public Source getAvailableSourceByName(String name) {
		for (int i=0; i<availableSources.size(); i++) {
			if (availableSources.get(i).getName().toLowerCase().equals(name.toLowerCase()))
				return availableSources.get(i);
		}
		return null;
	}
	

	public Source getSelectedSourceByName(String name) {
		for (int i=0; i<selectedSources.size(); i++) {
			if (selectedSources.get(i).getName().toLowerCase().equals(name.toLowerCase()))
				return selectedSources.get(i);
		}
		return null;
	}

	public ArrayList<Source> getSelectedSources() {
		return selectedSources;
	}

	public void addSelectedSource(Source s) {
		selectedSources.add(s);
		
		// add the source to the MainView
		MainView.getInstance().addSelectedSource(s.getName(), s.getUrl());
	}
	public void removeSelectedSource(Source s) {
		availableSources.add(s);
		
		// add the source to the MainView
		MainView.getInstance().removeSelectedSource(s.getName(), s.getUrl());
	}
}
