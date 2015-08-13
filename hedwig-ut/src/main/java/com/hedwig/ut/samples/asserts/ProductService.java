package com.hedwig.ut.samples.asserts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductService {

	
	public Map<Date, List<Transaction>> getTransactions(Product product) throws ParseException {
		Date date12 = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12");
		Date date09 = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-09");

		Map<Date, List<Transaction>>  result = new HashMap<Date, List<Transaction>>();
		List<Transaction> tx12 = Arrays.asList(new Transaction("2012-05-12", 5), new Transaction("2012-05-12", 7));
		List<Transaction> tx09 = Arrays.asList(new Transaction("2012-05-09", 3));
		result.put(date12, tx12);
		result.put(date09, tx09);
		return result;
	}
}
