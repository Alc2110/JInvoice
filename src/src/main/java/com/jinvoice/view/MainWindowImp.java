package com.jinvoice.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;

import com.jinvoice.models.InvoiceItem;
import com.jinvoice.presenter.*;

/*
 * Application main window.
 */
public class MainWindowImp extends JFrame implements IMainWindow
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
	
	private final ArrayList<IViewListener> _listeners = new ArrayList<IViewListener>();
	
	/*
	 * Constructor.
	 */
	public MainWindowImp(String title, String version, int windowWidth, int windowHeight, String[] attributions)
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
		
		// add subpanels
		this._mainPanel.add(this._northPanel, BorderLayout.NORTH);
		this._mainPanel.add(this._itemsTablePanel, BorderLayout.CENTER);
		this._mainPanel.add(this._southPanel, BorderLayout.SOUTH);
		
		// register events
		// buttons
		this._itemsTablePanel._btnsPanel._addBtn.addActionListener((event) -> {
			for (final IViewListener listener : this._listeners) {
				listener.onAddItemButtonClicked();
			}
		});
		this._itemsTablePanel._btnsPanel._removeBtn.addActionListener((event) -> {
			for (final IViewListener listener : this._listeners) {
				listener.onRemoveSelectedItemButtonClicked();
			}
		});
		this._southPanel._notesAndButtonsPanel._buttonsPanel._createButton.addActionListener((event) -> {
			for (final IViewListener listener: this._listeners) {
				listener.onCreateButtonClicked();
			}
		});
		this._southPanel._notesAndButtonsPanel._buttonsPanel._cancelButton.addActionListener((event) -> {
			for (final IViewListener listener : this._listeners) {
				listener.onCancelButtonClicked();
			}
		});
		// remaining components
		this._itemsTablePanel._table.getSelectionModel().addListSelectionListener((event) -> {
			for (final IViewListener listener: this._listeners) {
				if (!event.getValueIsAdjusting())
					listener.onItemsSelected();
			}
		});
		this._northPanel._toPanel._billToField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._northPanel._toPanel._fromField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._northPanel._toPanel._shipToField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._northPanel._detailsPanel._titleField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._northPanel._detailsPanel._numberField.addChangeListener(new SpinnerListener(this._listeners));
		this._northPanel._detailsPanel._paymentTermsField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._northPanel._detailsPanel._datePicker.getModel().addChangeListener(new DateChangeListener(this._listeners));
		this._northPanel._detailsPanel._dueDatePicker.getModel().addChangeListener(new DateChangeListener(this._listeners));
		this._southPanel._notesAndButtonsPanel._notesField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._southPanel._totalsPanel._shippingField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		//this._southPanel._totalsPanel._subtotalField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		//this._southPanel._totalsPanel._totalField.getDocument().addDocumentListener(new InputFieldListener(this._listeners));
		this._southPanel._totalsPanel._taxField.addChangeListener(new SpinnerListener(this._listeners));
		this._southPanel._totalsPanel._discountField.addChangeListener(new SpinnerListener(this._listeners));
		
		this.add(this._mainPanel);
		
		this.setVisible(true);
	}//ctor

	public void addViewListener(IViewListener listener)
	{
		this._listeners.add(listener);
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

	@Override
	public String getFromText()
	{
		return this._northPanel._toPanel._fromField.getText();
	}

	@Override
	public void setFromText(String from)
	{
		this._northPanel._toPanel._fromField.setText(from);
	}

	@Override
	public String getBillTo()
	{
		return this._northPanel._toPanel._billToField.getText();
	}

	@Override
	public void setBillTo(String billTo)
	{
		this._northPanel._toPanel._billToField.setText(billTo);
	}

	@Override
	public String getShipTo()
	{
		return this._northPanel._toPanel._shipToField.getText();
	}

	@Override
	public void setShipTo(String shipTo)
	{
		this._northPanel._toPanel._shipToField.setText(shipTo);
	}
	
	@Override
	public void setInvoiceTitle(String title)
	{
		this._northPanel._detailsPanel._titleField.setText(title);
	}
	
	@Override
	public String getInvoiceTitle()
	{
		return this._northPanel._detailsPanel._titleField.getText();
	}

	@Override
	public int getNumber()
	{
		return commitSpinnerValue(this._northPanel._detailsPanel._numberField);
	}

	@Override
	public void setNumber(int number)
	{
		this._northPanel._detailsPanel._numberField.getModel().setValue(number);
	}

	@Override
	public Date getDate()
	{
		return (Date)this._northPanel._detailsPanel._datePicker.getModel().getValue();
	}

	@Override
	public void setDate(Date date)
	{
		this._northPanel._detailsPanel._datePicker.getModel().setDate(date.getYear(), date.getMonth(), date.getDay());
	}

	@Override
	public String getPaymentTerms()
	{
		return this._northPanel._detailsPanel._paymentTermsField.getText();
	}

	@Override
	public void setPaymentTerms(String paymentTerms)
	{
		this._northPanel._detailsPanel._paymentTermsField.setText(paymentTerms);
	}

	@Override
	public Date getDueDate()
	{
		return (Date)this._northPanel._detailsPanel._dueDatePicker.getModel().getValue();
	}

	@Override
	public void setDueDate(Date date)
	{
		this._northPanel._detailsPanel._dueDatePicker.getModel().setDate(date.getYear(), date.getMonth(), date.getDay());
	}

	@Override
	public ArrayList<InvoiceItem> getItems()
	{
		ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
		int rows = this._itemsTablePanel._table.getRowCount();
		for (int row = 0; row < rows; row++)
		{
			for (int i = 1; i <= (int)(this._itemsTablePanel._table.getModel().getValueAt(row, 2)); i++)
			{
				items.add(new InvoiceItem(
						(String)this._itemsTablePanel._table.getModel().getValueAt(row, 0),
						(double)this._itemsTablePanel._table.getModel().getValueAt(row, 1)
						));
			}
		}
		
		return items;
	}

	@Override
	public ArrayList<InvoiceItem> getSelectedItems()
	{
		ArrayList<InvoiceItem> selectedItems = new ArrayList<InvoiceItem>();
		int[] rows = this._itemsTablePanel._table.getSelectedRows();
		for (final int row : rows)
		{
			selectedItems.add(new InvoiceItem(
					(String)this._itemsTablePanel._table.getModel().getValueAt(row, 0),
					(double)this._itemsTablePanel._table.getModel().getValueAt(row, 1)
					));
		}
		
		return selectedItems;
	}

	@Override
	public void addItem(InvoiceItem item)
	{
		ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
		int rows = this._itemsTablePanel._table.getRowCount();
		for (int row = 0; row < rows; row++)
		{
			InvoiceItem currentItem = new InvoiceItem(
					(String)this._itemsTablePanel._table.getModel().getValueAt(row, 0),
					(double)this._itemsTablePanel._table.getModel().getValueAt(row, 1)
					);
			// TODO: figure out why equals() does not work
			if (currentItem.getDescription().equals(item.getDescription()) && 
				currentItem.getPrice() == item.getPrice())
			{
				// item already exists, increment the count
				int currQuantity = (int)this._itemsTablePanel._table.getModel().getValueAt(row, 2);
				this._itemsTablePanel._table.getModel().setValueAt(currQuantity+1, row, 2);
				this._itemsTablePanel._table.getModel().setValueAt(item.getPrice()*(currQuantity+1), row, 3);
				// fire update event
				for (final IViewListener listener : this._listeners)
				{
					listener.onInputFieldUpdated();
				}
				// nothing more to do
				return;
			}
		}
		// item does not yet exist, just add it
		DefaultTableModel tableModel = (DefaultTableModel)this._itemsTablePanel._table.getModel();
		tableModel.addRow(new Object[] {item.getDescription(), item.getPrice(), 1, item.getPrice()});
		
		// fire update event
		for (final IViewListener listener : this._listeners)
		{
			listener.onInputFieldUpdated();
		}
	}

	@Override
	public void removeItem(InvoiceItem item)
	{
		int rows = this._itemsTablePanel._table.getRowCount();
		for (int row = 0; row < rows; row++)
		{
			InvoiceItem currentItem = new InvoiceItem(
					(String)this._itemsTablePanel._table.getModel().getValueAt(row, 0),
					(double)this._itemsTablePanel._table.getModel().getValueAt(row, 1)
					);
			// TODO: figure out why equals() does not work
			if (currentItem.getDescription().equals(item.getDescription()) && 
					currentItem.getPrice() == item.getPrice())
			{
				// remove this item
				DefaultTableModel model = (DefaultTableModel)this._itemsTablePanel._table.getModel();
				model.removeRow(row);
			}
		}
		
		// fire update event
		for (final IViewListener listener : this._listeners)
		{
			listener.onInputFieldUpdated();
		}
	}

	@Override
	public boolean getAddItemButtonEnabled()
	{
		return this._itemsTablePanel._btnsPanel._addBtn.isEnabled();
	}

	@Override
	public void setAddItemButtonEnabled(boolean enabled)
	{
		this._itemsTablePanel._btnsPanel._addBtn.setEnabled(enabled);
	}
	
	@Override
	public ArrayList<InvoiceItem> showAddItemDialog()
	{
		JTextField descriptionField = new JTextField();
		JTextField priceField = new JTextField();
		JSpinner quantityField = new JSpinner();
		final JComponent[] inputs = new JComponent[] {new JLabel("Description"), descriptionField, new JLabel("Price"), priceField, new JLabel("Quantity"), quantityField};
		int dialogResult = JOptionPane.showConfirmDialog(this, inputs, "Add Item", JOptionPane.PLAIN_MESSAGE);
		if (dialogResult == JOptionPane.OK_OPTION)
		{
			String description = descriptionField.getText();
			if (description == null ||
				description.trim().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Please enter a description.", "Error", JOptionPane.ERROR_MESSAGE);
				
				return null;
			}

			double price;
			try
			{
				price = Double.parseDouble(priceField.getText());
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
				
				return null;
			}
			
			ArrayList<InvoiceItem> items = new ArrayList<InvoiceItem>();
			for (int i = 1; i <= (int)quantityField.getModel().getValue(); i++)
			{
				items.add(new InvoiceItem(descriptionField.getText(), price));
			}
			
			return items;
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean getRemoveSelectedItemButtonEnabled()
	{
		return this._itemsTablePanel._btnsPanel._removeBtn.isEnabled();
	}

	@Override
	public void setRemoveSelectedItemButtonEnabled(boolean enabled)
	{
		this._itemsTablePanel._btnsPanel._removeBtn.setEnabled(enabled);
	}

	@Override
	public String getNotes()
	{
		return this._southPanel._notesAndButtonsPanel._notesField.getText();
	}

	@Override
	public void setNotes(String notes)
	{
		this._southPanel._notesAndButtonsPanel._notesField.setText(notes);
	}

	@Override
	public double getSubtotal()
	{
		try
		{
			return Double.parseDouble(this._southPanel._totalsPanel._subtotalField.getText());
		}
		catch (Exception e)
		{
			// this shouldn't happen
			setSubtotal(0);
			return 0;
		}
	}

	@Override
	public void setSubtotal(double subtotal)
	{
		// update the UI on the EDT
		Runnable doUpdate = () -> {
			DecimalFormat decFormat = new DecimalFormat("#.00");
			String formattedSubtotal = decFormat.format(subtotal);
			this._southPanel._totalsPanel._subtotalField.setText(formattedSubtotal);
		};
		SwingUtilities.invokeLater(doUpdate);
	}

	@Override
	public int getTaxPercent()
	{
		return commitSpinnerValue(this._southPanel._totalsPanel._taxField);
	}

	@Override
	public void setTaxPercent(int percent)
	{
		this._southPanel._totalsPanel._taxField.setValue(percent);
	}

	@Override
	public int getDiscountPercent()
	{
		return commitSpinnerValue(this._southPanel._totalsPanel._discountField);
	}
	
	private int commitSpinnerValue(JSpinner spinner)
	{
		// commit the value
		// ensures manually-typed values are reflected in the model
		try
		{
			spinner.commitEdit();
		}
		catch (java.text.ParseException e)
		{
			return 0;
		}
		
		return (Integer)spinner.getValue();
	}

	@Override
	public void setDiscountPercent(int percent)
	{
		this._southPanel._totalsPanel._discountField.setValue(percent);
	}

	@Override
	public String getEnteredShipping()
	{
		return this._southPanel._totalsPanel._shippingField.getText();
	}

	@Override
	public void setShipping(double shipping)
	{
		this._southPanel._totalsPanel._shippingField.setText(String.valueOf(shipping));
	}

	@Override
	public double getTotal()
	{
		try
		{
			return Double.parseDouble(this._southPanel._totalsPanel._totalField.getText());
		}
		catch (Exception e)
		{
			this.setTotal(0);
			return 0;
		}
	}

	@Override
	public void setTotal(double total)
	{
		// update the UI on the EDT
		Runnable doUpdate = () -> {
			DecimalFormat decFormat = new DecimalFormat("#.00");
			String formattedTotal = decFormat.format(total);
			this._southPanel._totalsPanel._totalField.setText(formattedTotal);
		};
		SwingUtilities.invokeLater(doUpdate);
	}

	@Override
	public boolean getCreateButtonEnabled()
	{
		return this._southPanel._notesAndButtonsPanel._buttonsPanel._createButton.isEnabled();
	}

	@Override
	public void setCreateButtonEnabled(boolean enabled) 
	{
		this._southPanel._notesAndButtonsPanel._buttonsPanel._createButton.setEnabled(enabled);
	}

	@Override
	public boolean getCancelButtonEnabled()
	{
		return this._southPanel._notesAndButtonsPanel._buttonsPanel._cancelButton.isEnabled();
	}

	@Override
	public void setCancelButtonEnabled(boolean enabled)
	{
		this._southPanel._notesAndButtonsPanel._buttonsPanel._cancelButton.setEnabled(enabled);
	}
	
	@Override
	public String showSaveFileDialog()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save invoice file");
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Excel spreadsheet", "xlsx");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx"))
			{
				// user has already provided extension
				return file.getAbsolutePath();
			}
			else
			{
				return file.getAbsolutePath() + ".xlsx";
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void showInfoDialog(String title, String message)
	{
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void showErrorDialog(String title, String message)
	{
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
}//class