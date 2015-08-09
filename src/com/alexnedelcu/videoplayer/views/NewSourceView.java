package com.alexnedelcu.videoplayer.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import com.alexnedelcu.videoplayer.controllers.CtrlEnterNewSource;

public class NewSourceView extends JFrame implements WindowListener {
	static NewSourceView window;
	Container contentPanel;
	
	public NewSourceView () {
		window = this;
		this.setupLayout();
	}
	
	public static NewSourceView getInstance() {
		if (window == null)
			window = new NewSourceView();
		return window;
	}
	
	public void setupLayout() {
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setTitle("New Source");
		window.setResizable(false);
		window.setSize(500, 100);
		window.addWindowListener(this);
		
		contentPanel = window.getContentPane();
		contentPanel.setLayout(new GridBagLayout());
		

		/*
		 * Set some constaints that are common for all the components on this window
		 */
		GridBagConstraints cstr = new GridBagConstraints();
		cstr.fill = GridBagConstraints.BOTH;			//maximum width
        cstr.anchor=GridBagConstraints.FIRST_LINE_START;
        cstr.gridheight=1;
        cstr.gridwidth=1;
        cstr.gridx=0;
        cstr.gridy=0;
        cstr.weightx=0;
        cstr.weighty=1;
        
		/*
		 * Adding the labels
		 */
		JLabel lblSourceName = new JLabel ("Source name:");
		JLabel lblURL = new JLabel ("URL:");

		contentPanel.add(lblSourceName, cstr);
		
        cstr.gridy=1;
		contentPanel.add(lblURL, cstr);
		
		/* 
		 * Adding the text components
		 */
		final JTextField txtSourceName = new JTextField();
		cstr.weightx=1;
		cstr.gridx=1;
		cstr.gridy=0;
		contentPanel.add(txtSourceName, cstr);

		final JTextField txtURL = new JTextField();
		cstr.gridy=1;
		contentPanel.add(txtURL, cstr);

		/*
		 * Adding the OK / Cancel buttons
		 */
		JButton btnOK = new JButton("Save");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlEnterNewSource().addNewSource(txtSourceName.getText(), txtURL.getText());
			}
		});
		
		cstr.gridy=2;
		cstr.gridx=0;
		contentPanel.add(btnOK, cstr);

		JButton btnCancel = new JButton("Cancel");
		cstr.gridx=1;
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.close();
			}
		});
		contentPanel.add(btnCancel, cstr);
	}
	
	public void open () {
		/*
		 * Displaying the window
		 */
		window.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	/*
	 * enable the main view once this window closes
	 */
	@Override
	public void windowClosing(WindowEvent arg0) {
		MainView.getInstance().setEnabled(true); 
		window=null;
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {	}

	/*
	 * disable the main window as long as this window is open
	 */
	@Override
	public void windowOpened(WindowEvent arg0) {
		MainView.getInstance().setEnabled(false);
	}

	public void close() {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
}