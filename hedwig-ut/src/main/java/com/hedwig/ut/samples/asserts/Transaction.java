package com.hedwig.ut.samples.asserts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private final Date date;
	private final int quantity;

	public Transaction(String date, int quantity) throws ParseException {
		this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		this.quantity = quantity;
	}

	public Date getDate() {
		return date;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"date=" + date +
				", quantity=" + quantity +
				'}';
	}
}
