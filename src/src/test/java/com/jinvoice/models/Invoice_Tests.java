package com.jinvoice.models;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class Invoice_Tests {

	/*
	@Test
	void testInvoice() {
		fail("Not yet implemented");
	}

	@Test
	void testGetItems() {
		fail("Not yet implemented");
	}
	
	@Test
	void testAddItem() {
		fail("Not yet implemented");
	}
	*/
	
	@Test
	void getSubtotal() {
		// arrange
		Invoice invoice = new Invoice();
		InvoiceItem item1 = new InvoiceItem("item1", 1.00);
		InvoiceItem item2 = new InvoiceItem("item2", 0.50);
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item2);
		double expectedTotal = 2;
		
		// act
		double actualTotal = invoice.getSubtotal();
		
		// assert
		assertTrue(actualTotal==expectedTotal);
	}
	
	@Test
	void getTotal() {
		// arrange
		Invoice invoice = new Invoice();
		InvoiceItem item1 = new InvoiceItem("item1", 1.00);
		InvoiceItem item2 = new InvoiceItem("item2", 0.50);
		invoice.setDiscountPercentage(1);
		invoice.setTaxPercentage(2);
		invoice.setShipping(1);
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item2);
		double expectedTotal = 3.02;

		// act
		double actualTotal = invoice.getTotal();

		// assert
		assertEquals(expectedTotal, actualTotal, 0);
	}

	@Test
	void testRemoveItem_exists() {
		// arrange
		Invoice invoice = new Invoice();
		invoice.addItem(new InvoiceItem("item1", 5.95));
		
		// act
		boolean removed = invoice.removeItem(new InvoiceItem("item1", 5.95));
		
		// assert
		assertTrue(removed);
		assertFalse(invoice.containsItem(new InvoiceItem("item1", 5.95)));
	}
	
	@Test
	void testRemoveItem_doesNotExist() {
		// arrange
		Invoice invoice = new Invoice();
		
		// act
		boolean removed = invoice.removeItem(new InvoiceItem("item1", 5.95));
		
		// assert
		assertFalse(removed);
	}
	
	@Test
	void getItemsWithCounts() {
		// arrange
		InvoiceItem item1 = new InvoiceItem("item1", 1);
		InvoiceItem item2 = new InvoiceItem("item2", 0.50);
		Invoice invoice = new Invoice();
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item2);
		
		// act
		HashMap<InvoiceItem, Integer> freqMap = invoice.getItemsWithCounts();
		
		// assert
		assertTrue(freqMap.get(item1)==1);
		assertTrue(freqMap.get(item2)==2);
	}

	/*
	@Test
	void testGetFrom() {
		fail("Not yet implemented");
	}

	@Test
	void testSetFrom() {
		fail("Not yet implemented");
	}

	@Test
	void testGetShipTo() {
		fail("Not yet implemented");
	}

	@Test
	void testSetShipTo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBillTo() {
		fail("Not yet implemented");
	}

	@Test
	void testSetBillTo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTitle() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTitle() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNumber() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNumber() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDate() {
		fail("Not yet implemented");
	}

	@Test
	void testSetDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPaymentTerms() {
		fail("Not yet implemented");
	}

	@Test
	void testSetPaymentTerms() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDueDate() {
		fail("Not yet implemented");
	}

	@Test
	void testSetDueDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNotes() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNotes() {
		fail("Not yet implemented");
	}

	@Test
	void testGetTax() {
		fail("Not yet implemented");
	}

	@Test
	void testSetTax() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDiscount() {
		fail("Not yet implemented");
	}

	@Test
	void testSetDiscount() {
		fail("Not yet implemented");
	}

	@Test
	void testGetShipping() {
		fail("Not yet implemented");
	}

	@Test
	void testSetShipping() {
		fail("Not yet implemented");
	}
	*/
}
