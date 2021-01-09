package com.thinking.machines.inventory.pl;
import com.thinking.machines.inventory.pl.model.*;
import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import java.io.*;
import java.math.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.math.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
public class ItemPanel extends JPanel implements DocumentListener,ListSelectionListener,ActionListener
{
enum MODE{VIEW_MODE,ADD_MODE,EDIT_MODE,DELETE_MODE,EXPORT_TO__MODE};
private MODE mode;
private JLabel moduleTitle;
private JLabel searchCaptionLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel  searchErrorLabel;
private JTable table;
private JScrollPane jsp;
private ItemModel itemModel;
private ItemDetailsPanel itemDetailsPanel;
ItemPanel()
{
try{
itemModel=new ItemModel();
}catch(ValidationException ex)
{
String properties[]=new String[ex.size()];
properties=ex.getProperties();
for(int i=0;i<ex.size();i++)
{
JOptionPane.showMessageDialog(this,properties[i]+":"+ex.getException(properties[i]));
}
return;
}catch(ProcessException ex)
{
JOptionPane.showMessageDialog(this,ex.getMessage());
}
initComponents();
setAppearance();
addListeners();
setMode(MODE.VIEW_MODE);
itemDetailsPanel.setMode(MODE.VIEW_MODE);
}
private void initComponents()
{
moduleTitle=new JLabel("Items");
searchCaptionLabel=new JLabel("Search");
clearSearchTextFieldButton=new JButton(new ImageIcon("clear.png"));
searchErrorLabel=new JLabel(" ");
table=new JTable(itemModel);
searchTextField=new JTextField();
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
itemDetailsPanel=new ItemDetailsPanel();
setLayout(null);
int lm=5;
moduleTitle.setBounds(lm+5,5,400,50);
searchTextField.setBounds(lm+65,60,400,30);
searchCaptionLabel.setBounds(lm+5,60,60,30);
clearSearchTextFieldButton.setBounds(lm+467,60,30,30);
searchErrorLabel.setBounds(lm+405,40,75,20);
jsp.setBounds(lm+5,95,663,250);
itemDetailsPanel.setBounds(lm+5,350,663,250);
add(moduleTitle);
add(searchCaptionLabel);
add(searchTextField);
add(clearSearchTextFieldButton);
add(searchErrorLabel);
add(jsp);
add(itemDetailsPanel);
}

private void setAppearance()
{
Font moduleTitleFont=new Font("Verdana",Font.BOLD,20);
moduleTitle.setFont(moduleTitleFont);
Font font=new Font("Verdana",Font.PLAIN,16);
Font searchErrorLabelFont=new Font("Verdana",Font.BOLD,10);
searchCaptionLabel.setFont(font);
searchTextField.setFont(font);
searchErrorLabel.setFont(searchErrorLabelFont);
searchErrorLabel.setForeground(new Color(111,0,0));
table.setRowHeight(30);
table.setFont(font);
Font tableTitleFont=new Font("Verdana",Font.BOLD,16);
table.getTableHeader().setFont(tableTitleFont);
table.getColumnModel().getColumn(0).setPreferredWidth(100);
table.getColumnModel().getColumn(1).setPreferredWidth(560);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setResizingAllowed(false);
table.getTableHeader().setReorderingAllowed(false);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
table.getSelectionModel().addListSelectionListener(this);
clearSearchTextFieldButton.addActionListener(this);
}

private void search()
{
searchErrorLabel.setText("");
String searchWhat=searchTextField.getText().trim();
if(searchWhat.length()==0) return;
int index=itemModel.searchItem(searchWhat);
if(index==-1)
{
searchErrorLabel.setText("Not Found");
return;
}
else
{
searchErrorLabel.setText("");
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
ItemInterface selectedItem=itemModel.getItemAt(index);
itemDetailsPanel.setItem(selectedItem); 
}
}
public void setMode(MODE m)
{
if(m==MODE.ADD_MODE)
{
mode=m;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}

if(m==MODE.DELETE_MODE)
{
mode=m;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}

if(m==MODE.EXPORT_TO_PDF_MODE)
{
mode=m;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}

if(m==MODE.EDIT_MODE)
{
mode=m;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}

if(m==MODE.VIEW_MODE)
{
mode=m;
if(itemModel.getRowCount()==0)
{
searchTextField.setText("");
searchErrorLabel.setText("");
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
table.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
table.setEnabled(true);
}
}
}

public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchErrorLabel.setText("");
searchTextField.requestFocus(); 
}

