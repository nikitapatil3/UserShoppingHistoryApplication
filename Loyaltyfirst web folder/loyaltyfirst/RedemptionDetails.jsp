<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>
<%
    String prizeid = request.getParameter("prizeid");
    String cid = request.getParameter("cid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String connString = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    String pattern = "dd-MMM-yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(connString, "username", "password");
    } catch (Exception ex) {
        out.print("Some exception occured while creating database connection, Make sure username and password is correct " + ex);
    }
   
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT p.p_description, SUM(p.points_needed * r.quantity), r.r_date, e.center_name " + 
                                              "FROM Prizes p, Redemption_History r, ExchgCenters e " +
                                              "WHERE p.prize_id = r.prize_id AND r.center_id = e.center_id AND " +
                                              "r.prize_id = " + prizeid + " AND r.cid = " + cid +
                                              "GROUP BY p.p_description, r.r_date, e.center_name");
        String output = "";
        while (rs.next()) {
            String date = simpleDateFormat.format(rs.getDate(3));
            output += rs.getObject(1) + "," + rs.getObject(2) + "," + date + "," + rs.getObject(4)+"#";
        }     
        
        conn.close();  
        out.print(output); 
    } catch (Exception ex) {
        out.print("Some exception occured while executing request " + ex);
    }
    
%>

