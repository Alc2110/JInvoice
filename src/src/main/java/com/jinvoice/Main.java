package com.jinvoice;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jinvoice.view.*;

/*
 * JInvoice
 * 
 * Simple invoice generator.
 */
public class Main
{
	/*
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				// set the look and feel
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} 
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				} 
				catch (InstantiationException e) 
				{
					e.printStackTrace();
				} 
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				} 
				catch (UnsupportedLookAndFeelException e)
				{
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				// show the main window
				MainWindow window = new MainWindow(Configuration.APP_NAME, Configuration.APP_VERSION,
						Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGHT,
						Configuration.ATTRIBUTIONS);
				window.setVisible(true);
			}
		});
	}//main
	
	public static class Configuration
	{
		public static final int WINDOW_WIDTH = 600;
		public static final int WINDOW_HEIGHT = 500;
		
		public static final String APP_NAME = "JInvoice";
		public static final String APP_VERSION = "0.9.0";
		public static final String[] ATTRIBUTIONS = new String[]
				{
						"Apache POI by Apache Software Foundation",
						//"Apache PDFBox by Apache Software Foundation",
						"JDatePicker by Juan Heyns"
				};
	}
}