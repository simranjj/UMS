# UMS
Unit Management System
<p align="center">
 

## Table of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [Important](#important)
* [Installation](#installation)
* [Acknowledgements](#acknowledgements)


## About The Project

This project is to manage items and generate ad-hoc reports for a company.

Features :
* More than just managing the stocks.
* Secured Access through Login/Password. 
* Dashboard gives you details like total count of general items, count of a particular item and visual representation of the category of item.
* Find tab: Search for items issued/returned to employees or buildings using an auto-fill text field and displays data in a tabular form.
* Visualization tab: Displays data of different categories in a piechart form, when mouse hovered over a section of a piechart it displays another piechart giving cost information for each item in that category.
* Report Tab: Reports of items issued/returned can be generated by autofill text fields based on employee number, the site name or between two specific dates. The report generated will be sent to the admin as an email and will open the report in a pdf viewer. Users can also specify an email id to whom you want to send it.
* StockItem Tab: Item scanned by a barcode scanner automatically gets stored in the database. Detail for each item can be seen in a table format. The GenerateBarCode button will use a printer to print out a barcode for an item. BarCode stores the detailed information of the item like gender, type, size and colour.
* IsueReturn tab: Uses a barcode scanner to scan the barcode on the item and automatically removes it from the database. Sends an email to the admin if an item count goes below its minimum count in inventory. Manage the stock. 
* AddRecord tab: Adds a new item, employee, site, change minimum count for an item, changes the prices of the item. 
* Create/Reset User: Creates new login and resets the password.


### Built With
Here are the libraries that I have used for this project. 
* [barcode4j](http://repo2.maven.org/maven2/net/sf/barcode4j/barcode4j/2.1)
* [controlsfx/8.40.14](http://central.maven.org/maven2/org/controlsfx/controlsfx/8.40.14)
* [itextpdf-5.4.0](http://www.java2s.com/Code/Jar/i/Downloaditextpdf540jar.htm)
* [javax.mail](https://javaee.github.io/javamail/)
* [mysql_jdbc_7_4_1_jre8](https://jar-download.com/artifacts/com.microsoft.sqlserver/mssql-jdbc)


## Important
```
Use Java 1.8 because of the library compatibility issues
```


### Installation

Clone the repo
```
git clone https://github.com/simranjj/UMS.git
```


## Acknowledgements
* [Google][www.google.com]
* [StackOverflow][www.stackoverfow.com]
* [TutorialsPoint][www.tutorialspoint.net]
* [J2EE][http://www.java2s.com/]
* [Microsoft SQL Server][https://www.microsoft.com/en-ca/sql-server/sql-server-downloads]

