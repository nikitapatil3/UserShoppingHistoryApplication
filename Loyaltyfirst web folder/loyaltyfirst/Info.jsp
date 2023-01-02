<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
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
        ResultSet rs = stmt.executeQuery("SELECT c.cname, p.num_of_points FROM Customers c, Point_Accounts p WHERE c.cid = p.cid AND c.cid = " + cid);
        String output = "";
       
        while (rs.next()) {
            output += rs.getObject(1)+","+rs.getObject(2);
        }     
        
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
    
%>

