package com.alexnedelcu.videoplayer.views;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alexnedelcu.videoplayer.controllers.CtrlEnterNewSource;
import com.alexnedelcu.videoplayer.controllers.CtrlRemoveAvailableSource;
import com.alexnedelcu.videoplayer.controllers.CtrlSearch;
import com.alexnedelcu.videoplayer.controllers.CtrlSelectSource;


public class MainView extends JFrame {

	public static MainView window;
	private static Container panel;
	private static Container rightPanel;
	private static Container leftPanel;
	private static Container rightTopPanel;
	private static Container rightBottomPanel;
	private static Container pnlSelectedSource;
	private static JComboBox currentSources;
	private static JScrollPane scrSourceArea;
	
	public MainView() {
		window = this;
	}
	
	public void display() {
		initializeWindow();
	}

	/*
	 * Create split the window in 3 main areas: leftPanel, rightTopPanel, rightBottomPanel.
	 * The leftPanel stays fixed - does not resize horizontally while the window is resized.
	 */
	public void initializeWindow() {
        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                window.setTitle("Video News Player");
                window.setSize(600, 600);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		
                panel = window.getContentPane();
                panel.setLayout(new GridBagLayout());

                leftPanel = new Panel();
                rightPanel = new Panel();

                rightPanel.setLayout(new GridBagLayout());

                Container rightTopPane = new Panel();
                Container rightBottomPane = new Panel();

        		GridBagConstraints cstrRightPane = new GridBagConstraints();
                cstrRightPane.fill = GridBagConstraints.BOTH;			//maximum width
                cstrRightPane.anchor=GridBagConstraints.FIRST_LINE_START;
                cstrRightPane.gridheight=1;
                cstrRightPane.gridwidth=1;
                cstrRightPane.gridx=0;
                cstrRightPane.gridy=0;
                cstrRightPane.weightx=1;
                cstrRightPane.weighty=1;
                
                rightPanel.add(rightTopPane, cstrRightPane);
                
                cstrRightPane.weightx=0;
                cstrRightPane.weighty=0;
                cstrRightPane.gridy=1;
                
                rightPanel.add(rightBottomPane, cstrRightPane);


        		GridBagConstraints cstrMainPane = new GridBagConstraints();
        		cstrMainPane.fill = GridBagConstraints.BOTH;			//maximum width
        		cstrMainPane.gridheight=1;
        		cstrMainPane.gridwidth=1;
        		cstrMainPane.gridx=0;
        		cstrMainPane.gridy=0;
        		cstrMainPane.weightx=0;
        		cstrMainPane.weighty=1;
                
                panel.add(leftPanel, cstrMainPane);

        		cstrMainPane.gridx=1;
        		cstrMainPane.weightx=1;
                panel.add(rightPanel, cstrMainPane);
        
                initializeRightMenu();
                
                window.setVisible(true);
            }
        });
	}
	
	/*
	 * Initialize the right menu: 
	 * 	buttons:
	 * 		- enter new source
	 * 		- use source
	 * 		- remove source
	 * 	text input:
	 * 		- search topic
	 */
	public static void initializeRightMenu() {
		leftPanel.setLayout(new GridBagLayout());
		
		/*
		 * Creating the grid bag contraints
		 */
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;			//maximum width
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.gridheight=1;
        c.gridwidth=2;
        c.gridx=0;
        c.gridy=0;
        c.weightx=0;
        c.weighty=0;
		
        
        /*
         * Adding the Enter new source button
         */
		JButton btnEnterNewSource = new JButton();
		btnEnterNewSource.setText("Enter new source");
		btnEnterNewSource.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				new CtrlEnterNewSource().handleMainViewEnterButtonPressed(a);
			}
		});
		leftPanel.add(btnEnterNewSource, c);
		
		
		/*
		 * Adding the drop down select
		 */
        c.gridy=1;
        currentSources = new JComboBox();
        leftPanel.add(currentSources, c);
		
		/*
		 * Adding the Use Source Button
		 */
        c.gridwidth=1;
        c.gridy=2;
		JButton btnUseSource = new JButton();
		btnUseSource.setText("Use source");
		btnUseSource.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlSelectSource().selectSource((String) currentSources.getSelectedItem());
			}
			
		});
		leftPanel.add(btnUseSource, c);
		
		/*
		 * Adding the Remove Source Button
		 */
        c.gridx=1;
        JButton btnRemoveSource = new JButton();
        btnRemoveSource.setText("Remove source");
        btnRemoveSource.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CtrlRemoveAvailableSource().removeSrc((String) currentSources.getSelectedItem());
			}
        });
        leftPanel.add(btnRemoveSource, c);
        
		/*
		 * Adding the scrollable area containing the selected news sources
		 */
        c.gridx=0;
        c.gridy=3;
        c.gridwidth=2;
        c.weighty=1;
		scrSourceArea = new JScrollPane();
		pnlSelectedSource = new Panel();
		pnlSelectedSource.setLayout(new GridBagLayout());
//		scrSourceArea.setLayout(new GridBagLayout());
//		scrSourceArea.add(pnlSelectedSource);
		leftPanel.add(pnlSelectedSource, c);
        
		/*
		 * Adding the Search text box
		 */
        c.gridy=4;
        c.weighty=0;
        String initText = "Enter topic...";
        JTextField txtSearch = new JTextField();
        txtSearch.setText(initText);
        txtSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlSearch().search(e);
			}
		});
        leftPanel.add(txtSearch, c);
	}


	/*
	 * Singleton pattern
	 */
	public static MainView getInstance() {
		if(window == null) window = new MainView();
		
		return window;
	}

	public void addAvailableSource(String name, String url) {
		currentSources.addItem(name);
	}

	public void removeAvailableSource(String name, String url) {
		currentSources.removeItem(name);
	}

	int numSelectedSources=0;
	public void addSelectedSource(final String name, String url) {
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor=GridBagConstraints.FIRST_LINE_START;
        c.gridheight=1;
        c.gridwidth=1;
        c.gridx=0;
        c.gridy=numSelectedSources;
        c.weightx=0;
        c.weighty=0;
        
        /*
         * add the remove button
         */
        JButton btnRemoveSrc;
		try {
	        BufferedImage buttonIcon;
			buttonIcon = ImageIO.read(new File("res/16x16-black-white-metro-delete-icon.png"));
			btnRemoveSrc = new JButton(new ImageIcon(buttonIcon));
	        btnRemoveSrc.setBorder(BorderFactory.createEmptyBorder());
	        btnRemoveSrc.setContentAreaFilled(false);
		} catch (IOException e) {
			btnRemoveSrc = new JButton("x");
			e.printStackTrace();
		}
		btnRemoveSrc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CtrlSelectSource().unselectSource(name);
			}
		});

        pnlSelectedSource.add(btnRemoveSrc, c);
		
        /*
         *  add the source name
         */
        JLabel source = new JLabel(name);
        c.gridx=1;
        pnlSelectedSource.add(source, c);
  
        window.setVisible(true);
        
        numSelectedSources++;
	}

	public void removeSelectedSource(String name, String url) {
		for (int i=0; i<pnlSelectedSource.getComponentCount(); i++) {
			Component c = pnlSelectedSource.getComponent(i);
			String classname = c.getClass().getSimpleName();
			classname.length();
			if (classname.equals("JLabel"))
				if (((JLabel)c).getText().equals(name)) {
					pnlSelectedSource.remove(i);
					pnlSelectedSource.remove(i-1);
				}
		}
		
        pnlSelectedSource.setVisible(false);
        pnlSelectedSource.setVisible(true);
		
	}
	
}
