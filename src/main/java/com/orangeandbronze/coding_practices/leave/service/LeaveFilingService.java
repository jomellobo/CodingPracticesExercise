package com.orangeandbronze.coding_practices.leave.service;

import static java.time.DayOfWeek.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.orangeandbronze.coding_practices.leave.domain.*;
import com.orangeandbronze.coding_practices.leave.domain.LeaveApplication.Status;
import com.orangeandbronze.coding_practices.leave.exceptions.*;
import com.orangeandbronze.coding_practices.leave.repositories.*;

/* TODO Notice that all the methods have to declare 'throws SQLException' since that's what the repository code throws. 
 * This breaks Single Responsibility Principle since a service should not be aware of any persistence details. The service
 * is now tightly coupled to the implementation of the Repositories. If the Repositories are changed to use something other
 * than JDBC, then all the 'throws SQLException' code needs to be changed as well.
 * Implement exception translation on the Repositories so that we no longer need to declare 'throws SQLException' in
 * the Service classes. Remove the 'throws SQLException' declarations after you have implemented exception translation. */
public class LeaveFilingService {

	private EmployeeRepository employeeRepo = new EmployeeRepository();
	private LeaveApplicationRepository leaveApplicationRepo = new LeaveApplicationRepository();

	/** For simplicity, only whole-day leaves are supported. No half-day leaves. **/
	public void fileLeave(int filerEmployeeId, LeaveType leaveType,
			LocalDate start, LocalDate end){
		if (end.isBefore(start)) {
			throw new IllegalArgumentException(
					"End date before start date: Start: " + start + " End: "
							+ end);
		}
		Employee filer = employeeRepo.findById(filerEmployeeId);

		// check if filer has enough credits
		int availableLeaveCredits = filer.getLeaveCreditsOfType(leaveType);
		if (availableLeaveCredits <  LeaveApplication.getLeaveDays(start, end)) {
			throw new InsufficientLeaveCreditsException("Employee " + filer
					+ " tried to file " + LeaveApplication.getLeaveDays(start, end) + " days leave of type "
					+ leaveType + " but the employee only has "
					+ availableLeaveCredits + ' ' + leaveType
					+ " leave credits.");
		}
		LeaveApplication newLeaveApplication = new LeaveApplication(
				UUID.randomUUID(), filer, filer.getLeaveApprover(), leaveType,
				start, end, Status.PENDING);
		leaveApplicationRepo.create(newLeaveApplication);
	}

	public Collection<LeaveApplication> getPendingLeaveApplications(
			int filerEmployeeId){
		return leaveApplicationRepo
				.findPendingByFilerEmployeeId(filerEmployeeId);
	}

	public void cancelLeaveApplication(int filerEmployeeId,
			UUID leaveApplicationId){
		Employee filer = employeeRepo.findById(filerEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		filer.cancel(application);
		leaveApplicationRepo.updateStatus(application);
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public void setLeaveApplicationRepository(
			LeaveApplicationRepository leaveApplicationRepo) {
		this.leaveApplicationRepo = leaveApplicationRepo;
	}

}
