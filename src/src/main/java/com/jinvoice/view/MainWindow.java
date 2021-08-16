package com.jinvoice.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
		c2.weightx = 0; 
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
		private final JLabel _titleLbl = new JLabel("Title");
		private final JTextField _titleField = new JTextField();
		
		private final JLabel _numberLbl = new JLabel("Number");
		private final JTextField _numberField = new JTextField();
		
		private final JLabel _dateLbl = new JLabel("Date");
		private final JTextField _dateField = new JTextField();
		
		private final JLabel _paymentTermsLbl = new JLabel("Payment Terms");
		private final JTextField _paymentTermsField = new JTextField();
		
		private final JLabel _dueDateLbl = new JLabel("Due Date");
		private final JTextField _dueDateField = new JTextField();
		
		public DetailsPanel()
		{
			this.setLayout(new GridBagLayout());
			
			addComponent(this._titleLbl, 0, 0);
			addComponent(this._titleField, 1, 0);
			
			addComponent(this._numberLbl, 0, 1);
			addComponent(this._numberField, 1, 1);
			
			addComponent(this._dateLbl, 0, 2);
			addComponent(this._dateField, 1, 2);
			
			addComponent(this._paymentTermsLbl, 0, 3);
			addComponent(this._paymentTermsField, 1, 3);
			
			addComponent(this._dueDateLbl, 0, 4);
			addComponent(this._dueDateField, 1, 4);
		}
		
		private void addComponent(Component component,
				int gridx, int gridy)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2,2,2,2);
			c.gridx = gridx;
			c.gridy = gridy;
			c.anchor = GridBagConstraints.CENTER;
			this.add(component, c);
		}
	}
	
	class ItemsTablePanel extends JPanel
	{
		private final JTable _table = new JTable();
		private final JScrollPane _scrollPane;
		
		private final String[] TABLE_HEADERS = new String[]
				{
						"Description", 
						"Price",
						"Quantity",
						"Amount"
				};
		
		public ItemsTablePanel()
		{
			this.setLayout(new GridLayout());
			DefaultTableModel tableModel = new DefaultTableModel()
			{
				@Override
				public boolean isCellEditable(int i, int i1)
				{
					return false;
				}
			};
			tableModel.addColumn(TABLE_HEADERS[0]);
			tableModel.addColumn(TABLE_HEADERS[1]);
			tableModel.addColumn(TABLE_HEADERS[2]);
			tableModel.addColumn(TABLE_HEADERS[3]);
			this._table.setModel(tableModel);
			this._table.setFillsViewportHeight(true);
			this._scrollPane = new JScrollPane(this._table);
			this._scrollPane.setViewportView(this._table);
			this._scrollPane.setBorder(BorderFactory.createTitledBorder("Items"));
			this.add(this._scrollPane);
		}
	}
	
	class TotalsPanel extends JPanel
	{
		private final JLabel _subtotalLbl = new JLabel("Subtotal");
		private final JTextField _subtotalField = new JTextField();
		
		private final JLabel _taxLbl = new JLabel("Tax (%)");
		private final JTextField _taxField = new JTextField();
		
		private final JLabel _discountLbl = new JLabel("Discount (%)");
		private final JTextField _discountField = new JTextField();
		
		private final JLabel _shippingLbl = new JLabel("Shipping");
		private final JTextField _shippingField = new JTextField();
		
		private final JLabel _totalLbl = new JLabel("Total");
		private final JTextField _totalField = new JTextField();
		
		public TotalsPanel()
		{
			this.setLayout(new GridBagLayout());
			
			addComponent(this._subtotalLbl, 0, 0);
			this._subtotalField.setEditable(false);
			addComponent(this._subtotalField, 1, 0);
			
			addComponent(this._taxLbl, 0, 1);
			addComponent(this._taxField, 1, 1);
			
			addComponent(this._discountLbl, 0, 2);
			addComponent(this._discountField, 1, 2);
			
			addComponent(this._shippingLbl, 0, 3);
			addComponent(this._shippingField, 1, 3);
			
			addComponent(this._totalLbl, 0, 4);
			this._totalField.setEditable(false);
			addComponent(this._totalField, 1, 4);
		}
		
		private void addComponent(Component component,
				int gridx, int gridy)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2,2,2,2);
			c.gridx = gridx;
			c.gridy = gridy;
			c.anchor = GridBagConstraints.CENTER;
			this.add(component, c);
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
