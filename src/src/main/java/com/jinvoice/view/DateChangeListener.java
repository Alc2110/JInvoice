package com.jinvoice.view;

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jinvoice.presenter.IViewListener;

class DateChangeListener extends AbstractListener implements ChangeListener
{
	public DateChangeListener(ArrayList<IViewListener> listeners)
	{
		super(listeners);
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0)
	{
		notifyViewListeners();
	}
}
