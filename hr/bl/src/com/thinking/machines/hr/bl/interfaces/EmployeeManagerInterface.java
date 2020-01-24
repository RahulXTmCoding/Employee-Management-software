package com.thinking.machines.hr.bl.interfaces;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.bl.exceptions.*;

public interface EmployeeManagerInterface 
{
public enum OrderedBy{CODE,NAME,GENDER,SALARY,PAN_NUMBER};
public static final OrderedBy CODE=OrderedBy.CODE;
public static final OrderedBy NAME=OrderedBy.NAME;
public static final OrderedBy GENDER=OrderedBy.GENDER;
public static final OrderedBy SALARY=OrderedBy.SALARY;
public static final OrderedBy PAN_NUMBER=OrderedBy.PAN_NUMBER;
public void add(EmployeeInterface e) throws ValidationException,processException;
public boolean hasEmployee();
public int getCount();
public void delete(int code) throws ValidationException,processException;
public void update(EmployeeInterface e)throws ValidationException,processException;
public EmployeeInterface getByCode(int code) throws ValidationException;
public EmployeeInterface getByPanNumber(String panNumber) throws ValidationException;
public List<EmployeeInterface> getOrderedBy(OrderedBy order);
public void deleteAll() throws processException;
}