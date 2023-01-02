import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

/**
 *
 * @author talmanie
 */

@WebServlet ("/login")
public class Login extends HttpServlet{
public void doGet(HttpServletRequest request, HttpServletResponse response){
       try   
       {
       String user=request.getParameter("user");
       String pass=request.getParameter("pass");
       
       DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
       String connString="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
       Connection conn=DriverManager.getConnection(connString,"username","password");
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery("SELECT * FROM Login WHERE username='"+user+"' AND passwd='"+pass+"'");
   
       PrintWriter out=response.getWriter();
       
       if(rs.next()){
            if(user.equals(rs.getString("username"))&& pass.equals(rs.getString("passwd"))){
                out.print("Yes:"+rs.getInt("cid"));
            }
            else{
                out.print("No");
            }
       } 
       else{
           out.print("No");
       }
       
       conn.close();
       }
       catch (Exception e){}  
   }
}