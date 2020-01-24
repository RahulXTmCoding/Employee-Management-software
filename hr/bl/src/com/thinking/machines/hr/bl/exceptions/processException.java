package com.thinking.machines.hr.bl.exceptions;
public class processException extends Exception
{
private String message;
public processException(String message)
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