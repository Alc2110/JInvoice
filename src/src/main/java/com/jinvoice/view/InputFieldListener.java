package com.jinvoice.view;

import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jinvoice.presenter.IViewListener;

class InputFieldListener extends AbstractListener implements DocumentListener
{
	public InputFieldListener(ArrayList<IViewListener> listeners)
	{
		super(listeners);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0)
	{	
		notifyViewListeners();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0)
	{
		notifyViewListeners();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0)
	{
		notifyViewListeners();
	}
}
