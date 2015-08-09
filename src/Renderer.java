import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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


public class Renderer {

	public static JFrame window;
	private static Container panel;
	private static Container rightPanel;
	private static Container leftPanel;
	private static Container rightTopPanel;
	private static Container rightBottomPanel;
	private static Renderer instance;
	
	
	public void display() {
		initializeWindow();
	}
	
	

	public static void initializeWindow() {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
            	window = new JFrame();
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

//                JLabel leftSide = new JLabel();
//                leftSide.setText("Left side");
//                leftPanel.add(leftSide);
//
//                JLabel rightTopSide = new JLabel();
//                rightTopSide.setText("Right top side");
//                rightTopPane.add(rightTopSide);
//
//                JLabel rightBottomSide = new JLabel();
//                rightBottomSide.setText("Right bottom side");
//                rightBottomPane.add(rightBottomSide);
                
                initializeRightMenu();
                
                window.setVisible(true);
            }
        });
	}
	
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
		leftPanel.add(btnEnterNewSource, c);
		
		/*
		 * Adding the drop down select
		 */
        c.gridy=1;
        JComboBox currentSources = new JComboBox();
        leftPanel.add(currentSources, c);
		
		/*
		 * Adding the Use Source Button
		 */
        c.gridwidth=1;
        c.gridy=2;
		JButton btnUseSource = new JButton();
		btnUseSource.setText("Use source");
		leftPanel.add(btnUseSource, c);
		
		/*
		 * Adding the Remove Source Button
		 */
        c.gridx=1;
        JButton btnRemoveSource = new JButton();
        btnRemoveSource.setText("Remove source");
        leftPanel.add(btnRemoveSource, c);
        
		/*
		 * Adding the scrollable area containing the selected news sources
		 */
        c.gridx=0;
        c.gridy=3;
        c.gridwidth=2;
        c.weighty=1;
		JScrollPane scrSourceArea = new JScrollPane();
		scrSourceArea.add(new Panel());
		leftPanel.add(scrSourceArea, c);
        
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
				
			}
		});
        leftPanel.add(txtSearch, c);
        
        
	}



	public static Renderer getInstance() {
		if (instance == null)
			instance  = new Renderer();
		return instance;
	}
	
}
