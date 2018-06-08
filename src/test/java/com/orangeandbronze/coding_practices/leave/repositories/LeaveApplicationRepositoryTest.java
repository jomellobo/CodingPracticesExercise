package com.orangeandbronze.coding_practices.leave.repositories;

import static org.junit.Assert.*;

import java.time.*;
import java.util.*;

import org.dbunit.dataset.*;
import org.junit.*;

import com.orangeandbronze.coding_practices.leave.domain.*;

public class LeaveApplicationRepositoryTest {

	private static LeaveApplicationRepository repo = new LeaveApplicationRepository();

	@Before
	public void setup() throws Exception {
		DbUnitUtil.cleanInsert();
	}

	@Test
	public void testFindPendingByFilerEmployeeId() throws Exception {
		Set<LeaveApplication> expectedApplications = new HashSet<>();
		expectedApplications.add(new LeaveApplication(UUID
				.fromString("87e8fe72-1f15-4e77-a582-f54937fc6fa5"),
				new Employee(3, 0, 0, null), new Employee(2, 0, 0, null),
				LeaveType.VL, LocalDate.of(2015, 9, 1), LocalDate
						.of(2015, 9, 4), LeaveApplication.Status.PENDING));
		expectedApplications.add(new LeaveApplication(UUID
				.fromString("c992ab8f-88d3-440a-8a3c-accc7c9f9590"),
				new Employee(3, 0, 0, null), new Employee(2, 0, 0, null),
				LeaveType.SL, LocalDate.of(2015, 9, 1), LocalDate
						.of(2015, 9, 4), LeaveApplication.Status.PENDING));
		Set<LeaveApplication> actualApplications = new HashSet<>(
				repo.findPendingByFilerEmployeeId(3));
		assertEquals(expectedApplications, actualApplications);

	}

	@Test
	public void testFindById() throws Exception {
		LeaveApplication expected = new LeaveApplication(
				UUID.fromString("87e8fe72-1f15-4e77-a582-f54937fc6fa5"),
				new Employee(3, 0, 0, null), new Employee(2, 0, 0, null),
				LeaveType.VL, LocalDate.of(2015, 9, 1),
				LocalDate.of(2015, 9, 4), LeaveApplication.Status.PENDING);
		LeaveApplication actual = repo.findById(UUID
				.fromString("87e8fe72-1f15-4e77-a582-f54937fc6fa5"));
		assertEquals(expected, actual);
	}

	@Test
	public void testFindPendingByApprover() throws Exception {
		Set<LeaveApplication> expectedApplications = new HashSet<>();
		expectedApplications.add(new LeaveApplication(UUID
				.fromString("5d46f55c-d8dc-475b-913a-2c480281ccdc"),
				new Employee(1, 0, 0, null), new Employee(1, 0, 0, null),
				LeaveType.VL, LocalDate.of(2015, 9, 1), LocalDate
						.of(2015, 9, 4), LeaveApplication.Status.PENDING));
		expectedApplications.add(new LeaveApplication(UUID
				.fromString("779a9513-18ad-4165-97dd-604229cb6b1f"),
				new Employee(1, 0, 0, null), new Employee(1, 0, 0, null),
				LeaveType.SL, LocalDate.of(2015, 9, 1), LocalDate
						.of(2015, 9, 4), LeaveApplication.Status.PENDING));
		expectedApplications.add(new LeaveApplication(UUID
				.fromString("aebb940b-3f66-455f-b65d-e4c0718d67b6"),
				new Employee(2, 0, 0, null), new Employee(1, 0, 0, null),
				LeaveType.SL, LocalDate.of(2015, 9, 1), LocalDate
						.of(2015, 9, 4), LeaveApplication.Status.PENDING));
		Set<LeaveApplication> actualApplications = new HashSet<>(
				repo.findPendingByApprover(1));
		assertEquals(expectedApplications, actualApplications);

	}

	@Test
	public void testUpdate() throws Exception {
		Employee employee = new Employee(1, 0, 0, null);
		String leaveApplicationIdString = "5d46f55c-d8dc-475b-913a-2c480281ccdc";
		LeaveApplication application = new LeaveApplication(
				UUID.fromString(leaveApplicationIdString), employee, employee,
				LeaveType.VL, LocalDate.of(2015, 9, 1),
				LocalDate.of(2015, 9, 4), LeaveApplication.Status.PENDING);
		employee.cancel(application);
		repo.updateStatus(application);
		ITable result = DbUnitUtil.getIDatabaseConnection().createQueryTable(
				"",
				"select status from leave_applications where leave_application_id = '"
						+ leaveApplicationIdString + "'");
		assertEquals(LeaveApplication.Status.CANCELED.toString(),
				result.getValue(0, "status"));
	}

	@Test
	public void testCreate() throws Exception {
		String idString = "f8376730-92f3-4da2-b605-b22fb305f11d";
		LeaveApplication application = new LeaveApplication(
				UUID.fromString(idString), new Employee(1, 0, 0, null),
				new Employee(1, 0, 0, null), LeaveType.VL, LocalDate.of(2015,
						9, 1), LocalDate.of(2015, 9, 4),
				LeaveApplication.Status.PENDING);
		repo.create(application);
		ITable result = DbUnitUtil.getIDatabaseConnection().createQueryTable(
				"",
				"select count(*) from leave_applications where leave_application_id = '"
						+ idString + "'");
		assertEquals(java.math.BigInteger.ONE, result.getValue(0, "c1"));
	}

}
