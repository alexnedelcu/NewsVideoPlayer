package com.alexnedelcu.videoplayer.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import com.alexnedelcu.videoplayer.Source;
import com.alexnedelcu.videoplayer.SourceManager;
import com.alexnedelcu.videoplayer.search.NewsArticle;
import com.alexnedelcu.videoplayer.search.SearchEngine;


public class CtrlSearch extends Ctrl {

	public void search(JTextField txtSearch) {
		String textToSearch = txtSearch.getText() + " ";
		
		renderer.showLoadingScreen();
		
		ArrayList<Source> sites = SourceManager.getInstance().getSelectedSources();
		for (int i=0; i<sites.size(); i++) {
			textToSearch += "site:"+sites.get(i).getUrl();
			if (sites.size()>1 && i<sites.size()-1) 
				textToSearch += " OR ";
			else
				textToSearch += " ";
		}
		System.out.println(textToSearch);
		
		SearchEngine se = new SearchEngine();
		List<NewsArticle> results = se.search(textToSearch);
		
		for (int i=0; i<results.size(); i++) 
			System.out.println(results.get(i).getTitle() + " - " + results.get(i).getUrl());
		
		renderer.displaySearchResults();
	}
	
}












