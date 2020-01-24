package com.thinking.machines.hr.pl.ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EmployeeFrame extends JFrame
{
private EmployeePanel ep;
private Container c;

public EmployeeFrame()
{

initComponent();
setApperiance();
addListener();
}
private void initComponent()
{
ep=new EmployeePanel();
c=getContentPane();
}
private void setApperiance()
{
c.add(ep);
setSize(500,650);
ImageIcon img=new ImageIcon("C://uec20198am//hr//pl//icons//title1.png");
setIconImage(img.getImage());
setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
setLocation(100,20);
}
private void addListener()
{
}
}