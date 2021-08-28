package com.jinvoice.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

class NorthPanel extends JPanel
{
	final ToPanel _toPanel = new ToPanel();
	final DetailsPanel _detailsPanel = new DetailsPanel();
	
	public NorthPanel()
	{
		this.setLayout(new GridBagLayout());

		addPanel(this._toPanel, 0, 0, 2, 1, 1, 1);
		addPanel(this._detailsPanel, 2, 0, 2, 1, 0.5, 0);
	}
	
	private void addPanel(JPanel panel,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = new Insets(2,2,2,2);
		this.add(panel, gbc);
	}
}
