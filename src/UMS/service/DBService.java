package UMS.service;

import UMS.dbHelper.DB;
import UMS.dto.*;
import UMS.util.MailUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

import java.sql.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;

import static UMS.util.AlertUtil.callAlert;

public class DBService {

    private Connection con;
    Alert alert = new Alert(Alert.AlertType.NONE);
    private static byte[] key;
    private static SecretKeySpec secretKey;
    private final String secret = "ssshhhhhhhhhhh!!!!";

    public DBService() {
        this.con = DB.getConnection();
    }

    @FXML
    public void addStock(ItemDTO itemDTO) {

        int count = 0;
        String selectQuery = "select * from item where iname = ? ";

        try {
            PreparedStatement pst = con.prepareStatement(selectQuery);
            pst.setString(1, itemDTO.getItemName());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
                System.out.println(count);
            }

            String updateCountQuery = "update item set count = ?"
                    + " where iname = ? ";
            pst = con.prepareStatement(updateCountQuery);
            pst.setInt(1, count + 1);
            pst.setString(2, itemDTO.getItemName());
            pst.executeUpdate();
            System.out.println("insertion done");
        } catch (Exception s) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + s);
            alert.show();
        }
        //order
        String orderquery = "INSERT INTO orders ( empid,orderdate,aname,iname,qty) VALUES (?, ?, ?,?,?)";
        try {

            PreparedStatement pst = con.prepareStatement(orderquery);

            pst.setString(1, "Order");
            pst.setDate(2, Date.valueOf(LocalDate.now()));
            pst.setString(3,"Item Added to Stock");
            pst.setString(4, itemDTO.getItemName());
            pst.setInt(5, 1);

            pst.executeUpdate();
        } catch (Exception s) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessful\n" + s);
            alert.show();
        }
    }

    public ObservableList<AddTableDTO> view(String gender, String cloth) {

        String viewQuery = "select * from item where iname like ?";
        double gTotal=0;
        double cost = 0;
        ObservableList<AddTableDTO> obList = FXCollections.observableArrayList();

        try {
            PreparedStatement pst = con.prepareStatement(viewQuery);
            if (!(gender.isEmpty()) && !(cloth.isEmpty()))
                pst.setString(1, gender.toUpperCase() + "_" + cloth.toUpperCase() + "%");
            else if ((gender.isEmpty()) && (cloth.isEmpty()))
                pst.setString(1, "%");
            else if (!(gender.isEmpty()) && (cloth.isEmpty()))
                pst.setString(1, gender.toUpperCase() + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                AddTableDTO addTableDTO=new AddTableDTO();
                addTableDTO.setItemName(rs.getString("iname"));
                addTableDTO.setItemCount(rs.getInt("count"));
                cost = Double.parseDouble(rs.getString("cost"))*Double.parseDouble(rs.getString("count"));
                addTableDTO.setCost(cost);
                gTotal+=cost;
                addTableDTO.setGrandTotal(gTotal);
                obList.add(addTableDTO);
            }

        } catch (Exception era) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + era);
            alert.show();
            return obList;
        }

        return obList;
    }

    public String getGrand(String gender, String cloth) {
        String viewquery = "select * from item where iname like ?";
        int gTotal=0;

        try {
            PreparedStatement pst = con.prepareStatement(viewquery);
            if (!(gender.isEmpty()) && !(cloth.isEmpty()))
                pst.setString(1, gender.toUpperCase() + "_" + cloth.toUpperCase() + "%");
            else if ((gender.isEmpty()) && (cloth.isEmpty()))
                pst.setString(1, "%");
            else if (!(gender.isEmpty()) && (cloth.isEmpty()))
                pst.setString(1, gender.toUpperCase() + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                gTotal+=Integer.parseInt(rs.getString("cost"))*Integer.parseInt(rs.getString("count"));

            }



        } catch (Exception era) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + era);
            alert.show();
            return "";
        }
        return gTotal+"";

    }

    public ObservableList<FindTableDTO> find(EmployeeDTO employeeDTO) {

        int itemCount = 0;
        String findQuerry = "SELECT * FROM orders WHERE empid = ?";
        ObservableList<FindTableDTO> obList = FXCollections.observableArrayList();

        try {
            PreparedStatement pST = con.prepareStatement(findQuerry);
            pST.setString(1, employeeDTO.getEmpID());
            ResultSet rs = pST.executeQuery();
            while (rs.next()) {
                itemCount++;
                obList.add(new FindTableDTO(rs.getString("empid"), itemCount,
                        rs.getString("iname"), rs.getString("qty"), rs.getDate("orderdate")));
            }
        } catch (SQLException era) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Not Found\n" + era);
            alert.show();
            return null;
        }

        return obList;
    }

    public AreaChart view(String type, Date startDate, Date endDate, String itemName, AreaChart areachart) {

        switch (type) {
            case "in": {
                XYChart.Series dataSeries1 = new XYChart.Series();
                dataSeries1.setName(itemName);
                if (itemName.contains("Count")) {
                    switch (itemName) {
                        case "Male Shirts Count": {
                            return getCount("MALE_SHIRT%", startDate, endDate, areachart, "in");
                        }
                        case "Male Pants Count": {
                            return getCount("MALE_PANT%", startDate, endDate, areachart, "in");
                        }
                        case "Male Tie Count": {
                            return getCount("MALE_TIE%", startDate, endDate, areachart, "in");
                        }
                        case "Male Blazer Count": {
                            return getCount("MALE_BLAZER%", startDate, endDate, areachart, "in");
                        }
                        case "Female Shirts Count": {
                            return getCount("FEMALE_SHIRT%", startDate, endDate, areachart, "in");
                        }
                        case "Female Pants Count": {
                            return getCount("FEMALE_PANT%", startDate, endDate, areachart, "in");
                        }
                        case "Female Tie Count": {
                            return getCount("FEMALE_TIE%", startDate, endDate, areachart, "in");
                        }
                        case "Female Blazer Count": {
                            return getCount("FEMALE_BLAZER%", startDate, endDate, areachart, "in");
                        }
                    }
                }
                else if (itemName.contains("Cost")) {
                    switch (itemName) {
                        case "Male Shirts Cost": {
                            return getCost("MALE_SHIRT%",startDate, endDate, areachart,"in");
                        }
                        case "Male Pants Cost": {
                            return getCost("MALE_PANT%",startDate, endDate, areachart,"in");
                        }
                        case "Male Tie Cost": {
                            return getCost("MALE_TIE%",startDate, endDate, areachart,"in");
                        }
                        case "Male Blazer Cost": {
                            return getCost("MALE_BLAZER%",startDate, endDate, areachart,"in");
                        }
                        case "Female Shirts Cost": {
                            return getCost("FEMALE_SHIRT%",startDate, endDate, areachart,"in");
                        }
                        case "Female Pants Cost": {
                            return getCost("FEMALE_PANT%",startDate, endDate, areachart,"in");
                        }
                        case "Female Tie Cost": {
                            return getCost("FEMALE_TIE%",startDate, endDate, areachart,"in");
                        }
                        case "Female Blazer Cost": {
                            return getCost("FEMALE_BLAZER%",startDate, endDate, areachart,"in");
                        }
                    }
                }
                else
                    return areachart;
            }
            case "out": {
                XYChart.Series dataSeries1 = new XYChart.Series();
                dataSeries1.setName(itemName);
                if (itemName.contains("Count")) {
                    switch (itemName) {
                        case "Male Shirts Count": {
                            return getCount("MALE_SHIRT%", startDate, endDate, areachart, "out");
                        }
                        case "Male Pants Count": {
                            return getCount("MALE_PANT%", startDate, endDate, areachart, "out");
                        }
                        case "Male Tie Count": {
                            return getCount("MALE_TIE%", startDate, endDate, areachart, "out");
                        }
                        case "Male Blazer Count": {
                            return getCount("MALE_BLAZER%", startDate, endDate, areachart, "out");
                        }
                        case "Female Shirts Count": {
                            return getCount("FEMALE_SHIRT%", startDate, endDate, areachart, "out");
                        }
                        case "Female Pants Count": {
                            return getCount("FEMALE_PANT%", startDate, endDate, areachart, "out");
                        }
                        case "Female Tie Count": {
                            return getCount("FEMALE_TIE%", startDate, endDate, areachart, "out");
                        }
                        case "Female Blazer Count": {
                            return getCount("FEMALE_BLAZER%", startDate, endDate, areachart, "out");
                        }
                    }
                } else if (itemName.contains("Cost")) {
                    switch (itemName) {
                        case "Male Shirts Cost": {
                            return getCost("MALE_SHIRT%", startDate, endDate, areachart,"out");
                        }
                        case "Male Pants Cost": {
                            return getCost("MALE_PANT%", startDate, endDate, areachart,"out");
                        }
                        case "Male Tie Cost": {
                            return getCost("MALE_TIE%", startDate, endDate, areachart,"out");
                        }
                        case "Male Blazer Cost": {
                            return getCost("MALE_BLAZER%", startDate, endDate, areachart,"out");
                        }
                        case "Female Shirts Cost": {
                            return getCost("FEMALE_SHIRT%", startDate, endDate, areachart,"out");
                        }
                        case "Female Pants Cost": {
                            return getCost("FEMALE_PANT%", startDate, endDate, areachart,"out");
                        }
                        case "Female Tie Cost": {
                            return getCost("FEMALE_TIE%", startDate, endDate, areachart,"out");
                        }
                        case "Female Blazer Cost": {
                            return getCost("FEMALE_BLAZER%", startDate, endDate, areachart,"out");
                        }
                    }
                }
                else
                    return areachart;
            }
            default:
                return new AreaChart(new NumberAxis(),new NumberAxis());
        }

    }

    AreaChart getCost(String item, Date startDate, Date endDate, AreaChart areaChart,String option){

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName(item);
        String viewquery="";

        if(option.equals("in"))
            viewquery = " Select o.orderdate,SUM(o.qty * i.Cost) as c from orders o  join item i  on o.IName = i.IName" +
                    " where  (orderdate between ? and ? ) " +
                    "and i.IName like ? " +
                    "and qty>0 "+
                    "group by orderdate";
        else if (option.equals("out"))
            viewquery = " Select o.orderdate,SUM(o.qty * i.Cost) as c from orders o  join item i  on o.IName = i.IName" +
                    " where  (orderdate between ? and ? ) " +
                    "and i.IName like ? " +
                    "and qty<0 "+
                    "group by orderdate";

        try {
            PreparedStatement pst = con.prepareStatement(viewquery);
            pst.setDate(1, startDate);
            pst.setDate(2, endDate);
            pst.setString(3, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataSeries1.getData().add(new XYChart.Data(rs.getString("orderdate"),
                        Math.abs(Integer.parseInt(rs.getString("c")))));
            }
            areaChart.getData().add(dataSeries1);
            return areaChart;
        } catch (SQLException era) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + era);
            alert.show();
            return null;
        }
    }

    AreaChart getCount(String item, Date startDate, Date endDate, AreaChart areaChart,String option){

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName(item);
        String viewquery="";
        if(option.equals("in"))
            viewquery = " Select orderdate,SUM(qty) as s from orders where (orderdate between ? and ?) " +
                    "and iname like ? " +
                    " and qty>0 " +
                    " group by orderdate ";
        else if (option.equals("out"))
            viewquery = " Select orderdate,SUM(qty) as s from orders where (orderdate between ? and ?) " +
                    "and iname like ? " +
                    " and qty<0 " +
                    " group by orderdate ";

        try {
            PreparedStatement pst = con.prepareStatement(viewquery);
            pst.setDate(1, startDate);
            pst.setDate(2, endDate);
            pst.setString(3, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dataSeries1.getData().add(new XYChart.Data(rs.getString("orderdate"), Math.abs(Integer.parseInt(rs.getString("s")))));
            }
            areaChart.getData().add(dataSeries1);
            return areaChart;
        } catch (SQLException era) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + era);
            alert.show();
            return null;
        }
    }

    public void report(String empID, int flag, Date sDate, Date eDate, String email) {
        String v1,v2,v3,v4,v5,sql="";
        Document pdfsup = new Document();               // creating the pdfs using itext
        Image logo = null;

        try {
            PdfWriter.getInstance(pdfsup, new FileOutputStream("D:\\UMS\\UMS\\src\\UMS\\assets\\Report.pdf"));
        } catch (Exception e) {
            callAlert(alert,e.getMessage());
        }
        pdfsup.open();
        try {
            logo = Image.getInstance("D:\\UMS\\UMS\\src\\UMS\\assets\\logo.jpg");
            logo.setAlignment(Element.ALIGN_CENTER);
        } catch (Exception e1) {
            callAlert(alert,e1.getMessage());
        }
        try {
            Paragraph paragraph = new Paragraph();
            paragraph.add(new java.util.Date().toString());
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            pdfsup.add(paragraph);
            pdfsup.add(new Paragraph("____________________________________________________________________________"));
            pdfsup.add(logo);
            Paragraph paragraph1 = new Paragraph();
            paragraph1.add("PILLAR SECURITY");
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            paragraph1.setSpacingAfter(15);
            paragraph1.setSpacingBefore(15);
            pdfsup.add(paragraph1);
        } catch (Exception e3) {
            callAlert(alert,e3.getMessage());
        }
        PdfPTable tablesup = new PdfPTable(5);
        PdfPCell cell = new PdfPCell(new Paragraph("Unit Report"));
        cell.setColspan(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.ORANGE);
        tablesup.addCell(cell);
        tablesup.addCell("Employee ID");
        tablesup.addCell("Order Date");
        tablesup.addCell("Issuer Name");
        tablesup.addCell("Item Name");
        tablesup.addCell("Quantity");
        try {
            if (flag == 0)
                sql = "Select * from orders where empid = ?";
            else if (flag == 1)
                sql = "Select * from orders where orderdate between ? and ?";
            PreparedStatement pst = con.prepareStatement(sql);
            if (flag == 0)
                pst.setString(1, empID);
            else if (flag == 1) {
                pst.setDate(1, sDate);
                pst.setDate(2, eDate);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                v1 = rs.getString("empid");
                v2 = rs.getString("orderdate");
                v3 = rs.getString("aname");
                v4 = rs.getString("iname");
                v5 = rs.getString("qty");
                tablesup.addCell(v1);
                tablesup.addCell(v2);
                tablesup.addCell(v3);
                tablesup.addCell(v4);
                tablesup.addCell(v5);
            }
            pdfsup.add(new Paragraph(""));
            pdfsup.add(new Paragraph(""));
            pdfsup.add(new Paragraph(""));
            pdfsup.add(tablesup);
            Paragraph paragraph3 = new Paragraph();
            paragraph3.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC, 2f));
            paragraph3.add("SIMRANJEET SINGH :)");
            paragraph3.setAlignment(Paragraph.ALIGN_RIGHT);
            pdfsup.add(paragraph3);
            pdfsup.close();

            if (Desktop.isDesktopSupported()) {
                try {
                    File file = new File("D:\\UMS\\UMS\\src\\UMS\\assets\\Report.pdf");
                    Desktop.getDesktop().open(file);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //default report receiver
            MailUtil.sendMail("simranjeet271@gmail.com", "Report");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Report Generated Successfully");
            alert.show();
            if (!(email.isEmpty())) {
                MailUtil.sendMail(email, "Report");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Report Generated Successfully");
                alert.show();
            }
        } catch (Exception e4) {
            callAlert(alert,e4.getMessage());
        }
    }

