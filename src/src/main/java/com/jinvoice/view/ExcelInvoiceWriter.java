package com.jinvoice.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

import com.jinvoice.models.Invoice;
import com.jinvoice.models.InvoiceItem;

public class ExcelInvoiceWriter extends InvoiceWriter
{
	// https://howtodoinjava.com/java/library/readingwriting-excel-files-in-java-poi-tutorial/#useful_common_classes
	private final int TITLE_ROW = 0;
	private final int FROM_ROW = 1;
	private final int BILL_TO_ROW = 2;
	private final int NUMBER_ROW = 3;
	private final int DATE_ROW = 4;
	private final int PAYMENT_TERMS_ROW = 5;
	private final int DUE_DATE_ROW = 6;
	private final int HEADER_ROW = 8;
	
	/*
	 * Constructor with dependency injection.
	 */
	public ExcelInvoiceWriter(Invoice invoice, String filePath)
	{
		super(filePath, invoice);
	}
	
	public void write() throws IOException
	{
		// create spreadsheet
		final XSSFWorkbook wb = new XSSFWorkbook();
		final XSSFSheet ws = wb.createSheet(this._invoice.getTitle());
		
		// write headers and metadata
		Row row;
		Cell cell;
		// invoice title
		row = ws.createRow(TITLE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Invoice: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getTitle());
		// from
		row = ws.createRow(FROM_ROW);
		cell = row.createCell(0);
		cell.setCellValue("From: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getFrom());
		// bill to
		row = ws.createRow(BILL_TO_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Bill To: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getBillTo());
		// invoice number
		row = ws.createRow(NUMBER_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Number: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getNumber());
		// date
		row = ws.createRow(DATE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Date: ");
		cell = row.createCell(1);
		CellStyle dateStyle = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		dateStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("dd/mm/yyyy")
				);
		cell.setCellValue(this._invoice.getDate());
		cell.setCellStyle(dateStyle);
		// payment terms
		row = ws.createRow(PAYMENT_TERMS_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Payment Terms: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getPaymentTerms());
		// due date
		row = ws.createRow(DUE_DATE_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Due Date: ");
		cell = row.createCell(1);
		cell.setCellValue(this._invoice.getDueDate());
		cell.setCellStyle(dateStyle);
		// header row
		row = ws.createRow(HEADER_ROW);
		cell = row.createCell(0);
		cell.setCellValue("Description");
		cell = row.createCell(1);
		cell.setCellValue("Amount");
		cell = row.createCell(2);
		cell.setCellValue("Quantity");
		cell = row.createCell(3);
		cell.setCellValue("Item Total");
		
		// write data
		HashMap<InvoiceItem, Integer> freqMap = this._invoice.getItemsWithCounts();
		int currRow = HEADER_ROW + 1;
		CellStyle currencyStyle = wb.createCellStyle();
		currencyStyle.setDataFormat(
				createHelper.createDataFormat().getFormat("_($* #,##0.00_);_($* (#,##0.00);_($* \\\"-\\\"??_);_(@_)")
				);
		for (final Map.Entry<InvoiceItem, Integer> kvp : freqMap.entrySet()) {
			InvoiceItem item = kvp.getKey();
			int count = kvp.getValue();
			
			writeItemRow(ws, currencyStyle, item, count, currRow);
			currRow++;
		}
		
		// write totals
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Subtotal: ");
		cell = row.createCell(3);
		String subtotalFormula = "SUM(D10:D" + currRow + ")";
		cell.setCellFormula(subtotalFormula);
		cell.setCellStyle(currencyStyle);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Tax (%): ");
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getTaxPercentage());
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Discount (%): " );
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getDiscountPercentage());
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("Shipping: ");
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getShipping());
		cell.setCellStyle(currencyStyle);
		currRow++;
		row = ws.createRow(currRow);
		cell = row.createCell(2);
		cell.setCellValue("TOTAL: ");
		cell = row.createCell(3);
		cell.setCellValue(this._invoice.getTotal());
		cell.setCellStyle(currencyStyle);
		// save to disk
		FileOutputStream os = new FileOutputStream(this._filePath);
		wb.write(os);
	}
	
	private void writeItemRow(XSSFSheet ws, CellStyle currencyStyle, InvoiceItem item, int itemCount, int currRow)
	{
		Row row = ws.createRow(currRow);
		Cell cell = row.createCell(0);
		cell.setCellValue(item.getDescription());
		cell = row.createCell(1);
		cell.setCellValue(item.getPrice());
		cell.setCellStyle(currencyStyle);
		cell = row.createCell(2);
		cell.setCellValue(itemCount);
		cell = row.createCell(3);
		String itemTotalFormula = CellReference.convertNumToColString(1) + (currRow+1) + "*" + CellReference.convertNumToColString(2) + (currRow+1);
		cell.setCellFormula(itemTotalFormula);
		cell.setCellStyle(currencyStyle);
	}
}
