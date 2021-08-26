package com.jinvoice.models;

import java.util.Objects;

public class InvoiceItem
{
	private String _description;
	private double _price;
	
	public InvoiceItem(String description, double price)
	{
		this._description = description;
		this._price = price;
	}
	
	public String getDescription()
	{
		return this._description;
	}
	
	public void setDescription(String description)
	{
		this._description = description;
	}
	
	public double getPrice()
	{
		return this._price;
	}
	
	public void setPrice(double price)
	{
		this._price = price;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof InvoiceItem))
		{
			return false;
		}
		
		if (obj == this)
		{
			return true;
		}
		
		InvoiceItem that = (InvoiceItem)obj;
		return (this._price == that._price && this._description == that._description);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this._description, this._price);
	}
	
	@Override
	public String toString()
	{
		return this._description + "; " + this._price;
	}
}//class
