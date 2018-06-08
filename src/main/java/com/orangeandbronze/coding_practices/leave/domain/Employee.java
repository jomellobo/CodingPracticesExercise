package com.orangeandbronze.coding_practices.leave.domain;

public class Employee {
	private final int employeeId;
	private int vlCredits;
	private int slCredits;
	private Employee leaveApprover;

	public Employee(int employeeId, int vlCredits, int slCredits,
			Employee leaveApprover) {
		this.employeeId = employeeId;
		this.vlCredits = vlCredits;
		this.slCredits = slCredits;
		this.leaveApprover = leaveApprover;
	}

	public void cancel(LeaveApplication application) {
		application.cancel(this);		
	}
	
	public void approve(LeaveApplication application) {
		application.approve(this);
	}
	
	public void disapprove(LeaveApplication application) {
		application.disapprove(this);
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getLeaveCreditsOfType(LeaveType type) {
		if (type == LeaveType.SL) {
			return slCredits;
		} else {
			return vlCredits;
		}
	}
	
	public int getVlCredits() {
		return vlCredits;
	}

	public int getSlCredits() {
		return slCredits;
	}

	public Employee getLeaveApprover() {
		return leaveApprover;
	}
	
	@Override
	public String toString() {
		return "Employee #" + employeeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeId;
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
		Employee other = (Employee) obj;
		if (employeeId != other.employeeId)
			return false;
		return true;
	}

}
