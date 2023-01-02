<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>
<%
    String tref = request.getParameter("tref");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String connString = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = null;
    String pattern = "dd-MMM-yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    try {
        conn = DriverManager.getConnection(connString, "username", "password");
    } catch (Exception ex) {
        out.print("Some exception occured while creating database connection, Make sure username and password is correct " + ex);
    }
   
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT t.t_date, t.t_points, p.prod_name, p.prod_points, tp.quantity "
            + "FROM Transactions t, Transactions_Products tp, Products p "
            + "WHERE t.tref = tp.tref AND tp.prod_id = p.prod_id AND t.tref ='"+tref+"'");
        String output = "";
       
        while (rs.next()) {
            String date = simpleDateFormat.format(rs.getDate(1));
            output += date+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5)+"#";
        }     
       
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
    
%>


