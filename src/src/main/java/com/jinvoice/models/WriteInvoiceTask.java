package com.jinvoice.models;

import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

import com.jinvoice.view.IMainWindow;
import com.jinvoice.view.InvoiceWriter;

/*
 * Writes the invoice to a file on a worker thread.
 */
public class WriteInvoiceTask extends SwingWorker<Void,Void>
{
	protected InvoiceWriter _invoiceWriter;
	protected IMainWindow _view;
	
	/*
	 * Constructor with dependency injection.
	 */
	public WriteInvoiceTask(InvoiceWriter invoiceWriter, IMainWindow view) {
		this._invoiceWriter = invoiceWriter;
		this._view = view;
	}

	@Override
	protected Void doInBackground() throws Exception {
		this._invoiceWriter.write();
		return null;
	}
	
	@Override
	protected void done()
	{
		try
		{
			get();
			this._view.showInfoDialog("Export invoice", "Successfully exported invoice file.");
		}
		catch (InterruptedException ex)
		{
			this._view.showInfoDialog("Operation Interrupted", "Save invoice file interrupted.");
		}
		catch (ExecutionException ex)
		{
			this._view.showInfoDialog("Error", "Error saving invoice file: " + ex.getCause().getMessage());
		}
		finally
		{
			this._view.setCreateButtonEnabled(true);
			this._view.setCancelButtonEnabled(true);
		}
	}//done
}//class
