package com.jinvoice.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import com.jinvoice.models.Invoice;
import com.jinvoice.models.InvoiceItem;

/*
 * Writes a single invoice with multiple items to an Excel spreadsheet.
 */
public class ExcelInvoiceWriter extends InvoiceWriter
{
	private final int TITLE_ROW = 0;
	private final int FROM_ROW = 1;
	private final int BILL_TO_ROW = 2;
	private final int NUMBER_ROW = 3;
	private final int DATE_ROW = 4;
	private final int PAYMENT_TERMS_ROW = 5;
	private final int DUE_DATE_ROW = 6;
	private final int HEADER_ROW = 8;
	/*
	private final int META_KEY_COL = 0;
	private final int META_VAL_COL = 1;
	
	private final int DESCRIPTION_COL = 0;
	private final int AMOUNT_COL = 1;
	private final int QUANTITY_COL = 2;
	private final int ITEM_TOTAL_COL = 3;
	*/
	
	// TODO: tweak colours
	private final short HEADER_AND_META_STYLE_COLOUR_INDEX = IndexedColors.BLUE.getIndex();
	private final short ODD_ROW_COLOUR_INDEX = IndexedColors.LIGHT_BLUE.getIndex();
	private final short EVEN_ROW_COLOUR_INDEX = IndexedColors.SKY_BLUE.getIndex();
	
	private final String CURRENCY_FORMAT = "$#,##0.00_);($#,##0.00)";
	private final String DATE_FORMAT = "dd/mm/yyyy";
	
	/*
	 * Constructor with dependency injection.
	 */
	public ExcelInvoiceWriter(Invoice invoice, String filePath)
	{
		super(filePath, invoice);
	}
	
