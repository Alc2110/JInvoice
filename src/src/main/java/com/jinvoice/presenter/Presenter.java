package com.jinvoice.presenter;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jinvoice.models.InvoiceItem;
import com.jinvoice.view.*;

/*
 * Contains most of the logic that interacts with the UI and models.
 */
public class Presenter implements IViewListener
{
	private final IMainWindow _view;
	
	public Presenter(IMainWindow view)
	{
		this._view = view;
		this._view.addViewListener(this);
		
		// disable some components initially
		this._view.setCreateButtonEnabled(false);
		this._view.setCancelButtonEnabled(false);
		this._view.setRemoveSelectedItemButtonEnabled(false);
	}
	
	public void onAddItemButtonClicked()
	{
		ArrayList<InvoiceItem> items = this._view.showAddItemDialog();
		if (items != null)
		{
			for (final InvoiceItem item : items)
			{
				this._view.addItem(item);
			}
		}
	}

	public void onRemoveSelectedItemButtonClicked()
	{
		ArrayList<InvoiceItem> selectedItems = this._view.getSelectedItems();
		for (final InvoiceItem item : selectedItems)
		{
			this._view.removeItem(item);
		}
	}
	
	public void onItemsSelected()
	{
		this._view.setRemoveSelectedItemButtonEnabled(this._view.getSelectedItems().size() > 0);
	}
	
	public void onCreateButtonClicked()
	{
		// TODO

	}
	
	public void onCancelButtonClicked()
	{
		// clear all fields
		this._view.setFromText("");
		this._view.setBillTo("");
		this._view.setShipTo("");
		this._view.setInvoiceTitle("");
		this._view.setPaymentTerms("");
		this._view.setNotes("");
	}
	
	public void onInputFieldUpdated()
	{
		boolean inputsComplete = true;
		
		inputsComplete = inputsComplete && !isEmptyString(this._view.getFromText());
		inputsComplete = inputsComplete && !isEmptyString(this._view.getBillTo());
		
		inputsComplete = inputsComplete && !isEmptyString(this._view.getInvoiceTitle());
		inputsComplete = inputsComplete && !isEmptyString(this._view.getPaymentTerms());
		if (!(this._view.getDate() == null) && !(this._view.getDueDate() == null))
		{
			inputsComplete = inputsComplete && !this._view.getDueDate().before(this._view.getDate());
		}
		else
		{
			inputsComplete = inputsComplete && false;
		}
			
		inputsComplete = inputsComplete && (this._view.getItems().size() > 0);
		
		try
		{
			double shipping =  Double.parseDouble(this._view.getEnteredShipping());
			inputsComplete = inputsComplete && true;
		}
		catch (NumberFormatException ex)
		{
			inputsComplete = inputsComplete && false;
		}
		
		this._view.setCreateButtonEnabled(inputsComplete);
		this._view.setCancelButtonEnabled(inputsComplete);
	}//onInputFieldUpdated
	
	private boolean isEmptyString(String text)
	{
		if (text == null)
			return true;
		
		if (text.trim().isEmpty() || text.equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}//class
