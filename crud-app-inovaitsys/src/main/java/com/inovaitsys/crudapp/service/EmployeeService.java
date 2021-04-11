package com.inovaitsys.crudapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.inovaitsys.crudapp.exception.ResourceNotFoundException;
import com.inovaitsys.crudapp.model.Employee;
import com.inovaitsys.crudapp.model.Error;
import com.inovaitsys.crudapp.model.Response;
import com.inovaitsys.crudapp.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository empRepository;

	public ResponseEntity<Response> saveEmployee(@RequestBody Employee employee) {
		empRepository.save(employee);
		List<Employee> emp = new ArrayList<>();
		emp.add(employee);
		Response response = new Response(emp);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	public ResponseEntity<Response> getAllEmployees() {
		List<Employee> emp = empRepository.findAll();
		Response response = new Response(emp);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	public ResponseEntity<Response> getEmployeeById(int id) {
		List<Employee> emp = new ArrayList<>();
		Employee employee = null;

		try {

			employee = empRepository.findById(id).<ResourceNotFoundException>orElseThrow(
					() -> new ResourceNotFoundException("Employee not found for this id : " + id));

		} catch (ResourceNotFoundException e) {

			com.inovaitsys.crudapp.model.Error error = new Error("Resource not found", e.getMessage());
			Response response = new Response(error);

			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}

		emp.add(employee);
		Response response = new Response(emp);

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	public ResponseEntity<Response> deleteEmployeeById(int id) {
		empRepository.deleteById(id);
		Response response = new Response(empRepository.findAll());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	public ResponseEntity<Response> updateEmployeeDetails(int id, @RequestBody Employee employee) {
		List<Employee> employeelist = new ArrayList<>();
		Employee emp = null;

		try {
			emp = empRepository.findById(id)// find employee by id
					.<ResourceNotFoundException>orElseThrow(
							() -> new ResourceNotFoundException("Employee not found for this id : " + id));

		} catch (ResourceNotFoundException e) {
			com.inovaitsys.crudapp.model.Error error = new Error("Resource not found", e.getMessage());
			Response response = new Response(error);

			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);

		}

		emp.setFname(employee.getFname());
		emp.setLname(employee.getLname());
		emp.setDob(employee.getDob());
		emp.setNic(employee.getNic());

		empRepository.save(emp);
		employeelist.add(emp);
		Response response = new Response(employeelist);

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
