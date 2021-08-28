package com.jinvoice.models;

import java.util.ArrayList;
import java.util.Date;

public class Invoice
{
	private String _from;
	private String _shipTo;
	private String _billTo;
	private String _title;
	private int _number;
	private Date _date;
	private String _paymentTerms;
	private Date _dueDate;
	private String _notes;
	private double _tax;
	private double _discount;
	private double _shipping;
	private ArrayList<InvoiceItem> _items = new ArrayList<InvoiceItem>();
	
	public Invoice()
	{
		
	}
	
	public double getTotal()
	{
		double total = 0;
		for (final InvoiceItem item : this._items)
		{
			total += item.getPrice();
		}
		
		return total;
	}
	
	public boolean containsItem(InvoiceItem item)
	{
		return this._items.contains(item);
	}
	
	public ArrayList<InvoiceItem> getItems()
	{
		return this._items;
	}
	
	public void addItem(InvoiceItem item)
	{
		this._items.add(item);
	}
	
	public boolean removeItem(InvoiceItem item)
	{
		if (!this._items.contains(item))
		{
			return false;
		}
		else 
		{
			this._items.remove(item);
			return true;
		}
	}

	public String getFrom()
	{
		return _from;
	}

	public void setFrom(String from)
	{
		this._from = from;
	}

	public String getShipTo()
	{
		return _shipTo;
	}

	public void setShipTo(String shipTo) 
	{
		this._shipTo = shipTo;
	}

	public String getBillTo() 
	{
		return _billTo;
	}

	public void setBillTo(String billTo) 
	{
		this._billTo = billTo;
	}

	public String getTitle()
	{
		return _title;
	}

	public void setTitle(String title) 
	{
		this._title = title;
	}

	public int getNumber() 
	{
		return _number;
	}

	public void setNumber(int number) 
	{
		this._number = number;
	}

	public Date getDate() 
	{
		return _date;
	}

	public void setDate(Date date) 
	{
		this._date = date;
	}

	public String getPaymentTerms() 
	{
		return _paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) 
	{
		this._paymentTerms = paymentTerms;
	}

	public Date getDueDate() 
	{
		return _dueDate;
	}

	public void setDueDate(Date dueDate) 
	{
		this._dueDate = dueDate;
	}

	public String getNotes() 
	{
		return _notes;
	}

	public void setNotes(String notes) 
	{
		this._notes = notes;
	}

	public double getTax() 
	{
		return _tax;
	}

	public void setTax(double tax) 
	{
		this._tax = tax;
	}

	public double getDiscount() 
	{
		return _discount;
	}

	public void setDiscount(double discount) 
	{
		this._discount = discount;
	}

	public double getShipping() 
	{
		return _shipping;
	}

	public void setShipping(double _shipping) 
	{
		this._shipping = _shipping;
	}//setShipping
}//class
