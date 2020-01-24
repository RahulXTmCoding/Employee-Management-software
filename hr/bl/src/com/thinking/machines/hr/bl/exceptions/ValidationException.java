package com.thinking.machines.hr.bl.exceptions;
import java.util.*;
import java.io.*;
public class ValidationException extends Exception
{
private Map<String,String> map=new HashMap<String,String>();
public void addExceptions(String parameter,String message)
{
map.put(parameter,message);
}
public void delete(String parameter)
{
map.remove(parameter);
}
public void deleteAll()
{
map.clear();
}
public boolean hasException()
{
return map.size()>0;
}
public int ExceptionSize()
{
return map.size();
}
public List<String> listOfParameters()
{
return new ArrayList<String>(map.keySet());
}
public String getByparameter(String parameter)
{
return map.get(parameter);
}
}