<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
    String tref = request.getParameter("tref");
    String cid = request.getParameter("cid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String connString = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(connString, "username", "password");
    } catch (Exception ex) {
        out.print("Some exception occured while creating database connection, Make sure username and password is correct " + ex);
    }
   
    try {
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT p.family_id, p.percent_added, t.t_points "
            + "FROM Point_accounts p, Transactions t "
            + "WHERE p.cid = t.cid AND t.tref ='"+tref+"'" + " AND p.cid =" + cid);
        String output = "";
       
        while (rs.next()) {
            output += rs.getInt(1) + "," + rs.getInt(2) + "," + rs.getInt(3);
        }     
       
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
    
%>


