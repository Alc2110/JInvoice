package com.jinvoice.view;

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jinvoice.presenter.IViewListener;

class SpinnerListener extends AbstractListener implements ChangeListener
{
	public SpinnerListener(ArrayList<IViewListener> listeners)
	{
		super(listeners);
	}

	@Override
	public void stateChanged(ChangeEvent arg0)
	{
		notifyViewListeners();
	}
}
