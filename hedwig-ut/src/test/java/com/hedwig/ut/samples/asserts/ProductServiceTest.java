package com.hedwig.ut.samples.asserts;

import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Created by patrick on 15/8/13.
 *
 * @version $Id$
 */
public class ProductServiceTest {

    @Test
    public void shouldReturnTxFrom12MayWithQuantity7() throws ParseException {
        Date dateToLookFor = new SimpleDateFormat("yyyy-MM-dd").parse("2012-05-12");
        ProductService service = new ProductService();
        // some complex set up here


        Map<Date, List<Transaction>> transactions = service.getTransactions(new Product());

        System.out.println(transactions);
        boolean found = false;
        for (Map.Entry<Date, List<Transaction>> dateListEntry : transactions.entrySet()) {
            if (dateListEntry.getKey().equals(dateToLookFor)) {
                for (Transaction transaction : dateListEntry.getValue()) {
                    if (transaction.getQuantity() == 7)	{
                        found = true;
                    }
                }
            }
        }
        assertThat(found).isTrue();

        //CustomAssert.assertThat(transactions).hasTransaction("2012-05-12", 7);
    }
}