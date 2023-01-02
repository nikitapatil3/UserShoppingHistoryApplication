<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>
<%
    String cid = request.getParameter("cid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String connString = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = null;
    String pattern = "dd-MMM-yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    
    try {
        conn = DriverManager.getConnection(connString, "username", "password");
    } catch( Exception ex) {
        out.print("Some exception occured while creating database connection, Make sure username and password is correct " + ex);
    }
   
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT tref, t_date, t_points, amount FROM Transactions WHERE cid ="+cid);
        String output = "";
       
        while (rs.next()) {
            String date = simpleDateFormat.format(rs.getDate("t_date"));
            output += rs.getString("tref") + ","+ date + "," + rs.getInt("t_points") + "," + rs.getFloat("amount")+"#";
        }     
        
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
%>



