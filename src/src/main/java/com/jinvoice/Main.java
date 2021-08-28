package com.jinvoice;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jinvoice.view.*;
import com.jinvoice.presenter.*;

/*
 * JInvoice
 * 
 * Simple invoice generator.
 */
public class Main
{
	public Main()
	{
		final MainWindowImp _view = new MainWindowImp(Configuration.APP_NAME, Configuration.APP_VERSION,
				Configuration.WINDOW_WIDTH, Configuration.WINDOW_HEIGHT,
				Configuration.ATTRIBUTIONS);
		new Presenter(_view);
	}
	
	/*
	 * Program entry point.
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

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

						// show main window
						new Main();
					}
				});
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