package com.jinvoice.view;

import java.io.IOException;

import com.jinvoice.models.Invoice;

/*
 * Base class for writing invoice files.
 */
public abstract class InvoiceWriter
{
	protected String _filePath;
	protected Invoice _invoice;
	
	/*
	 * Constructor with dependency injection.
	 */
	public InvoiceWriter(String filePath, Invoice invoice) {
		this._filePath = filePath;
		this._invoice = invoice;
	}
	
	/*
	 * Write all data and save to disk.
	 */
	public abstract void write() throws IOException;
}