public void valueChanged(ListSelectionEvent ev)
{
int selectedIndex=table.getSelectedRow();
if(selectedIndex<0)
{
itemDetailsPanel.setItem(null);
return;
}
ItemInterface selectedItem=itemModel.getItemAt(selectedIndex);
itemDetailsPanel.setItem(selectedItem);
}

public void insertUpdate(DocumentEvent ev)
{
search();
}

public void removeUpdate(DocumentEvent ev)
{
search();
}

public void changedUpdate(DocumentEvent ev)
{
search();
}

public class ItemDetailsPanel extends JPanel implements ActionListener 
{
private ItemInterface item;
private JLabel nameCaptionLabel;
private JTextField nameTextField;
private JLabel nameLabel;
private JButton clearNameTextFieldButton;
private JLabel priceCaptionLabel;
private JTextField priceTextField;
private JButton clearPriceTextFieldButton;
private JLabel priceLabel;
private JLabel categoryCaptionLabel;
private JLabel categoryLabel;
private ButtonGroup categoryGroup;
private JRadioButton finishedGoodsRadioButton;
private JRadioButton rawMaterialRadioButton;
private JRadioButton consumableRadioButton;
private JLabel typeCaptionLabel;
private JTextField typeTextField;
private JLabel typeLabel;
private JButton clearTypeTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;

ItemDetailsPanel()
{
initComponents();
setAppearance();
addListeners();
}
private void initComponents()
{
nameCaptionLabel=new JLabel("Item : ");
nameTextField=new JTextField();
nameLabel=new JLabel("");
clearNameTextFieldButton=new JButton(new ImageIcon("clear.png"));
priceCaptionLabel=new JLabel("Price : ");
priceTextField=new JTextField();
priceLabel=new JLabel("");
clearPriceTextFieldButton=new JButton(new ImageIcon("clear.png"));
categoryCaptionLabel=new JLabel("Category :");
categoryGroup=new ButtonGroup();
finishedGoodsRadioButton=new JRadioButton("Finished Goods");
rawMaterialRadioButton=new JRadioButton("Raw Material");
consumableRadioButton=new JRadioButton("Consumable");
categoryGroup.add(finishedGoodsRadioButton);
categoryGroup.add(rawMaterialRadioButton);
categoryGroup.add(consumableRadioButton);
categoryLabel=new JLabel("");
typeCaptionLabel=new JLabel("Type : ");
typeTextField=new JTextField();
typeLabel=new JLabel("");
clearTypeTextFieldButton=new JButton(new ImageIcon("clear.png"));
addButton=new JButton(new ImageIcon("add.png"));
editButton=new JButton(new ImageIcon("update.png"));
deleteButton=new JButton(new ImageIcon("delete.png"));
cancelButton=new JButton(new ImageIcon("cancel.png"));
exportToPDFButton=new JButton(new ImageIcon("pdf.png"));
setLayout(null);
int lm,tm;
lm=5;
tm=5;
nameCaptionLabel.setBounds(lm+5,tm+8,60,30);
nameTextField.setBounds(lm+5+60+5,tm+8,300,30);
nameLabel.setBounds(lm+5+60+5,tm+8,300,30);
clearNameTextFieldButton.setBounds(lm+5+60+5+300+2,tm+8,30,30);
	
priceCaptionLabel.setBounds(lm+5,tm+8+24+8,60,30);
priceTextField.setBounds(lm+5+60+5,tm+8+24+8,300,30);
priceLabel.setBounds(lm+5+60+5,tm+8+24+8,300,30);
clearPriceTextFieldButton.setBounds(lm+372,tm+8+24+8,30,30);

categoryCaptionLabel.setBounds(lm+5,tm+8+24+8+24+8+24+8,100,30);
finishedGoodsRadioButton.setBounds(lm+5+100+5,tm+8+24+8+24+8+24+8,150,30);
rawMaterialRadioButton.setBounds(lm+5+100+5+150+5,tm+8+24+8+24+8+24+8,140,30);
consumableRadioButton.setBounds(lm+5+100+5+150+5+140+5,tm+8+24+8+24+8+24+8,150,30);
categoryLabel.setBounds(lm+5+100,tm+8+24+8+24+8+24+8,350,30);

typeCaptionLabel.setBounds(lm+5,tm+8+24+8+24+8,60,30);
typeTextField.setBounds(lm+5+60+5,tm+8+24+8+24+8,300,30);
typeLabel.setBounds(lm+5+60+5,tm+8+24+8+24+8,300,30);
clearTypeTextFieldButton.setBounds(lm+372,tm+8+24+8+24+8,30,30);

JPanel p1=new JPanel();
p1.setLayout(null);
p1.setBorder(BorderFactory.createLineBorder(Color.gray));
p1.setBounds(663/2-310/2,tm+8+24+8+24+8+24+8+24+8+20,310,70);
p1.setLayout(null);
addButton.setBounds(10,10,50,50);
editButton.setBounds(70,10,50,50);
deleteButton.setBounds(130,10,50,50);
cancelButton.setBounds(190,10,50,50);
exportToPDFButton.setBounds(250,10,50,50);
p1.add(addButton);
p1.add(editButton);
p1.add(deleteButton);
p1.add(cancelButton);
p1.add(exportToPDFButton);
add(nameCaptionLabel);
add(nameTextField);
add(clearNameTextFieldButton);
add(nameLabel);

add(priceCaptionLabel);
add(priceTextField);
add(clearPriceTextFieldButton);
add(priceLabel);

add(categoryCaptionLabel);
add(finishedGoodsRadioButton);
add(rawMaterialRadioButton);
add(consumableRadioButton);
add(categoryLabel);

add(typeCaptionLabel);
add(typeTextField);
add(clearTypeTextFieldButton);
add(typeLabel);

add(p1);
}
private void setAppearance()
{
setBorder(BorderFactory.createLineBorder(Color.gray));
Font font=new Font("Verdana",Font.PLAIN,16);
nameCaptionLabel.setFont(font);
nameLabel.setFont(font);
nameTextField.setFont(font);
priceCaptionLabel.setFont(font);
priceLabel.setFont(font);
priceTextField.setFont(font);
categoryCaptionLabel.setFont(font);
finishedGoodsRadioButton.setFont(font);
rawMaterialRadioButton.setFont(font);
consumableRadioButton.setFont(font);
categoryLabel.setFont(font);
typeCaptionLabel.setFont(font);
typeLabel.setFont(font);
typeTextField.setFont(font);
}

private void addListeners()
{
addButton.addActionListener(this);
editButton.addActionListener(this);
cancelButton.addActionListener(this);
deleteButton.addActionListener(this);
exportToPDFButton.addActionListener(this);
clearTypeTextFieldButton.addActionListener(this);
clearPriceTextFieldButton.addActionListener(this);
clearNameTextFieldButton.addActionListener(this);
}

public void setMode(MODE m)
{
if(m==MODE.ADD_MODE)
{
mode=m;
nameTextField.setText("");
priceTextField.setText("");
finishedGoodsRadioButton.setSelected(false);
rawMaterialRadioButton.setSelected(false);
consumableRadioButton.setSelected(false);
categoryGroup.clearSelection();
typeTextField.setText("");
nameLabel.setVisible(false);
priceLabel.setVisible(false);
categoryLabel.setVisible(false);
typeLabel.setVisible(false);
nameTextField.setVisible(true);
clearNameTextFieldButton.setVisible(true);
priceTextField.setVisible(true);
clearPriceTextFieldButton.setVisible(true);
finishedGoodsRadioButton.setVisible(true);
rawMaterialRadioButton.setVisible(true);
consumableRadioButton.setVisible(true);
typeTextField.setVisible(true);
clearTypeTextFieldButton.setVisible(true);
addButton.setIcon(new ImageIcon("save.png"));
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

if(m==MODE.DELETE_MODE)
{
mode=m;
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

if(m==MODE.EXPORT_TO_PDF_MODE)
{
mode=m;
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}


if(m==MODE.EDIT_MODE)
{
mode=m;
nameTextField.setText(this.item.getName());
priceTextField.setText(String.valueOf(this.item.getPrice()));
finishedGoodsRadioButton.setSelected(false);
rawMaterialRadioButton.setSelected(false);
consumableRadioButton.setSelected(false);
typeTextField.setText(this.item.getType());
if(item.getCategory().equals("F")) finishedGoodsRadioButton.setSelected(true);
if(item.getCategory().equals("C")) consumableRadioButton.setSelected(true);
if(item.getCategory().equals("R")) rawMaterialRadioButton.setSelected(true);
nameLabel.setVisible(false);
priceLabel.setVisible(false);
categoryLabel.setVisible(false);
typeLabel.setVisible(false);
nameTextField.setVisible(true);
clearNameTextFieldButton.setVisible(true);
priceTextField.setVisible(true);
clearPriceTextFieldButton.setVisible(true);
finishedGoodsRadioButton.setVisible(true);
rawMaterialRadioButton.setVisible(true);
consumableRadioButton.setVisible(true);
typeTextField.setVisible(true);
clearTypeTextFieldButton.setVisible(true);
editButton.setIcon(new ImageIcon("save.png"));
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

if(m==MODE.VIEW_MODE)
{
mode=m;
nameTextField.setVisible(false);
clearNameTextFieldButton.setVisible(false);
nameLabel.setVisible(true);
priceTextField.setVisible(false);
clearPriceTextFieldButton.setVisible(false);
priceLabel.setVisible(true);
finishedGoodsRadioButton.setVisible(false);
rawMaterialRadioButton.setVisible(false);
consumableRadioButton.setVisible(false);
categoryLabel.setVisible(true);
cancelButton.setEnabled(false);
typeTextField.setVisible(false);
clearTypeTextFieldButton.setVisible(false);
typeLabel.setVisible(true);
addButton.setIcon(new ImageIcon("add.png"));
editButton.setIcon(new ImageIcon("edit.png"));
addButton.setEnabled(true);
if(itemModel.getRowCount()==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
}
}
public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==clearTypeTextFieldButton)
{
typeTextField.setText("");
typeTextField.requestFocus();
}
if(ev.getSource()==clearPriceTextFieldButton)
{
priceTextField.setText("");
priceTextField.requestFocus();
}
if(ev.getSource()==clearNameTextFieldButton)
{
nameTextField.setText("");
nameTextField.requestFocus();
}

if(ev.getSource()==addButton)
{
if(mode==MODE.VIEW_MODE)
{
this.setMode(MODE.ADD_MODE);
ItemPanel.this.setMode(MODE.ADD_MODE);
}
else
{
String name=nameTextField.getText().trim();
if(name.length()==0)
{
JOptionPane.showMessageDialog(this,"Name required");
nameTextField.requestFocus();
return;
}
String price=priceTextField.getText().trim();
if(price.length()==0)
{
JOptionPane.showMessageDialog(this,"Price required");
priceTextField.requestFocus();
return;
}
try
{
Integer.parseInt(price);
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Price should be numeric");
priceTextField.requestFocus();
return;
}
if(finishedGoodsRadioButton.isSelected()==false && rawMaterialRadioButton.isSelected()==false && consumableRadioButton.isSelected()==false)
{
JOptionPane.showMessageDialog(this,"Category required");
return;
}
String type=typeTextField.getText().trim();
if(type.length()==0)
{
JOptionPane.showMessageDialog(this,"type required");
typeTextField.requestFocus();
return;
}
ItemInterface item=new Item();
item.setName(name);
item.setPrice(new BigDecimal(price));
if(finishedGoodsRadioButton.isSelected()) item.setCategory("F");
if(rawMaterialRadioButton.isSelected()) item.setCategory("R");
if(consumableRadioButton.isSelected()) item.setCategory("C");
item.setType(type);
try
{
itemModel.addItem(item);
}catch(ValidationException modelException)
{
String properties[]=new String[modelException.size()];
properties=modelException.getProperties();
for(int i=0;i<modelException.size();i++)
{
JOptionPane.showMessageDialog(this,properties[i]+":"+modelException.getException(properties[i]));
}
return;
}catch(ProcessException ex)
{
JOptionPane.showMessageDialog(this,ex.getMessage());
}

int index=itemModel.searchItem(name);
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
}
}

if(ev.getSource()==deleteButton)
{
int selectedIndex=table.getSelectedRow();
if(selectedIndex<0)
{
JOptionPane.showMessageDialog(this,"Select an item to delete");
return;
}
this.setMode(MODE.DELETE_MODE);
ItemPanel.this.setMode(MODE.DELETE_MODE);
int selectedOption;
selectedOption=JOptionPane.showConfirmDialog(this,"Delete : "+this.item.getName(),"Delete Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.YES_OPTION)
{
try
{
String name=this.item.getName();
itemModel.deleteItem(this.item.getCode());
this.setItem(null);
JOptionPane.showMessageDialog(this,"Item : "+name+" deleted");
}catch(ValidationException ex)
{
String properties[]=new String[ex.size()];
properties=ex.getProperties();
for(int i=0;i<ex.size();i++)
{
JOptionPane.showMessageDialog(this,properties[i]+":"+ex.getException(properties[i]));
}
return;
}catch(ProcessException ex)
{
JOptionPane.showMessageDialog(this,ex.getMessage());
}
}
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
}

if(ev.getSource()==cancelButton)
{
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
}

if(ev.getSource()==editButton)
{
if(mode==MODE.VIEW_MODE)
{
int selectedRowIndex=table.getSelectedRow();
if(selectedRowIndex<0)
{
JOptionPane.showMessageDialog(this,"Select an item to edit");
return;
}
this.setMode(MODE.EDIT_MODE);
ItemPanel.this.setMode(MODE.EDIT_MODE);
}
else
{
int selectedRowIndex=table.getSelectedRow();
String name=nameTextField.getText().trim();
if(name.length()==0)
{
JOptionPane.showMessageDialog(this,"Name required");
nameTextField.requestFocus();
return;
}
String type=typeTextField.getText().trim();
if(type.length()==0)
{
JOptionPane.showMessageDialog(this,"Type required");
typeTextField.requestFocus();
return;
}
String price=priceTextField.getText().trim();
if(price.length()==0)
{
JOptionPane.showMessageDialog(this,"Price required");
priceTextField.requestFocus();
return;
}
try
{
Integer.parseInt(price);
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Price should be numeric");
priceTextField.requestFocus();
return;
}
if(finishedGoodsRadioButton.isSelected()==false && rawMaterialRadioButton.isSelected()==false && consumableRadioButton.isSelected()==false)
{
JOptionPane.showMessageDialog(this,"Category required");
return;
}
ItemInterface item=new Item();
item.setCode(this.item.getCode());
item.setName(name);
item.setPrice(new BigDecimal(price));
item.setType(type);
if(finishedGoodsRadioButton.isSelected()) item.setCategory("F");
if(rawMaterialRadioButton.isSelected()) item.setCategory("R");
if(consumableRadioButton.isSelected()) item.setCategory("C");
try
{
itemModel.editItem(item,selectedRowIndex);
}catch(ValidationException ex)
{
String properties[]=new String[ex.size()];
properties=ex.getProperties();
for(int i=0;i<ex.size();i++)
{
JOptionPane.showMessageDialog(this,properties[i]+":"+ex.getException(properties[i]));
}
return;
}catch(ProcessException ex)
{
JOptionPane.showMessageDialog(this,ex.getMessage());
}
int index=itemModel.searchItem(name);
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,false)));
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
}
}

if(ev.getSource()==exportToPDFButton)
{
this.setMode(MODE.EXPORT_TO_PDF_MODE);
ItemPanel.this.setMode(MODE.EXPORT_TO_PDF_MODE);
JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("PDF Files","pdf");
jfc.addChoosableFileFilter(fileFilter);
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(ItemPanel.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File file=jfc.getSelectedFile();
String fullPath=file.getAbsolutePath();
File parent=new File(file.getParent());
if(parent.exists()==false)
{
JOptionPane.showMessageDialog(this,"Invalid path : "+fullPath);
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
return;
}
if(parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(this,parent.getAbsolutePath()+" is not a folder");
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
return;
}
if(fullPath.endsWith(".pdf")==false)
{
if(fullPath.endsWith(".")) fullPath+="pdf";
else fullPath+=".pdf";
}
try
{
com.itextpdf.text.Document document=new com.itextpdf.text.Document();
com.itextpdf.text.pdf.PdfWriter pdfWriter=com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream(fullPath));
document.open();
com.itextpdf.text.Paragraph p=new com.itextpdf.text.Paragraph();
int k=itemModel.getRowCount();
int pageNumber=0;
int pageSize=40;
boolean newPage=true;
com.itextpdf.text.Image logo;
com.itextpdf.text.pdf.PdfPTable table=new com.itextpdf.text.pdf.PdfPTable(5);
com.itextpdf.text.Paragraph paragraph;
com.itextpdf.text.Font firmNameFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,16,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font titleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,14,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font columnTitleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font dataFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.NORMAL);
String firmName="ABC Retailers";
ItemInterface item;
int x=0;
while(x<k)
{
if(newPage)
{
logo=com.itextpdf.text.Image.getInstance("retailManager.png");
logo.setAbsolutePosition(100.0f,50.0f);
document.add(logo);
paragraph=new com.itextpdf.text.Paragraph(firmName,firmNameFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

paragraph=new com.itextpdf.text.Paragraph("Items",titleFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

table.addCell("S. No");
table.addCell("Item");
table.addCell("Price");
table.addCell("Type");
table.addCell("Category");
pageNumber++;
newPage=false;
}
try
{
item=itemModel.getItemAt(x);
table.addCell(""+(x+1));
table.addCell(item.getName());
table.addCell(item.getPrice()+"");
table.addCell(item.getType());
table.addCell(item.getCategory());
}catch(Exception ex)
{
JOptionPane.showMessageDialog(this,ex.getMessage());
}
x++;
if(x%pageSize==0 || x==k)
{
document.add(table);
paragraph=new com.itextpdf.text.Paragraph("XYZ");
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
document.add(paragraph);
if(x!=k)
{
document.newPage();
newPage=true;
}
}
}
document.close();
JOptionPane.showMessageDialog(this,fullPath+"  created");
}catch(Throwable t)
{
System.out.println(t.getMessage());
JOptionPane.showMessageDialog(this,"Cannot create pdf, contact administrator");
}
// code to export data to pdf ends here
}
this.setMode(MODE.VIEW_MODE);
ItemPanel.this.setMode(MODE.VIEW_MODE);
}
}
public void setItem(ItemInterface item)
{
this.item=item;
if(this.item==null)
{
nameLabel.setText("");
priceLabel.setText("");
categoryLabel.setText("");
typeLabel.setText("");
}
else
{
nameLabel.setText(this.item.getName());
priceLabel.setText(String.valueOf(this.item.getPrice()));
String category=this.item.getCategory();
if(category.equals("F"))
{
categoryLabel.setText("Finished Goods");
}
if(category.equals("R"))
{
categoryLabel.setText("Raw Material");
}
if(category.equals("C"))
{
categoryLabel.setText("Consumable");
}
typeLabel.setText(this.item.getType());
}
}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
}