package com.jinvoice.presenter;

import java.util.ArrayList;

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
		
		this._view.setShipping(0);
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
		// validate inputs
		boolean inputsComplete = true;
		// from and to
		inputsComplete = inputsComplete && !isEmptyString(this._view.getFromText());
		inputsComplete = inputsComplete && !isEmptyString(this._view.getBillTo());
		// invoice details
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
		// invoice items	
		inputsComplete = inputsComplete && (this._view.getItems().size() > 0);
		// shipping
		double shipping = 0;
		try
		{
			shipping =  Double.parseDouble(this._view.getEnteredShipping());
			inputsComplete = inputsComplete && true;
		}
		catch (NumberFormatException ex)
		{
			inputsComplete = inputsComplete && false;
		}
		
		// set buttons' enabled statuses
		this._view.setCreateButtonEnabled(inputsComplete);
		this._view.setCancelButtonEnabled(inputsComplete);
		
		// show totals
		if (this._view.getItems().size() > 0)
		{
			double subtotal = 0;
			double total = 0;
			for (final InvoiceItem item : this._view.getItems())
			{
				subtotal += item.getPrice();
				total += item.getPrice();
			}
			this._view.setSubtotal(subtotal);
			this._view.setTotal(total + shipping + getPercent(total, this._view.getTaxPercent()) - getPercent(total, this._view.getDiscountPercent()));
		}
		else
		{
			this._view.setSubtotal(0);
			this._view.setTotal(0);
		}
	}//onInputFieldUpdated
	
	private double getPercent(double value, int percent)
	{
		return (percent/(double)100)*value;
	}
	
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
