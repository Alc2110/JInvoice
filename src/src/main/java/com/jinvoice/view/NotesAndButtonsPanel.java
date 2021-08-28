package com.jinvoice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class NotesAndButtonsPanel extends JPanel
{
	final JTextArea _notesField = new JTextArea();
	final JScrollPane _notesFieldPane = new JScrollPane(this._notesField);
	
	final ButtonsPanel _buttonsPanel;
	
	public NotesAndButtonsPanel(Dimension btnDim)
	{
		this.setLayout(new BorderLayout());
		
		this._buttonsPanel = new ButtonsPanel(btnDim);
		
		Font notesFont = new Font("SansSerif", Font.PLAIN, 9);
		this._notesField.setFont(notesFont);
		this._notesField.setLineWrap(true);
		this._notesFieldPane.setPreferredSize(new Dimension(100, 100));
		this._notesFieldPane.setBorder(BorderFactory.createTitledBorder("Notes (optional)"));
		this.add(this._notesFieldPane, BorderLayout.CENTER);
		
		this.add(this._buttonsPanel, BorderLayout.SOUTH);
	}
	
	class ButtonsPanel extends JPanel
	{
		final JButton _createButton = new JButton("Create");
		final JButton _cancelButton = new JButton("Cancel");
		
		public ButtonsPanel(Dimension btnDim)
		{
			this.setLayout(new FlowLayout());
			
			this._createButton.setPreferredSize(btnDim);
			this.add(this._createButton);
			this._cancelButton.setPreferredSize(btnDim);
			this.add(this._cancelButton);
		}
	}
}
