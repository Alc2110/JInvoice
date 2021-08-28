package com.jinvoice.view;

import java.util.ArrayList;

import com.jinvoice.presenter.IViewListener;

abstract class AbstractListener
{
	protected ArrayList<IViewListener> _listeners;
	
	public AbstractListener(ArrayList<IViewListener> listeners)
	{
		this._listeners = listeners;
	}
	
	protected void notifyViewListeners()
	{
		for (final IViewListener listener : this._listeners)
		{
			listener.onInputFieldUpdated();
		}
	}
}
