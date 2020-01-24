package com.thinking.machines.hr.dl.interfaces;
import java.math.*;
public interface EmployeeDTOInterface extends java.io.Serializable,java.lang.Comparable<EmployeeDTOInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
public void setGender(String Gender);
public String getGender();
public void setSalary(BigDecimal salary);
public BigDecimal getSalary();
public void setPanNumber(String pan);
public String getPanNumber();
public boolean equals(Object other);
public int hashcode();
}