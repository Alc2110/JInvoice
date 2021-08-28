package com.jinvoice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class ItemsTablePanel extends JPanel
{
	final JTable _table = new JTable();
	final JScrollPane _scrollPane;
	
	final String[] TABLE_HEADERS = new String[]
			{
					"Description", 
					"Price",
					"Quantity",
					"Amount"
			};
	
	final ButtonsPanel _btnsPanel = new ButtonsPanel();
	
	public ItemsTablePanel()
	{
		this.setLayout(new BorderLayout());
		
		DefaultTableModel tableModel = new DefaultTableModel()
		{
			@Override
			public boolean isCellEditable(int i, int i1)
			{
				return false;
			}
		};
		tableModel.addColumn(TABLE_HEADERS[0]);
		tableModel.addColumn(TABLE_HEADERS[1]);
		tableModel.addColumn(TABLE_HEADERS[2]);
		tableModel.addColumn(TABLE_HEADERS[3]);
		this._table.setModel(tableModel);
		this._table.setFillsViewportHeight(true);
		this._scrollPane = new JScrollPane(this._table);
		this._scrollPane.setViewportView(this._table);
		this._scrollPane.setBorder(BorderFactory.createTitledBorder("Items"));
		this.add(this._scrollPane, BorderLayout.CENTER);
		
		this.add(this._btnsPanel, BorderLayout.SOUTH);
	}
	
	public Dimension getButtonSize()
	{
		return this._btnsPanel.getButtonSize();
	}
	
	class ButtonsPanel extends JPanel
	{
		final JButton _addBtn = new JButton("Add");
		final JButton _removeBtn = new JButton("Remove Selected");
		
		public ButtonsPanel()
		{
			this.setLayout(new FlowLayout());
			
			this._addBtn.setPreferredSize(getButtonSize());
			
			this.add(this._addBtn);
			this.add(this._removeBtn);
		}
		
		public Dimension getButtonSize()
		{
			return this._removeBtn.getPreferredSize();
		}
	}
}
