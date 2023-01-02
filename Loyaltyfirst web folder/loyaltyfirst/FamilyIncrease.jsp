<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
    String familyId = request.getParameter("fid");
    String cid = request.getParameter("cid");
    String noOfPoints = request.getParameter("npoints");
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
        ResultSet rs = stmt.executeQuery("UPDATE Point_Accounts SET num_of_points = num_of_points + " + noOfPoints + " " +
                                        "WHERE family_id = " + familyId + " AND cid != " + cid);
        String output = noOfPoints + " Points added to the member of Family ID " + familyId;
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
    
%>

