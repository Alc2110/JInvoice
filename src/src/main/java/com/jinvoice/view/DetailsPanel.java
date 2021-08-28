package com.jinvoice.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

class DetailsPanel extends JPanel
{
	final JLabel _titleLbl = new JLabel("Title");
	final JTextField _titleField = new JTextField();

	final JLabel _numberLbl = new JLabel("Number");
	final JSpinner _numberField = new JSpinner();

	final JLabel _dateLbl = new JLabel("Date");
	final JDatePickerImpl _datePicker;

	final JLabel _paymentTermsLbl = new JLabel("Payment Terms");
	final JTextField _paymentTermsField = new JTextField();

	final JLabel _dueDateLbl = new JLabel("Due Date");
	final JDatePickerImpl _dueDatePicker;

	final Font _detailsFont = new Font("SansSerif", Font.PLAIN, 9);

	public DetailsPanel()
	{
		this.setLayout(new GridBagLayout());

		addComponent(this._titleLbl, 
				0, 0,
				1, 1,
				0, 0);
		this._titleField.setFont(this._detailsFont);
		addComponent(this._titleField, 
				1, 0,
				1, 1,
				1, 0);

		addComponent(this._numberLbl, 
				0, 2,
				1, 1,
				0, 0);

		SpinnerNumberModel numberSpinnerModel = new SpinnerNumberModel();
		addComponent(this._numberField, 
				1, 2,
				1, 1,
				1, 0);

		addComponent(this._dateLbl, 
				0, 3,
				1, 1,
				0, 0);
		UtilDateModel dateModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
		this._datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		addComponent(this._datePicker, 
				1, 3,
				1, 1,
				1, 0);

		addComponent(this._paymentTermsLbl, 
				0, 4,
				1, 1,
				0, 0);
		this._paymentTermsField.setFont(this._detailsFont);
		addComponent(this._paymentTermsField, 
				1, 4,
				1, 1,
				1, 0);

		addComponent(this._dueDateLbl, 
				0, 5,
				1, 1,
				0, 0);
		UtilDateModel dueDateModel = new UtilDateModel();
		JDatePanelImpl dueDatePanel = new JDatePanelImpl(dueDateModel, p);
		this._dueDatePicker = new JDatePickerImpl(dueDatePanel, new DateComponentFormatter());
		addComponent(this._dueDatePicker, 
				1, 5,
				1, 1,
				1, 0);
	}
	private void addComponent(Component component,
			int gridx, int gridy,
			int gridwidth, int gridheight,
			double weightx, double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = weightx;
		c.weighty = weighty;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2,2,2,2);
		c.gridx = gridx;
		c.gridy = gridy;
		this.add(component, c);
	}
}
