package com.orangeandbronze.coding_practices.leave.domain;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.*;
import java.util.*;

import com.orangeandbronze.coding_practices.leave.exceptions.*;

public class LeaveApplication {

	public enum Status {
		PENDING, APPROVED, DISAPPROVED, CANCELED
	}

	private final UUID leaveApplicationId;
	private final Employee filer;
	private final Employee approver;
	private final LeaveType type;
	private final LocalDate start;
	private final LocalDate end;
	private Status status;
	private static int leaveDays = 0;

	static LeaveApplication newLeaveApplication(Employee filer,
			Employee approver, LeaveType type, LocalDate start, LocalDate end) {
		return new LeaveApplication(UUID.randomUUID(), filer, approver, type,
				start, end, Status.PENDING);
	}

	public LeaveApplication(UUID leaveApplicationId, Employee filer,
			Employee approver, LeaveType type, LocalDate start, LocalDate end,
			Status status) {

		if (end.isBefore(start)) {
			throw new IllegalArgumentException(
					"End date before start date: Start: " + start + " End: "
							+ end);
		}

		this.leaveApplicationId = leaveApplicationId;
		this.filer = filer;
		this.approver = approver;
		this.type = type;
		this.start = start;
		this.end = end;
		this.status = status;
		
	}

	public UUID getLeaveApplicationId() {
		return leaveApplicationId;
	}

	void approve(Employee approver) {
		checkIfRightApprover(approver);
		status = Status.APPROVED;
	}

	void disapprove(Employee approver) {
		checkIfRightApprover(approver);
		status = Status.DISAPPROVED;
	}

	private void checkIfRightApprover(Employee approver) {
		if (!this.approver.equals(approver)) {
			throw new WrongApproverException("Required approver is "
					+ this.approver + " but approver submitted was " + approver
					+ '.');
		}
	}

	void cancel(Employee filer) {
		if (!this.filer.equals(filer)) {
			throw new WrongFilerException("Leave application filer is "
					+ this.filer + " but employee trying to cancel was "
					+ filer + '.');
		}
		status = status.CANCELED;
	}

	public Employee getFiler() {
		return filer;
	}

	public Employee getApprover() {
		return approver;
	}

	public LeaveType getType() {
		return type;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public Status getStatus() {
		return status;
	}

	public static int getLeaveDays(LocalDate start, LocalDate end) {
		/*
		 * TODO Lots of business code down here. Can we push this into one of
		 * the domain classes? Which one would be appropriate?
		 */
		for (LocalDate currentDate = start; currentDate.isBefore(end)
				|| currentDate.isEqual(end); currentDate = currentDate
				.plusDays(1)) {

			// TODO Is there a way to make this code easier to read?
			// skip weekends
			if (isWeekend(currentDate) || Holiday.isHoliday(currentDate)) {
				continue;
			}
			
			// increase count of leave days
			 leaveDays++;
		}
		
		return leaveDays;
	}
	@Override
	public String toString() {
		return "LeaveApplication [filer=" + filer + ", approver=" + approver
				+ ", type=" + type + ", start=" + start + ", end=" + end
				+ ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((leaveApplicationId == null) ? 0 : leaveApplicationId
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveApplication other = (LeaveApplication) obj;
		if (leaveApplicationId == null) {
			if (other.leaveApplicationId != null)
				return false;
		} else if (!leaveApplicationId.equals(other.leaveApplicationId))
			return false;
		return true;
	}
	

	private static boolean isWeekend(LocalDate date) {
		if (date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY) {
			return true;
		}
		return false;

	}
	
}
