package com.jinvoice.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ToPanel extends JPanel
{
	final JTextArea _fromField = new JTextArea();
	final JScrollPane _fromFieldPane = new JScrollPane(this._fromField);
	
	final JTextArea _billToField = new JTextArea();
	final JScrollPane _billToFieldPane = new JScrollPane(this._billToField);
	
	final JTextArea _shipToField = new JTextArea();
	final JScrollPane _shipToFieldPane = new JScrollPane(this._shipToField);
	
	final Font _toFont = new Font("SansSerif", Font.PLAIN, 9);
	
	public ToPanel()
	{
		this.setLayout(new GridBagLayout());
		
		this._fromField.setFont(this._toFont);
		this._fromField.setLineWrap(true);
		this._fromFieldPane.setBorder(BorderFactory.createTitledBorder("From"));
		addTextAreaPane(this._fromFieldPane, 0, 0, 2, 1, 1, 1);
		
		this._billToField.setFont(this._toFont);
		this._billToField.setLineWrap(true);
		this._billToFieldPane.setBorder(BorderFactory.createTitledBorder("Bill To"));
		addTextAreaPane(this._billToFieldPane, 0, 1, 1, 1, 1, 1);
		
		this._shipToField.setFont(this._toFont);
		this._shipToField.setLineWrap(true);
		this._shipToFieldPane.setBorder(BorderFactory.createTitledBorder("Ship To (optional)"));
		addTextAreaPane(this._shipToFieldPane, 1, 1, 1, 1, 1, 1);
	} 
	
	private void addTextAreaPane(JScrollPane scrollPane,
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
		this.add(scrollPane, gbc);
	}
}
