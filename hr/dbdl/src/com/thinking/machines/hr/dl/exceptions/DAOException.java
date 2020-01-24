package com.thinking.machines.hr.dl.exceptions;
public class DAOException extends Exception
{
private String message;
public DAOException(String message)
{
this.message=message;
}
public String toString()
{
return message;
}
public String getString()
{
return message;
}
 
}