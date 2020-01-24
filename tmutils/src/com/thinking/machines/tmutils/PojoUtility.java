package com.thinking.machines.tmutils;
import java.lang.reflect.*;
public class PojoUtility
{
private PojoUtility(){}
public static void copy(Object target,Object source)
{
Class sourceClass=source.getClass();
Class targetClass=target.getClass();
Method [] sourceMethods=sourceClass.getDeclaredMethods();
Method [] targetMethods=targetClass.getDeclaredMethods();
Method setterMethod;
int setterMethodIndex;
for(Method sourceMethod:sourceMethods)
{
if(isGetter(sourceMethod))
{
setterMethodIndex=getSetterMethodIndex(targetMethods,sourceMethod);
if(setterMethodIndex==-1) continue;
setterMethod=targetMethods[setterMethodIndex];
invoke(target,setterMethod,source,sourceMethod);

}
}
}
private static boolean isGetter(Method method)
{
String name=method.getName();
if(name.length()<4) return false;
if(name.startsWith("get")==false) return false;
char m=name.charAt(3);
if(isAlphabet(m) && isLowerCase(m)) return false;
if(method.getParameterCount()!=0) return false;
Class returnType=method.getReturnType();
if(returnType.getName().equals("void")) return false;
return true;
}
private static boolean isAlphabet(char m)
{
return ((m>=97 && m<=122) || (m>=65 && m<=90));
}
private static boolean isLowerCase(char m)
{
return (m>=97 && m<=122);
}
private static int getSetterMethodIndex(Method [] targetMethods,Method sourceMethod)
{
int i;
Class returnType;
returnType=sourceMethod.getReturnType();
String setterName="set"+sourceMethod.getName().substring(3);
Class parameters[];
i=-1;
for(Method method:targetMethods)
{
i++;
if(method.getName().equals(setterName)==false ) continue;
parameters=method.getParameterTypes();
if(parameters.length!=1) continue;
if(parameters[0].getName().equals(returnType.getName())==false) continue;
return i;
}
return -1;
}
public static void invoke(Object targetObject,Method setterMethod,Object sourceObject,Method
sourceMethod)
{
try
{
sourceMethod.setAccessible(true);
setterMethod.setAccessible(true);
setterMethod.invoke(targetObject,sourceMethod.invoke(sourceObject));
}catch(Throwable t)
{
}
}
}