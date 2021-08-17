package com.jinvoice.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.util.Properties;
import java.text.SimpleDateFormat;

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

			addPanel(this._toPanel, 0, 0, 2, 1, 1, 1);
			addPanel(this._detailsPanel, 2, 0, 2, 1, 0.5, 0);
		}
		
		private void addPanel(JPanel panel,
				int gridx, int gridy,
				int gridwidth, int gridheight,
				double weightx, double weighty)
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			gbc.gridwidth = gridwidth;
			gbc.gridheight = gridheight;
			gbc.weightx = weightx;
			gbc.weighty = weighty;
			gbc.insets = new Insets(2,2,2,2);
			this.add(panel, gbc);
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

			addPanel(this._notesAndButtonsPanel, 0, 0, 2, 1, 1, 1);
			addPanel(this._totalsPanel, 2, 0, 2, 1, 0.5, 0);
		}
		
		private void addPanel(JPanel panel,
				int gridx, int gridy,
				int gridwidth, int gridheight,
				double weightx, double weighty)
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			gbc.gridwidth = gridwidth;
			gbc.gridheight = gridheight;
			gbc.weightx = weightx;
			gbc.weighty = weighty;
			gbc.insets = new Insets(2,2,2,2);
			this.add(panel, gbc);
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
			addTextField(this._fromField, 0, 0, 2, 1, 1, 1);
			
			this._billToField.setBorder(BorderFactory.createTitledBorder("Bill To"));
			addTextField(this._billToField, 0, 1, 1, 1, 1, 1);
			
			this._shipToField.setBorder(BorderFactory.createTitledBorder("Ship To (optional)"));
			addTextField(this._shipToField, 1, 1, 1, 1, 1, 1);
		}
		
		private void addTextField(JTextField textField,
				int gridx, int gridy,
				int gridwidth, int gridheight,
				double weightx, double weighty)
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = gridx;
			gbc.gridy = gridy;
			gbc.gridwidth = gridwidth;
			gbc.gridheight = gridheight;
			gbc.weightx = weightx;
			gbc.weighty = weighty;
			gbc.insets = new Insets(2,2,2,2);
			this.add(textField, gbc);
		}
	}
	
	class DetailsPanel extends JPanel
	{
		private final JLabel _titleLbl = new JLabel("Title");
		private final JTextField _titleField = new JTextField();
		
		private final JLabel _numberLbl = new JLabel("Number");
		private final JTextField _numberField = new JTextField();
		
		private final JLabel _dateLbl = new JLabel("Date");
		private final JDatePickerImpl _datePicker;
		
		private final JLabel _paymentTermsLbl = new JLabel("Payment Terms");
		private final JTextField _paymentTermsField = new JTextField();
		
		private final JLabel _dueDateLbl = new JLabel("Due Date");
		//private final JTextField _dueDateField = new JTextField();
		private final JDatePickerImpl _dueDatePicker;
		
		public DetailsPanel()
		{
			this.setLayout(new GridBagLayout());
			
			addComponent(this._titleLbl, 0, 0);
			addComponent(this._titleField, 1, 0);
			
			addComponent(this._numberLbl, 0, 1);
			addComponent(this._numberField, 1, 1);
			
			addComponent(this._dateLbl, 0, 2);
			UtilDateModel dateModel = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
			this._datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
			addComponent(this._datePicker, 1, 2);
			
			addComponent(this._paymentTermsLbl, 0, 3);
			addComponent(this._paymentTermsField, 1, 3);
			
			addComponent(this._dueDateLbl, 0, 4);
			UtilDateModel dueDateModel = new UtilDateModel();
			JDatePanelImpl dueDatePanel = new JDatePanelImpl(dueDateModel, p);
			this._dueDatePicker = new JDatePickerImpl(dueDatePanel, new DateComponentFormatter());
			addComponent(this._dueDatePicker, 1, 4);
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
