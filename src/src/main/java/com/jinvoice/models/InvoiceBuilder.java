package com.jinvoice.models;

import java.util.ArrayList;
import java.util.Date;

/*
 * Fluent interface for creating Invoice objects.
 */
public class InvoiceBuilder
{
	protected Invoice _invoice = new Invoice();
	
	/*
	 * Default constructor.
	 */
	public InvoiceBuilder()
	{
		
	}
	
	/*
	 * Constructor with invoice dependency injection.
	 */
	public InvoiceBuilder(Invoice invoice)
	{
		this._invoice = invoice;
	}
	
	public InvoiceBuilder from(String from)
	{
		this._invoice.setFrom(from);
		return this;
	}
	
	public InvoiceBuilder to(String billTo, String shipTo)
	{
		this._invoice.setBillTo(billTo);
		this._invoice.setShipTo(shipTo);
		
		return this;
	}
	
	public InvoiceBuilder withDetails(String title, int number,
			Date date, Date dueDate,
			String paymentTerms)
	{
		this._invoice.setTitle(title);
		this._invoice.setNumber(number);
		this._invoice.setDate(date);
		this._invoice.setDueDate(dueDate);
		this._invoice.setPaymentTerms(paymentTerms);
		
		return this;
	}
	
	public InvoiceBuilder withItems(ArrayList<InvoiceItem> items)
	{
		for (final InvoiceItem item : items)
		{
			this._invoice.addItem(item);
		}
		return this;
	}
	
	public InvoiceBuilder withNotes(String notes)
	{
		this._invoice.setNotes(notes);
		return this;
	}

	public Invoice build()
	{
		return this._invoice;
	}
}
