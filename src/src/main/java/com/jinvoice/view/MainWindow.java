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

	private final NorthPanel _northPanel = new NorthPanel();
	
	private final ItemsTablePanel _itemsTablePanel = new ItemsTablePanel();

	private final SouthPanel _southPanel = new SouthPanel(this._itemsTablePanel.getButtonSize());
	
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

		this._mainPanel.setLayout(new BorderLayout());
		
		// add components
		this._mainPanel.add(this._northPanel, BorderLayout.NORTH);
		this._mainPanel.add(this._itemsTablePanel, BorderLayout.CENTER);
		this._mainPanel.add(this._southPanel, BorderLayout.SOUTH);

		this.add(this._mainPanel);
	}//ctor
	
	class NorthPanel extends JPanel
	{
		private final ToPanel _toPanel = new ToPanel();
		private final DetailsPanel _detailsPanel = new DetailsPanel();
		
		public NorthPanel()
		{
			this.setLayout(new GridBagLayout());
			
			GridBagConstraints c1 = new GridBagConstraints();
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 0;
			c1.gridy = 0;
			c1.gridwidth = 2;
			c1.gridheight = 1;
			c1.weightx = 1; 
			c1.weighty = 1; 
			c1.insets = new Insets(2,2,2,2);
			this.add(this._toPanel, c1);
			
			GridBagConstraints c2 = new GridBagConstraints();
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 2;
			c2.gridy = 0;
			c2.gridwidth = 2;
			c2.gridheight = 1;
			c2.weightx = 0.5; 
			c2.weighty = 0; 
			c2.insets = new Insets(2,2,2,2);
			this.add(this._detailsPanel, c2);
		}
	}
	
	class SouthPanel extends JPanel
	{
		private final TotalsPanel _totalsPanel = new TotalsPanel();
		private final NotesAndButtonsPanel _notesAndButtonsPanel;
		
		public SouthPanel(Dimension btnDim)
		{
			this.setLayout(new GridBagLayout());
			
			this._notesAndButtonsPanel = new NotesAndButtonsPanel(btnDim);
			
			GridBagConstraints c1 = new GridBagConstraints();
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 0;
			c1.gridy = 0;
			c1.gridwidth = 2;
			c1.gridheight = 1;
			c1.weightx = 1; 
			c1.weighty = 1; 
			c1.insets = new Insets(2,2,2,2);
			this.add(this._notesAndButtonsPanel, c1);
			
			GridBagConstraints c2 = new GridBagConstraints();
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 2;
			c2.gridy = 0;
			c2.gridwidth = 2;
			c2.gridheight = 1;
			c2.weightx = 0.5; 
			c2.weighty = 0; 
			c2.insets = new Insets(2,2,2,2);
			this.add(this._totalsPanel, c2);
		}
	}
	
	class ToPanel extends JPanel
	{
		private JTextField _fromField = new JTextField();
		private JTextField _billToField = new JTextField();
		private JTextField _shipToField = new JTextField();
		
		public ToPanel()
		{
			this.setLayout(new GridBagLayout());
			
			this._fromField.setBorder(BorderFactory.createTitledBorder("From"));
			GridBagConstraints c1 = new GridBagConstraints();
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 0;
			c1.gridy = 0;
			c1.gridwidth = 2;
			c1.gridheight = 1;
			c1.weightx = 1; 
			c1.weighty = 1; 
			c1.insets = new Insets(2,2,2,2);
			this.add(this._fromField, c1);
			
			this._billToField.setBorder(BorderFactory.createTitledBorder("Bill To"));
			GridBagConstraints c2 = new GridBagConstraints();
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 0;
			c2.gridy = 1;
			c2.gridwidth = 1;
			c2.gridheight = 1;
			c2.weightx = 1; 
			c2.weighty = 1; 
			c2.insets = new Insets(2,2,2,2);
			this.add(this._billToField, c2);
			
			this._shipToField.setBorder(BorderFactory.createTitledBorder("Ship To (optional)"));
			GridBagConstraints c3 = new GridBagConstraints();
			c3.fill = GridBagConstraints.BOTH;
			c3.gridx = 1;
			c3.gridy = 1;
			c3.gridwidth = 1;
			c3.gridheight = 1;
			c3.weightx = 1; 
			c3.weighty = 1; 
			c3.insets = new Insets(2,2,2,2);
			this.add(this._shipToField, c3);
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
			/*
			c.weightx = 1;
			c.weighty = 1;
			*/
			c.weightx = 0.5;
			c.weighty = 0;
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
		
		private final ButtonsPanel _btnsPanel = new ButtonsPanel();
		
		public ItemsTablePanel()
		{
			this.setLayout(new BorderLayout());
			
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
			this.add(this._scrollPane, BorderLayout.CENTER);
			
			this.add(this._btnsPanel, BorderLayout.SOUTH);
		}
		
		public Dimension getButtonSize()
		{
			return this._btnsPanel.getButtonSize();
		}
		
		class ButtonsPanel extends JPanel
		{
			private final JButton _addBtn = new JButton("Add");
			private final JButton _removeBtn = new JButton("Remove Selected");
			
			public ButtonsPanel()
			{
				this.setLayout(new FlowLayout());
				
				this._addBtn.setPreferredSize(getButtonSize());
				
				this.add(this._addBtn);
				this.add(this._removeBtn);
			}
			
			public Dimension getButtonSize()
			{
				return this._removeBtn.getPreferredSize();
			}
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
			
			Font totalFont = new Font("SansSerif", Font.BOLD, 20);
			
			addComponent(this._subtotalLbl, 0, 0);
			this._subtotalField.setPreferredSize(new Dimension(100,50));
			this._subtotalField.setEditable(false);
			this._subtotalField.setFont(totalFont);
			addComponent(this._subtotalField, 1, 0);
			
			addComponent(this._taxLbl, 0, 1);
			addComponent(this._taxField, 1, 1);
			
			addComponent(this._discountLbl, 0, 2);
			addComponent(this._discountField, 1, 2);
			
			addComponent(this._shippingLbl, 0, 3);
			addComponent(this._shippingField, 1, 3);
			
			addComponent(this._totalLbl, 0, 4);
			this._totalField.setPreferredSize(new Dimension(100,50));
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
		private final JTextField _notesField = new JTextField();
		private final ButtonsPanel _buttonsPanel;
		
		public NotesAndButtonsPanel(Dimension btnDim)
		{
			this.setLayout(new BorderLayout());
			
			this._buttonsPanel = new ButtonsPanel(btnDim);
			
			this._notesField.setPreferredSize(new Dimension(100, 100));
			this._notesField.setBorder(BorderFactory.createTitledBorder("Notes (optional)"));
			this.add(this._notesField, BorderLayout.CENTER);
			
			this.add(this._buttonsPanel, BorderLayout.SOUTH);
		}
		
		class ButtonsPanel extends JPanel
		{
			private final JButton _createButton = new JButton("Create");
			private final JButton _cancelButton = new JButton("Cancel");
			
			public ButtonsPanel(Dimension btnDim)
			{
				this.setLayout(new FlowLayout());
				
				this._createButton.setPreferredSize(btnDim);
				this.add(this._createButton);
				this._cancelButton.setPreferredSize(btnDim);
				this.add(this._cancelButton);
			}
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
