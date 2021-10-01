package com.jinvoice.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

class TotalsPanel extends JPanel
{
	final JLabel _subtotalLbl = new JLabel("Subtotal ($)");
	final JTextField _subtotalField = new JTextField();
	
	final JLabel _taxLbl = new JLabel("Tax (%)");
	final JSpinner _taxField = new JSpinner();
	
	final JLabel _discountLbl = new JLabel("Discount (%)");
	final JSpinner _discountField = new JSpinner();
	
	final JLabel _shippingLbl = new JLabel("Shipping ($)");
	final JTextField _shippingField = new JTextField();
	
	final JLabel _totalLbl = new JLabel("Total ($)");
	final JTextField _totalField = new JTextField();
	
	public TotalsPanel()
	{
		this.setLayout(new GridBagLayout());
		
		Font totalFont = new Font("SansSerif", Font.BOLD, 14);
		SpinnerNumberModel taxPercentSpinnerModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);
		SpinnerNumberModel discountPercentSpinnerModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);

		addComponent(this._subtotalLbl,
				0, 0,
				1, 1,
				0, 0);
		this._subtotalField.setEditable(false);
		this._subtotalField.setFont(totalFont);
		addComponent(this._subtotalField,
				1, 0,
				1, 1,
				1, 0);
		
		addComponent(this._taxLbl,
				0, 1,
				1, 1,
				0, 0);
		this._taxField.setModel(taxPercentSpinnerModel);
		addComponent(this._taxField,
				1, 1,
				1, 1,
				1, 0);
		
		addComponent(this._discountLbl,
				0, 2,
				1, 1,
				0.1, 0);
		this._discountField.setModel(discountPercentSpinnerModel);
		addComponent(this._discountField,
				1, 2,
				1, 1,
				1, 0);
		
		addComponent(this._shippingLbl,
				0, 3,
				1, 1,
				0, 0);
		addComponent(this._shippingField,
				1, 3,
				1, 1,
				1, 0);
		
		addComponent(this._totalLbl,
				0, 4,
				1, 1,
				0, 0);
		this._totalField.setEditable(false);
		this._totalField.setFont(totalFont);
		addComponent(this._totalField,
				1, 4,
				1, 1,
				1, 0);
	}
	
	private void addComponent(Component component,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(2,2,2,2);
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		this.add(component, c);
	}
}
