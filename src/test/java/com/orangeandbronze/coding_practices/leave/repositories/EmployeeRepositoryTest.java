package com.orangeandbronze.coding_practices.leave.repositories;

import static org.junit.Assert.*;

import org.junit.*;

import com.orangeandbronze.coding_practices.leave.domain.*;

public class EmployeeRepositoryTest {
	
	@Before
	public void setup() throws Exception {
		DbUnitUtil.cleanInsert();
	}

	@Test
	public void retrieveEmployeeWithApprover() throws Exception {
		EmployeeRepository repo = new EmployeeRepository();
		Employee emp1Actual = repo.findById(1);
		Employee emp1Expected = new Employee(1, 11, 11, new Employee(1, 11, 11, null));
		assertEquals(emp1Expected, emp1Actual);
		assertEquals(emp1Expected.getSlCredits(), emp1Actual.getSlCredits());
		assertEquals(emp1Expected.getVlCredits(), emp1Actual.getVlCredits());
		assertEquals(emp1Expected.getLeaveApprover(), emp1Actual.getLeaveApprover());
		Employee emp2Actual = repo.findById(2);
		Employee emp2Expected = new Employee(2, 12, 12, new Employee(1, 11, 11, null));
		assertEquals(emp2Expected, emp2Actual);
		assertEquals(emp2Expected.getLeaveApprover(), emp2Actual.getLeaveApprover());
	}

}
