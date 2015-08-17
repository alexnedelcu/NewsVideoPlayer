package com.alexnedelcu.videoplayer.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import com.alexnedelcu.videoplayer.search.SearchEngine;


public class CtrlSearch extends Ctrl {

	public void search(JTextField txtSearch, KeyEvent arg0) {
		String textToSearch = txtSearch.getText();
		
		SearchEngine se = new SearchEngine();
		se.search(textToSearch);
	}
	
}












