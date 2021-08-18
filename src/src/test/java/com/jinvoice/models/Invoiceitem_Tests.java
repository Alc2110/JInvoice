package com.jinvoice.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvoiceItem_Tests {
	/*
	@Test
	void testHashCode() {
		fail("Not yet implemented");
	}
	*/
	/*
	@Test
	void testInvoiceItem() {
		fail("Not yet implemented");
	}
	*/
	/*
	@Test
	void testGetDescription() {
		fail("Not yet implemented");
	}
	*/
	/*
	@Test
	void testSetDescription() {
		fail("Not yet implemented");
	}
	*/
	/*
	@Test
	void testGetPrice() {
		fail("Not yet implemented");
	}
	*/
	/*
	@Test
	void testSetPrice() {
		fail("Not yet implemented");
	}
	*/
	@Test
	void testEqualsObject_areEqual() {
		// arrange
		InvoiceItem item1 = new InvoiceItem("item1", 5.95);
		InvoiceItem item2 = new InvoiceItem("item1", 5.95);
		
		// act
		boolean areEqual1 = item1.equals(item2);
		boolean areEqual2 = item2.equals(item1);
		
		// assert
		assertTrue(areEqual1);
		assertTrue(areEqual2);
	}

	@Test
	void testEqualsObject_areNotEqual() {
		// arrange
		InvoiceItem item1 = new InvoiceItem("test1", 5.95);
		InvoiceItem item2 = new InvoiceItem("test1", 6.95);
		InvoiceItem item3 = new InvoiceItem("test2", 8.95);
		InvoiceItem item4 = new InvoiceItem("test3", 8.95);
		
		// act
		boolean areEqual1 = item1.equals(item2);
		boolean areEqual2 = item3.equals(item4);
		
		// assert
		assertFalse(areEqual1);
		assertFalse(areEqual2);
	}
	
	@Test
	void testEqualsObject_notInvoiceItem() {
		// arrange
		InvoiceItem item = new InvoiceItem("test", 5.95);
		int notItem = 0;
		
		// act
		boolean areEqual = item.equals(notItem);
		
		// assert
		assertFalse(areEqual);
	}
}
