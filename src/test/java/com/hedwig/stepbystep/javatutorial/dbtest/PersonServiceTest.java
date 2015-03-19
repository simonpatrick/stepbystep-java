package com.hedwig.stepbystep.javatutorial.dbtest;

import com.hedwig.stepbystep.javatutorial.dbtest.annotation.DatabaseSetup;
import com.hedwig.stepbystep.javatutorial.dbtest.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class PersonServiceTest {

	@Autowired
	private PersonService personService;

	@Test
	@DatabaseSetup("sampleData.xml")
	public void testFind() throws Exception {
		List<Person> personList = this.personService.find("hil");
		assertEquals(1, personList.size());
		assertEquals("Phillip", personList.get(0).getFirstName());
	}

	@Test
	@DatabaseSetup("sampleData.xml")
	@ExpectedDatabase("expectedData.xml")
	public void testRemove() throws Exception {
		this.personService.remove(1);
	}

}
