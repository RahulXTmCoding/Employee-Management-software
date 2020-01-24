package com.thinking.machines.hr.pl.exceptions;
public class ModelException extends Exception
{
private String message;
public ModelException(String message)
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