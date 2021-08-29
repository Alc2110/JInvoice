package com.jinvoice.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/*
 * Represents a single invoice, with all items and metadata.
 */
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
	private double _shipping;
	private ArrayList<InvoiceItem> _items = new ArrayList<InvoiceItem>();
	private int _taxPercentage;
	private int _discountPercentage;
	
	/*
	 * Default constructor.
	 */
	public Invoice()
	{
		
	}
	
	public int getTaxPercentage()
	{
		return this._taxPercentage;
	}
	
	public void setTaxPercentage(int percentage)
	{
		this._taxPercentage = percentage;
	}
	
	public void setDiscountPercentage(int percentage)
	{
		this._discountPercentage = percentage;
	}
	
	public int getDiscountPercentage()
	{
		return this._discountPercentage;
	}

	public double getSubtotal()
	{
		double total = 0;
		for (final InvoiceItem item : this._items)
		{
			total += item.getPrice();
		}
		
		return total;
	}
	
	public double getTotal()
	{
		return getSubtotal() + this.getShipping() + this.getTax() - this.getDiscount();
	}
	
	public boolean containsItem(InvoiceItem item)
	{
		return this._items.contains(item);
	}
	
	public ArrayList<InvoiceItem> getItems()
	{
		return this._items;
	}
	
	public void setItems(ArrayList<InvoiceItem> items)
	{
		this._items = items;
	}
	
	/*
	 * Generates a frequency map of the items.
	 */
	public HashMap<InvoiceItem, Integer> getItemsWithCounts()
	{
		HashMap<InvoiceItem, Integer> itemCounts = new HashMap<InvoiceItem, Integer>();
		for (final InvoiceItem item : this.getItems())
		{
			if (itemCounts.containsKey(item))
			{
				itemCounts.put(item, itemCounts.get(item)+1);
			}
			else
			{
				itemCounts.put(item, 1);
			}
		}
		
		return itemCounts;
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
		return getPercent(getSubtotal(), this._taxPercentage);
	}

	public double getDiscount() 
	{
		return getPercent(getSubtotal(), this._discountPercentage);
	}

	public double getShipping() 
	{
		return _shipping;
	}

	public void setShipping(double _shipping) 
	{
		this._shipping = _shipping;
	}//setShipping
	
	/*
	 * Calculates the value of a percentage of a given value.
	 */
	private double getPercent(double value, int percent)
	{
		return (percent/(double)100)*value;
	}//getPercent
}//class
