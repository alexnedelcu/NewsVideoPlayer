package com.alexnedelcu.videoplayer.views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.border.EmptyBorder;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.alexnedelcu.videoplayer.controllers.CtrlEnterNewSource;
import com.alexnedelcu.videoplayer.controllers.CtrlRemoveAvailableSource;
import com.alexnedelcu.videoplayer.controllers.CtrlSearch;
import com.alexnedelcu.videoplayer.controllers.CtrlSelectSource;
import com.alexnedelcu.videoplayer.search.NewsArticle;
import com.alexnedelcu.videoplayer.search.NewsArticleManager;


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
	    
	    SwingUtilities.invokeLater(new Runnable() {
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
					new CtrlSearch().search(txtSearch);
				
			}
        	
        });
        leftPanel.add(txtSearch, c);
        

		/*
		 * Adding the Search Button
		 */
        c.gridy=5;
        c.weighty=0;
        final JButton btnSearch = new JButton();
        btnSearch.setText("Search");
        btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CtrlSearch().search(txtSearch);
			}
		});
        leftPanel.add(btnSearch, c);
	}
	static JPanel newsListPanel;
	public static void initializeRightBottomPanel() {
		rightBottomPanel.setLayout(new BorderLayout());
		
//		JLabel label = new JLabel("List of video thumbnails ");
		newsListPanel = new JPanel();
		newsListPanel.setLayout(new FlowLayout());

		JScrollPane videoList;
		videoList = new JScrollPane(newsListPanel);
        videoList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        videoList.setMinimumSize(new Dimension(500, 215));
		rightBottomPanel.add(videoList);
	}
	
	public static JWebBrowser webBrowser;
	public static void initializeRightTopPanel() {
		
		rightTopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightTopPanel.setLayout(new BorderLayout());

		webBrowser = new JWebBrowser();
	    webBrowser.setBarsVisible(false);
	    webBrowser.setMenuBarVisible(false);
	    webBrowser.navigate("http://news.google.com");
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
        c.anchor=GridBagConstraints.LINE_START;
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
	
	
	final static double thumbMaxWidth=160.0,  thumbMaxHeight=120.0;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	static JLabel selectedLabel1=null,  selectedLabel2;
	public void displaySearchResults() {
		

		newsListPanel.removeAll();
		for(int i=0; i<labels.size(); i++) {
			newsListPanel.remove(labels.get(i));
		}
		
		final List<NewsArticle> news = NewsArticleManager.getInstance().getNewsArticles();

		GridBagLayout layout = new  GridBagLayout();

		GridBagConstraints c = new GridBagConstraints();
		c.ipadx=0;
		c.ipady=0;
		c.fill=c.BOTH;
		
		newsListPanel.setLayout(layout);

		System.out.println(news.size());

		try {
			showLoadingScreen();
			
			for (int i=0; i<news.size(); i++) {
				final NewsArticle na = news.get(i);

				c.gridx=i;
				c.gridy=0;
				
				URL url;
				url = new URL(na.getThumbUrl());
				Image image;
				image = ImageIO.read(url);

				double height = image.getHeight(null)	;
				double width = image.getWidth(null);
				
				if (height * 4 > width * 3) {
					width = width / (height/thumbMaxHeight);
					height = thumbMaxHeight;
				} else {

					height = height / (width/thumbMaxWidth);
					width = thumbMaxWidth;
				}
				Image thumb = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
		        final JLabel label = new JLabel(new ImageIcon(thumb));
		        label.setBorder(new EmptyBorder(20,10,5,10));

		        label.setHorizontalAlignment(JLabel.CENTER);

		        /*
		         * Display the title of the article
		         */
		        String title = na.getTitle(), formattedTitle;
				title = title + "       ";
				System.out.println(title);
				String[] part = title.split("\\s");
				formattedTitle = "<html><center> "+part[0] +  " ";
				if (part.length>1) formattedTitle += part[1] + " ";
				if (part.length>2) formattedTitle += part[2] + " ";
				if (part.length>3) formattedTitle += part[3] + " <br />";
				if (part.length>4) formattedTitle += part[4] + " ";
				if (part.length>5) formattedTitle += part[5] + " ";
				if (part.length>6) formattedTitle += part[6] + " ";
				if (part.length>7) formattedTitle += part[7];
				
				if (part.length<4) formattedTitle += "<br /> ";
				if (part.length>8) formattedTitle += "...";
				
				formattedTitle += "</center></html>";
				final JLabel lbl = new JLabel(formattedTitle);
				lbl.setBorder(new EmptyBorder(5,10,15,10));
				lbl.setHorizontalAlignment(JLabel.CENTER);
				
				final int k=i;
		        MouseListener clickAction = new MouseListener() {
		        	
		        	boolean isSelected=false;
		        	
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						if(selectedLabel1 != null) deselectPreviousNews();
						lbl.setOpaque(true);
						label.setOpaque(true);
						lbl.setBackground(Color.DARK_GRAY);
						label.setBackground(Color.DARK_GRAY);
						lbl.setForeground(Color.WHITE);
						label.setForeground(Color.WHITE);
						loadTopRightPane(na);
						selectedLabel1 = lbl;
						selectedLabel2 = label;
						
					}
					
				};

		        label.addMouseListener(clickAction);
				lbl.addMouseListener(clickAction);
				
				newsListPanel.add(label, c);
				c.gridy=1;
				newsListPanel.add(lbl,  c);
				
				labels.add(label);
				labels.add(lbl);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		window.pack();
        window.setVisible(true);


	}
	
	public void deselectPreviousNews() {
		selectedLabel1.setBackground(Color.LIGHT_GRAY);
		selectedLabel2.setBackground(Color.LIGHT_GRAY);
//		labels.get(selectedLabel).setBackground(Color.GRAY);
//		labels.get(selectedLabel+1).setBackground(Color.GRAY);
		window.repaint();
	}
	
	
	public void showLoadingScreen() {
//		webBrowser.setHTMLContent("<center valign=\"middle\"><img src=\"https://portal.ehawaii.gov/assets/images/loading.gif\"></center>");
//		webBrowser.setHTMLContent("Loading");
//		System.out.println("Progress : "+webBrowser.getLoadingProgress());
		
		
	}
	
	
	
	public void hideLoadingScreen() {
		webBrowser.setHTMLContent("");
	}
	
	public void loadTopRightPane (NewsArticle na) {
		
		String content = na.getTitle()+"<br>";
		content += "Link to article: "+na.getUrl();
//		content += na.getContent();
		
//		webBrowser.setHTMLContent(content);
		webBrowser.navigate(na.getUrl());
	}
	
}
