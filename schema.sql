create table employees (
	employee_id int primary key,
	vl_credits int,
	sl_credits int,
	approver_employee_id int,
	FOREIGN KEY (approver_employee_id) REFERENCES employees (employee_id)
);

create table leave_applications (
	leave_application_id char(36) primary key,
	filer_employee_id int,
	approver_employee_id int,
	leave_type varchar(2),
	start_date date,
	end_date date,
	status varchar(10),
	foreign key (filer_employee_id) references employees (employee_id),
	foreign key (approver_employee_id) references employees (employee_id)
);