	/*
	 * Write all data and save to disk.
	 */
	public void write() throws IOException
	{
		// create spreadsheet
		final XSSFWorkbook wb = new XSSFWorkbook();
		final XSSFSheet ws = wb.createSheet(this._invoice.getTitle());
		
		// create all styles
		// headers
		CellStyle headerAndMetaStyle = wb.createCellStyle();
		headerAndMetaStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerAndMetaStyle.setFillForegroundColor(HEADER_AND_META_STYLE_COLOUR_INDEX);
		// headers with date
		CellStyle headerAndMetaStyleWithDate = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		headerAndMetaStyleWithDate.setDataFormat(
				createHelper.createDataFormat().getFormat(DATE_FORMAT)
				);
		headerAndMetaStyleWithDate.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerAndMetaStyleWithDate.setFillForegroundColor(HEADER_AND_META_STYLE_COLOUR_INDEX);
		// even row
		CellStyle evenRowStyle = wb.createCellStyle();
		evenRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		evenRowStyle.setFillForegroundColor(EVEN_ROW_COLOUR_INDEX);
		// even row with currency
		CellStyle evenRowStyleWithCurrency = wb.createCellStyle();
		evenRowStyleWithCurrency.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		evenRowStyleWithCurrency.setFillForegroundColor(EVEN_ROW_COLOUR_INDEX);
		evenRowStyleWithCurrency.setDataFormat(
				createHelper.createDataFormat().getFormat(CURRENCY_FORMAT)
				);
		// odd row
		CellStyle oddRowStyle = wb.createCellStyle();
		oddRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		oddRowStyle.setFillForegroundColor(ODD_ROW_COLOUR_INDEX);
		// odd row with currency
		CellStyle oddRowStyleWithCurrency = wb.createCellStyle();
		oddRowStyleWithCurrency.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		oddRowStyleWithCurrency.setFillForegroundColor(ODD_ROW_COLOUR_INDEX);
		oddRowStyleWithCurrency.setDataFormat(
				createHelper.createDataFormat().getFormat(CURRENCY_FORMAT)
				);
		// headers with currency
		CellStyle headerAndMetaStyleWithCurrency = wb.createCellStyle();
		headerAndMetaStyleWithCurrency.setDataFormat(
				createHelper.createDataFormat().getFormat(CURRENCY_FORMAT)
				);
		headerAndMetaStyleWithCurrency.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerAndMetaStyleWithCurrency.setFillForegroundColor(HEADER_AND_META_STYLE_COLOUR_INDEX);
		
		// write headers and metadata
		Row row;
		Cell cell;	
		// invoice title
		row = ws.createRow(TITLE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Invoice: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getTitle());
		cell.setCellStyle(headerAndMetaStyle);
		// from
		row = ws.createRow(FROM_ROW);
		cell = row.createCell(0);
		cell.setCellValue("From: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getFrom());
		cell.setCellStyle(headerAndMetaStyle);
		// bill to
		row = ws.createRow(BILL_TO_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Bill To: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getBillTo());
		cell.setCellStyle(headerAndMetaStyle);
		// invoice number
		row = ws.createRow(NUMBER_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Number: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getNumber());
		cell.setCellStyle(headerAndMetaStyle);
		// date
		row = ws.createRow(DATE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Date: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getDate());
		cell.setCellStyle(headerAndMetaStyleWithDate);
		// payment terms
		row = ws.createRow(PAYMENT_TERMS_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Payment Terms: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getPaymentTerms());
		cell.setCellStyle(headerAndMetaStyle);
		// due date
		row = ws.createRow(DUE_DATE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Due Date: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getDueDate());
		cell.setCellStyle(headerAndMetaStyleWithDate);
		// header row
		row = ws.createRow(HEADER_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Description");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(1);
		cell.setCellValue("Amount");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(2);
		cell.setCellValue("Quantity");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(3);
		cell.setCellValue("Item Total");
		cell.setCellStyle(headerAndMetaStyle);
		
		// write data
		HashMap<InvoiceItem, Integer> freqMap = this._invoice.getItemsWithCounts();
		int currRow = HEADER_ROW + 1;
		for (final Map.Entry<InvoiceItem, Integer> kvp : freqMap.entrySet()) {
			InvoiceItem item = kvp.getKey();
			int count = kvp.getValue();
			
			writeItemRow(ws, 
					evenRowStyle, evenRowStyleWithCurrency,
					oddRowStyle, oddRowStyleWithCurrency,
					item, count, currRow);
			currRow++;
		}
		
		// write totals
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Subtotal: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(3);
		String subtotalFormula = "SUM(D10:D" + currRow + ")";
		cell.setCellFormula(subtotalFormula);
		cell.setCellStyle(headerAndMetaStyleWithCurrency);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Tax (%): ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getTaxPercentage());
		cell.setCellStyle(headerAndMetaStyle);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Discount (%): " );
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getDiscountPercentage());
		cell.setCellStyle(headerAndMetaStyle);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Shipping: ");
		cell.setCellStyle(headerAndMetaStyleWithCurrency);
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getShipping());
		cell.setCellStyle(headerAndMetaStyleWithCurrency);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("TOTAL: ");
		cell.setCellStyle(headerAndMetaStyle);
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getTotal());
		cell.setCellStyle(headerAndMetaStyleWithCurrency);
		
		// save to disk
		FileOutputStream os = new FileOutputStream(this._filePath);
		wb.write(os);
		wb.close();
	}//write
	
	/*
	 * Write and style a single item row.
	 */
	private void writeItemRow(XSSFSheet ws, 
			CellStyle evenStyle, CellStyle evenStyleWithCurrency,
			CellStyle oddStyle, CellStyle oddStyleWithCurrency,
			InvoiceItem item, int itemCount, int currRow)
	{
		boolean isOdd = (currRow+1)%2==1;
		
		Row row = ws.createRow(currRow);
		Cell cell = row.createCell(0);
		if (isOdd)
		{
			cell.setCellStyle(oddStyle);
		}
		else
		{
			cell.setCellStyle(evenStyle);
		}
		cell.setCellValue(item.getDescription());
		if (isOdd)
		{
			cell.setCellStyle(oddStyle);
		}
		else
		{
			cell.setCellStyle(evenStyle);
		}
		cell = row.createCell(1);
		cell.setCellValue(item.getPrice());
		if (isOdd)
		{
			cell.setCellStyle(oddStyleWithCurrency);
		}
		else
		{
			cell.setCellStyle(evenStyleWithCurrency);
		}
		cell = row.createCell(2);
		cell.setCellValue(itemCount);
		if (isOdd)
		{
			cell.setCellStyle(oddStyle);
		}
		else
		{
			cell.setCellStyle(evenStyle);
		}
		cell = row.createCell(3);
		String itemTotalFormula = CellReference.convertNumToColString(1) + (currRow+1) + "*" + CellReference.convertNumToColString(2) + (currRow+1);
		cell.setCellFormula(itemTotalFormula);
		if (isOdd)
		{
			cell.setCellStyle(oddStyleWithCurrency);
		}
		else
		{
			cell.setCellStyle(evenStyleWithCurrency);
		}
	}//writeItemRow
}//class
