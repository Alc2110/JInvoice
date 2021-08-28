package com.jinvoice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

//import com.jinvoice.view.MainWindowImp.NotesAndButtonsPanel;
//import com.jinvoice.view.MainWindowImp.TotalsPanel;

class SouthPanel extends JPanel
{
	final TotalsPanel _totalsPanel = new TotalsPanel();
	final NotesAndButtonsPanel _notesAndButtonsPanel;
	
	public SouthPanel(Dimension btnDim)
	{
		this.setLayout(new BorderLayout());
		
		this._notesAndButtonsPanel = new NotesAndButtonsPanel(btnDim);

		this.add(this._notesAndButtonsPanel, BorderLayout.CENTER);
		this._totalsPanel.setPreferredSize(new Dimension(225,this._totalsPanel.getHeight()));
		this.add(this._totalsPanel, BorderLayout.EAST);
	}
}