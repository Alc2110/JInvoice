package com.jinvoice.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.util.Date;
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
			this.setLayout(new BorderLayout());
			
			this._notesAndButtonsPanel = new NotesAndButtonsPanel(btnDim);

			this.add(this._notesAndButtonsPanel, BorderLayout.CENTER);
			this._totalsPanel.setPreferredSize(new Dimension(225,this._totalsPanel.getHeight()));
			this.add(this._totalsPanel, BorderLayout.EAST);
		}
	}
	
	class ToPanel extends JPanel
	{
		private JTextArea _fromField = new JTextArea();
		private JScrollPane _fromFieldPane = new JScrollPane(this._fromField);
		
		private JTextArea _billToField = new JTextArea();
		private JScrollPane _billToFieldPane = new JScrollPane(this._billToField);
		
		private JTextArea _shipToField = new JTextArea();
		private JScrollPane _shipToFieldPane = new JScrollPane(this._shipToField);
		
		private final Font _toFont = new Font("SansSerif", Font.PLAIN, 9);
		
		public ToPanel()
		{
			this.setLayout(new GridBagLayout());
			
			this._fromField.setFont(this._toFont);
			this._fromField.setLineWrap(true);
			this._fromFieldPane.setBorder(BorderFactory.createTitledBorder("From"));
			addTextAreaPane(this._fromFieldPane, 0, 0, 2, 1, 1, 1);
			
			this._billToField.setFont(this._toFont);
			this._billToField.setLineWrap(true);
			this._billToFieldPane.setBorder(BorderFactory.createTitledBorder("Bill To"));
			addTextAreaPane(this._billToFieldPane, 0, 1, 1, 1, 1, 1);
			
			this._shipToField.setFont(this._toFont);
			this._shipToField.setLineWrap(true);
			this._shipToFieldPane.setBorder(BorderFactory.createTitledBorder("Ship To (optional)"));
			addTextAreaPane(this._shipToFieldPane, 1, 1, 1, 1, 1, 1);
		}
		
		private void addTextAreaPane(JScrollPane scrollPane,
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
			this.add(scrollPane, gbc);
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
		private final JDatePickerImpl _dueDatePicker;
		
		private final Font _detailsFont = new Font("SansSerif", Font.PLAIN, 9);
		
		public DetailsPanel()
		{
			this.setLayout(new GridBagLayout());
			
			addComponent(this._titleLbl, 
					0, 0,
					1, 1,
					0, 0);
			this._titleField.setFont(this._detailsFont);
			addComponent(this._titleField, 
					1, 0,
					1, 1,
					1, 0);
			
			addComponent(this._numberLbl, 
					0, 2,
					1, 1,
					0, 0);
			this._numberField.setFont(this._detailsFont);
			addComponent(this._numberField, 
					1, 2,
					1, 1,
					1, 0);
			
			addComponent(this._dateLbl, 
					0, 3,
					1, 1,
					0, 0);
			UtilDateModel dateModel = new UtilDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
			this._datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
			addComponent(this._datePicker, 
					1, 3,
					1, 1,
					1, 0);
			
			addComponent(this._paymentTermsLbl, 
					0, 4,
					1, 1,
					0, 0);
			this._paymentTermsField.setFont(this._detailsFont);
			addComponent(this._paymentTermsField, 
					1, 4,
					1, 1,
					1, 0);
			
			addComponent(this._dueDateLbl, 
					0, 5,
					1, 1,
					0, 0);
			UtilDateModel dueDateModel = new UtilDateModel();
			JDatePanelImpl dueDatePanel = new JDatePanelImpl(dueDateModel, p);
			this._dueDatePicker = new JDatePickerImpl(dueDatePanel, new DateComponentFormatter());
			addComponent(this._dueDatePicker, 
					1, 5,
					1, 1,
					1, 0);
		}
		
		private void addComponent(Component component,
				int gridx, int gridy,
				int gridwidth, int gridheight,
				double weightx, double weighty)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = weightx;
			c.weighty = weighty;
			c.gridwidth = gridwidth;
			c.gridheight = gridheight;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2,2,2,2);
			c.gridx = gridx;
			c.gridy = gridy;
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
		private final JLabel _subtotalLbl = new JLabel("Subtotal ($)");
		private final JTextField _subtotalField = new JTextField();
		
		private final JLabel _taxLbl = new JLabel("Tax (%)");
		private final JTextField _taxField = new JTextField();
		
		private final JLabel _discountLbl = new JLabel("Discount (%)");
		private final JTextField _discountField = new JTextField();
		
		private final JLabel _shippingLbl = new JLabel("Shipping ($)");
		private final JTextField _shippingField = new JTextField();
		
		private final JLabel _totalLbl = new JLabel("Total ($)");
		private final JTextField _totalField = new JTextField();
		
		public TotalsPanel()
		{
			this.setLayout(new GridBagLayout());
			
			Font totalFont = new Font("SansSerif", Font.BOLD, 14);

			addComponent(this._subtotalLbl,
					0, 0,
					1, 1,
					0, 0);
			this._subtotalField.setEditable(false);
			this._subtotalField.setFont(totalFont);
			addComponent(this._subtotalField,
					1, 0,
					1, 1,
					1, 0);
			
			addComponent(this._taxLbl,
					0, 1,
					1, 1,
					0, 0);
			addComponent(this._taxField,
					1, 1,
					1, 1,
					1, 0);
			
			addComponent(this._discountLbl,
					0, 2,
					1, 1,
					0.1, 0);
			addComponent(this._discountField,
					1, 2,
					1, 1,
					1, 0);
			
			addComponent(this._shippingLbl,
					0, 3,
					1, 1,
					0, 0);
			addComponent(this._shippingField,
					1, 3,
					1, 1,
					1, 0);
			
			addComponent(this._totalLbl,
					0, 4,
					1, 1,
					0, 0);
			this._totalField.setEditable(false);
			this._totalField.setFont(totalFont);
			addComponent(this._totalField,
					1, 4,
					1, 1,
					1, 0);
		}
		
		private void addComponent(Component component,
				int gridx, int gridy,
				int gridwidth, int gridheight,
				double weightx, double weighty)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(2,2,2,2);
			c.gridx = gridx;
			c.gridy = gridy;
			c.gridwidth = gridwidth;
			c.gridheight = gridheight;
			c.weightx = weightx;
			c.weighty = weighty;
			this.add(component, c);
		}
	}
	
	class NotesAndButtonsPanel extends JPanel
	{
		private final JTextArea _notesField = new JTextArea();
		private final JScrollPane _notesFieldPane = new JScrollPane(this._notesField);
		
		private final ButtonsPanel _buttonsPanel;
		
		public NotesAndButtonsPanel(Dimension btnDim)
		{
			this.setLayout(new BorderLayout());
			
			this._buttonsPanel = new ButtonsPanel(btnDim);
			
			Font notesFont = new Font("SansSerif", Font.PLAIN, 9);
			this._notesField.setFont(notesFont);
			this._notesField.setLineWrap(true);
			this._notesFieldPane.setPreferredSize(new Dimension(100, 100));
			this._notesFieldPane.setBorder(BorderFactory.createTitledBorder("Notes (optional)"));
			this.add(this._notesFieldPane, BorderLayout.CENTER);
			
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