//    void insert(Utility utility) {
//
//        int count = 0;
//        String selectquery = "select * from item where iname = ? ";
//
//        try {
//            PreparedStatement pst = con.prepareStatement(selectquery);
//            pst.setString(1, utility.getName());
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                count = Integer.parseInt(rs.getString("count"));
//                System.out.println(count);
//            }
//
//            String updatecountquery = "update item set count = ?"
//                    + " where iname = ? ";
//            pst = con.prepareStatement(updatecountquery);
//            pst.setInt(1, count + utility.getCount());
//            pst.setString(2, utility.getName());
//            pst.executeUpdate();
//            System.out.println("insertion done");
//            alert.setAlertType(Alert.AlertType.INFORMATION);
//            alert.setContentText("Stock Added Successfully\n"+utility.getName());
//            alert.show();
//        } catch (Exception s) {
//            alert.setAlertType(Alert.AlertType.ERROR);
//            alert.setContentText("Task Unsuccessfully\n" + s);
//            alert.show();
//        }
//    }

    public void update(UpdateItemDTO updateItemDTO) {

        int count = 0;
        int mincount=0;
        String itemName = updateItemDTO.getItemName();
        int quantity = updateItemDTO.getQuantity();
        String empID = updateItemDTO.getEmpID();
        Date date = updateItemDTO.getDate();
        String providerName = updateItemDTO.getProviderName();
        String site = updateItemDTO.getSite();

        String selectquery = "select * from item where iname = ? ";
        try {
            PreparedStatement pst = con.prepareStatement(selectquery);
            pst.setString(1, itemName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
                mincount=Integer.parseInt(rs.getString("MinCount"));
            }

            String updatecountquery = "update item set count = ?" + " where iname = ? ";
            pst = con.prepareStatement(updatecountquery);
            int updatedCount = count - quantity;
            pst.setInt(1, updatedCount);
            pst.setString(2, itemName);
            pst.executeUpdate();
            System.out.println("count updation done");
            if (updatedCount < mincount){
                MailUtil.sendMail("simranjeet271@gmail.com", "Stock needed\n" + itemName + "\nItem left: "
                        + updatedCount+"\nOrder "+(mincount-updatedCount)+" more items to achieve Min"+
                        " " + "Count");

            }

        } catch (Exception s) {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + s);
            alert.show();
        }

        //order table

            String orderquery = "INSERT INTO orders ( empid,orderdate,aname,iname,qty) VALUES (?, ?, ?,?,?)";
            try {
                PreparedStatement pst = con.prepareStatement(orderquery);

                pst.setString(1, empID);
                pst.setDate(2, date);
                pst.setString(3, providerName);
                pst.setString(4, itemName);
                pst.setInt(5, quantity);

                pst.executeUpdate();
            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }

        //testing item
        String sitequery;
        if (itemName.contains("BLAZER")) {
            count = 0;
            String selectqueryB = "select * from site where sitename = ? ";
            try {
                //step 1
                PreparedStatement pst = con.prepareStatement(selectqueryB);
                pst.setString(1, site);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    count = Integer.parseInt(rs.getString("noofblazers"));
                    System.out.println(count);
                }
                sitequery = "update site set noofblazers = ? where sitename = ? ";
                pst = con.prepareStatement(sitequery);
                pst.setInt(1, count + quantity);
                pst.setString(2, site);
                pst.executeUpdate();
                System.out.println("site update done");
            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }
        } else if (itemName.contains("JACKET")) {
            count = 0;
            String selectqueryJ = "select * from site where sitename = ? ";
            try {
                //step 1
                PreparedStatement pst = con.prepareStatement(selectqueryJ);
                pst.setString(1, itemName);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    count = Integer.parseInt(rs.getString("noofjackets"));
                }
                sitequery = "update site set noofjackets = ? where sitename = ? ";
                pst = con.prepareStatement(sitequery);
                pst.setInt(1, count + quantity);
                pst.setString(2, site);
                pst.executeUpdate();
                System.out.println("site update done");
            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }
        }

    }

    public ObservableList<String> itemNames() {
        String viewquery = "select * from item ";
        ObservableList<String> names = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(viewquery);
            while (rs.next()) {
                names.add(rs.getString("IName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    public ObservableList<String> sitename() {
        String viewquery = "select * from site";
        ObservableList<String> names = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(viewquery);
            while (rs.next()) {
                names.add(rs.getString("SiteName"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return names;
    }

    public ObservableList<String> empidList() {
        String viewquery = "select * from employee ";
        ObservableList<String> names = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(viewquery);
            while (rs.next()) {
                names.add(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    public void resetItem(ItemDTO itemDTO) {

        if(itemDTO.getCost().isEmpty() && !itemDTO.getMinCount().isEmpty()){
            String selectquery = "UPDATE  item SET MinCount=?  WHERE IName=?";
            try {
                PreparedStatement pst = con.prepareStatement(selectquery);
                pst.setString(1, itemDTO.getMinCount());
                pst.setString(2, itemDTO.getItemName());
                pst.executeUpdate();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Item Reset Done");
                alert.show();

            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }}else if(!itemDTO.getCost().isEmpty() && itemDTO.getMinCount().isEmpty()){
            String selectquery = "UPDATE  item SET Cost=? WHERE IName=?";
            try {
                PreparedStatement pst = con.prepareStatement(selectquery);

                pst.setString(1, itemDTO.getCost());
                pst.setString(2, itemDTO.getItemName());
                pst.executeUpdate();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Item Reset Done");
                alert.show();



            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }}else if(!itemDTO.getCost().isEmpty() && !itemDTO.getMinCount().isEmpty()){
            String selectquery = "UPDATE  item SET MinCount=? , Cost=? WHERE IName=?";
            try {
                PreparedStatement pst = con.prepareStatement(selectquery);
                pst.setString(1, itemDTO.getMinCount());
                pst.setString(2, itemDTO.getCost());
                pst.setString(3, itemDTO.getItemName());
                pst.executeUpdate();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Item Reset Done");
                alert.show();



            } catch (Exception s) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + s);
                alert.show();
            }
        }}

    public void addEmployee(EmployeeDTO employeeDTO) {

            String selectquery = "insert into employee (empid,empname,phone,email,address,sitename) values" +
                    "(?,?,?,?,?,?)";
            try {
                PreparedStatement pst = con.prepareStatement(selectquery);
                pst.setString(1, employeeDTO.getEmpID());
                pst.setString(2, employeeDTO.getEmpName());
                pst.setString(3, employeeDTO.getPhoneNumber());
                pst.setString(4, employeeDTO.getEmail());
                pst.setString(5, employeeDTO.getAddress());
                pst.setString(6, employeeDTO.getSite());
                pst.executeUpdate();
                System.out.println("employee added");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Employee Added Successfully");
                alert.show();
            } catch (SQLException e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + e);
                alert.show();
            }
    }

    public void addSite(SiteDTO siteDTO) {

            String selectquery = "insert into site (sitename,address,noofblazers,noofjackets) values" +
                    "(?,?,?,?)";
            PreparedStatement pst = null;
            try {
                pst = con.prepareStatement(selectquery);
                pst.setString(1, siteDTO.getSite());
                pst.setString(2, siteDTO.getAddress());
                pst.setInt(3, 0);
                pst.setInt(4, 0);
                pst.executeUpdate();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Site Added Successfully");
                alert.show();
            } catch (SQLException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + ex);
                alert.show();
            }
    }

    public void addItem( ItemDTO itemDTO) {

            String selectquery = "insert into item (IName,MinCount,Cost,Count) values" +
                    "(?,?,?,?)";

            PreparedStatement pst = null;
            try {
                pst = con.prepareStatement(selectquery);
                pst.setString(1, itemDTO.getItemName());
                pst.setString(2, itemDTO.getMinCount());
                pst.setString(3, itemDTO.getCost());
                pst.setInt(4, 0);
                pst.executeUpdate();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Item Added Successfully");
                alert.show();
            } catch (SQLException ex) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Task Unsuccessfully\n" + ex);
                alert.show();
            }
    }

    private static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String hash(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public boolean forgot(UserDTO userDTO) {
        int rows = 0;
        try {
            String generatedPassword = hash(userDTO.getPassword(), secret);
            String updateQueryPre = "UPDATE  login SET password=? WHERE login=?";
            PreparedStatement pst = con.prepareStatement(updateQueryPre);
            pst.setString(1, generatedPassword);
            pst.setString(2, userDTO.getUsername());
            rows = pst.executeUpdate();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Reset Successful");
            alert.show();
            System.out.println("reset");
            if (rows != 0) {
                return true;
            }
        } catch (Exception s) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + s);
            alert.show();
            return false;
        }
        return false;
    }

    public boolean addUser(UserDTO userDTO) {
        try {
            String generatedPassword = hash(userDTO.getPassword(), secret);
            String addQueryPre = "INSERT INTO login (login,password) "

                    + " VALUES (?,?)";
            PreparedStatement pst = con.prepareStatement(addQueryPre);
            pst.setString(1, userDTO.getUsername());
            pst.setString(2, generatedPassword);
            pst.executeUpdate();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("User Added Successfully");
            alert.show();
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Task Unsuccessfully\n" + e);
            alert.show();
            return false;
        }
        return true;
    }


    public boolean match(UserDTO userDTO) {
        String database_password = "";
        String updateQueryPre = "SELECT * FROM login WHERE login=?";
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.prepareStatement(updateQueryPre);
            pst.setString(1, userDTO.getUsername());
            rs = pst.executeQuery();
            while (rs.next()) {
                database_password = rs.getString("Password");
                return database_password.equals(hash(userDTO.getPassword(), secret));
            }
        } catch (Exception s) {
            s.printStackTrace();
        }
        return false;
    }

//    public String getTotal() {
//        String viewquery = "select * from item ";
//        int gTotal=0;
//
//        try {
//            PreparedStatement pst = con.prepareStatement(viewquery);
//
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                gTotal+=Integer.parseInt(rs.getString("cost"))*Integer.parseInt(rs.getString("count"));
//
//            }
//
//
//
//        } catch (Exception era) {
//            alert.setAlertType(Alert.AlertType.ERROR);
//            alert.setContentText("Task Unsuccessfully\n" + era);
//            alert.show();
//            return "";
//        }
//        return gTotal+"";
//    }
}
