package com.alexnedelcu.videoplayer.views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.alexnedelcu.videoplayer.controllers.CtrlEnterNewSource;
import com.alexnedelcu.videoplayer.controllers.CtrlRemoveAvailableSource;
import com.alexnedelcu.videoplayer.controllers.CtrlSearch;
import com.alexnedelcu.videoplayer.controllers.CtrlSelectSource;


public class MainView extends JFrame {

	public static MainView window;
	private static Container panel;
	private static JPanel rightPanel;
	private static JPanel leftPanel;
	private static JPanel rightTopPanel;
	private static JPanel rightBottomPanel;
	private static JPanel pnlSelectedSource;
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

		NativeSwing.initialize();
	    NativeInterface.open();
	    UIUtils.setPreferredLookAndFeel();
	    
	    EventQueue.invokeLater(new Runnable() {
//	    SwingUtilities.invokeLater(new Runnable() {
        
            @Override
            public void run() {
                window.setTitle("Video News Player");
                window.setSize(600, 600);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		
                panel = window.getContentPane();
                panel.setLayout(new GridBagLayout());

                leftPanel = new JPanel();
                rightPanel = new JPanel();

                rightPanel.setLayout(new GridBagLayout());

                rightTopPanel = new JPanel();
                rightBottomPanel = new JPanel();

        		GridBagConstraints cstrRightPane = new GridBagConstraints();
                cstrRightPane.fill = GridBagConstraints.BOTH;			//maximum width
                cstrRightPane.anchor=GridBagConstraints.FIRST_LINE_START;
                cstrRightPane.gridheight=1;
                cstrRightPane.gridwidth=1;
                cstrRightPane.gridx=0;
                cstrRightPane.gridy=0;
                cstrRightPane.weightx=1;
                cstrRightPane.weighty=1;
                
                rightPanel.add(rightTopPanel, cstrRightPane);
                
                cstrRightPane.weightx=0;
                cstrRightPane.weighty=0;
                cstrRightPane.gridy=1;
                
                rightPanel.add(rightBottomPanel, cstrRightPane);


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
        
                initializeLeftMenu();
                initializeRightBottomPanel();
                initializeRightTopPanel();
                
                window.setVisible(true);
            }
        });
	    NativeInterface.runEventPump();
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
	public static void initializeLeftMenu() {
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
		pnlSelectedSource = new JPanel();
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
        final JTextField txtSearch = new JTextField();
        txtSearch.setText(initText);
        txtSearch.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				System.out.println(arg0.getKeyChar());
				if(arg0.getKeyChar() == '\n')
					new CtrlSearch().search(txtSearch, arg0);
				
			}
        	
        });
        leftPanel.add(txtSearch, c);
	}
	
	public static void initializeRightBottomPanel() {
		rightBottomPanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("List of video thumbnails ");
		
		JScrollPane videoList = new JScrollPane(label);
        videoList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        videoList.setMinimumSize(new Dimension(200, 100));
		rightBottomPanel.add(videoList);
	}
	public static void initializeRightTopPanel() {
		
		rightTopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightTopPanel.setLayout(new BorderLayout());

	    final JWebBrowser webBrowser = new JWebBrowser();
	    webBrowser.setBarsVisible(false);
	    webBrowser.setMenuBarVisible(false);
	    webBrowser.navigate("http://www.google.com");
	    rightTopPanel.add(webBrowser, BorderLayout.CENTER);
	    
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
