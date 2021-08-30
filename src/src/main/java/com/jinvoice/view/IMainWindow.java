package com.jinvoice.view;

import java.util.ArrayList;
import java.util.Date;

import com.jinvoice.models.*;
import com.jinvoice.presenter.IViewListener;

public interface IMainWindow
{
	public void addViewListener(IViewListener listener);
	
	public String getFromText();
	public void setFromText(String from);
	
	public String getBillTo();
	public void setBillTo(String billTo);
	
	public String getShipTo();
	public void setShipTo(String shipTo);
	
	public String getInvoiceTitle();
	public void setInvoiceTitle(String title);
	
	public int getNumber();
	public void setNumber(int number);
	
	public Date getDate();
	public void setDate(Date date);
	
	public String getPaymentTerms();
	public void setPaymentTerms(String paymentTerms);
	
	public Date getDueDate();
	public void setDueDate(Date date);
	
	public ArrayList<InvoiceItem> getItems();
	public ArrayList<InvoiceItem> getSelectedItems();
	public void addItem(InvoiceItem item);
	public void removeItem(InvoiceItem item);
	
	public boolean getAddItemButtonEnabled();
	public void setAddItemButtonEnabled(boolean enabled);
	
	public ArrayList<InvoiceItem> showAddItemDialog();
	
	public boolean getRemoveSelectedItemButtonEnabled();
	public void setRemoveSelectedItemButtonEnabled(boolean enabled);
	
	public String getNotes();
	public void setNotes(String notes);
	
	public double getSubtotal();
	public void setSubtotal(double subtotal);
	
	public int getTaxPercent();
	public void setTaxPercent(int percent);
	
	public int getDiscountPercent();
	public void setDiscountPercent(int percent);
	
	public String getEnteredShipping();
	public void setShipping(double shipping);
	
	public double getTotal();
	public void setTotal(double total);
	
	public boolean getCreateButtonEnabled();
	public void setCreateButtonEnabled(boolean enabled);
	
	public boolean getCancelButtonEnabled();
	public void setCancelButtonEnabled(boolean enabled);
	
	public String showSaveFileDialog();
	
	public void showInfoDialog(String title, String message);
	public void showErrorDialog(String title, String message);
}
