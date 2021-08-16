package com.jinvoice.view;

import javax.swing.*;
import java.awt.*;

/*
 * Application main window.
 */
public class MainWindow extends JFrame
{
	private final JPanel _mainPanel = new JPanel();
	
	private final JMenuBar _menuBar = new JMenuBar();
	private final JMenu _fileMenu = new JMenu("File");
	private final JMenuItem _fileExitMenuItem = new JMenuItem("Exit");
	private final JMenu _helpMenu = new JMenu("Help");
	private final JMenuItem _helpAboutMenuItem = new JMenuItem("About");
	
	private final ToPanel _toPanel = new ToPanel();
	private final DetailsPanel _detailsPanel = new DetailsPanel();
	private final ItemsTablePanel _itemsTablePanel = new ItemsTablePanel();
	private final TotalsPanel _totalsPanel = new TotalsPanel();
	private final NotesAndButtonsPanel _notesAndButtonsPanel = new NotesAndButtonsPanel();
	
	/*
	 * Constructor.
	 */
	public MainWindow(String title, String version, int windowWidth, int windowHeight, String[] attributions)
	{
		// set window properties
		this.setTitle(title);
		this.setSize(windowWidth, windowHeight);
		
		// create menubar
		this._fileMenu.add(this._fileExitMenuItem);
		this._menuBar.add(this._fileMenu);
		this._helpMenu.add(this._helpAboutMenuItem);
		this._menuBar.add(this._helpMenu);
		this.setJMenuBar(this._menuBar);
		this._fileExitMenuItem.addActionListener((event) -> System.exit(0));
		this._helpAboutMenuItem.addActionListener(((event) ->
		{
			JOptionPane.showMessageDialog(this, "Simple invoice generator. Creates invoices in Excel format. \n\n" + this.buildAttributionText(attributions), "About " + title, JOptionPane.INFORMATION_MESSAGE);
		}));
		
		this._mainPanel.setLayout(new GridBagLayout());
		
		// add components
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.BOTH;
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 2;
		c1.gridheight = 1;
		c1.weightx = 1; 
		c1.weighty = 1; 
		c1.insets = new Insets(2,2,2,2);
		this._mainPanel.add(this._toPanel, c1);
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.BOTH;
		c2.gridx = 2;
		c2.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c2.weightx = 0.5; 
		c2.weighty = 0; 
		c2.insets = new Insets(2,2,2,2);
		this._mainPanel.add(this._detailsPanel, c2);
		
		GridBagConstraints c3 = new GridBagConstraints();
		c3.fill = GridBagConstraints.BOTH;
		c3.gridx = 0;
		c3.gridy = 1;
		c3.gridwidth = 3;
		c3.gridheight = 1;
		c2.weightx = 1; 
		c2.weighty = 1; 
		c2.insets = new Insets(2,2,2,2);
		this._mainPanel.add(this._itemsTablePanel, c3);
		
		GridBagConstraints c4 = new GridBagConstraints();
		c4.fill = GridBagConstraints.BOTH;
		c4.gridx = 0;
		c4.gridy = 2;
		c4.gridwidth = 2;
		c4.gridheight = 1;
		c4.weightx = 0; 
		c4.weighty = 1; 
		c4.insets = new Insets(2,2,2,2);
		this._mainPanel.add(this._notesAndButtonsPanel, c4);
		
		GridBagConstraints c5 = new GridBagConstraints();
		c5.fill = GridBagConstraints.BOTH;
		c5.gridx = 2;
		c5.gridy = 2;
		c5.gridwidth = 1;
		c5.gridheight = 1;
		c5.weightx = 0.5; 
		c5.weighty = 0; 
		c5.insets = new Insets(2,2,2,2);
		this._mainPanel.add(this._totalsPanel, c5);
		
		this.add(this._mainPanel);
	}//ctor
	
	class ToPanel extends JPanel
	{
		public ToPanel()
		{
			this.setBackground(Color.RED);
		}
	}
	
	class DetailsPanel extends JPanel
	{
		public DetailsPanel()
		{
			this.setBackground(Color.GREEN);
		}
	}
	
	class ItemsTablePanel extends JPanel
	{
		public ItemsTablePanel()
		{
			this.setBackground(Color.BLUE);
		}
	}
	
	class TotalsPanel extends JPanel
	{
		public TotalsPanel()
		{
			this.setBackground(Color.YELLOW);
		}
	}
	
	class NotesAndButtonsPanel extends JPanel
	{
		public NotesAndButtonsPanel()
		{
			this.setBackground(Color.ORANGE);
		}
	}
	
	private String buildAttributionText(String[] attributions)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Thanks to: \n");
		for (final String a : attributions)
		{
			builder.append(a + "\n");
		}
		
		return builder.toString();
	}//buildAttributionText
}//class
