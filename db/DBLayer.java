package db;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;

import myexception.CustomException;

public class DBLayer implements Storage {
	
	public long idNo=0;
	public long accNo=0;
	Cache cache=new Cache();
	
	public Cache mapToFile(Map<Long,Map<Long, AccountInfo>> inpAccountMap,Map<Long, CustomerInfo> customerMap) throws CustomException, Exception {
		
        if(!inpAccountMap.isEmpty())
        {
        	
    			Class.forName("com.mysql.cj.jdbc.Driver");  
    			try(Connection con=DriverManager.getConnection(  
    					"jdbc:mysql://localhost:3306/bank","root","1234");)  	//here bank is database name, root is username and password  
    			{
    				int i=0;
    			Iterator<Long> itr = customerMap.keySet().iterator();
    			while (itr.hasNext())
    			{
    			    Long key = itr.next();
    			    CustomerInfo value = customerMap.get(key);
    			    String name= value.getName();
    			    int age=value.getAge();
    			   char gender= value.getGender();
    			   PreparedStatement stmt=con.prepareStatement("INSERT INTO customerdata " + "VALUES (?,?,?,?)");
    				stmt.setFloat(1,key);
    				stmt.setString(2,name);
    				stmt.setInt(3,age);
    				stmt.setString(4,String.valueOf(gender));
    				stmt.executeUpdate();
    				i++;
    			}
    			
    			Iterator<Long> itreate = inpAccountMap.keySet().iterator();
    			while (itreate.hasNext())
    			{
    			    Long key = itreate.next();
    			    Map<Long, AccountInfo> value = inpAccountMap.get(key);
    			    Iterator<Long> itreate1 = value.keySet().iterator();
    			    while (itreate1.hasNext())
        			{
    			       Long accNo = itreate1.next();
    			       AccountInfo account = value.get(accNo);
    			       String name= account.getName();
        			   Long balance=account.getBalance();
        			   String branch= account.getBranch();
        			   boolean status=account.isStatus();
        			   PreparedStatement stmt=con.prepareStatement("INSERT INTO accountdata " + "VALUES (?,?,?,?,?,?)");
        				stmt.setFloat(1,key);
        				stmt.setFloat(3,accNo);
        				stmt.setString(4, branch);
        				stmt.setString(2,name);
        				stmt.setFloat(5, balance);
        				stmt.setBoolean(6, status);
        				stmt.executeUpdate();
        			}
    				i++;
    			}
    			
    			System.out.println("rows affected for customerdata "+i);
    			cache.storeInCache(inpAccountMap,accNo,idNo,customerMap);
    	     	con.close();
            	}
        	    catch (Exception e) {
        		e.printStackTrace();
			}
    			return cache;
        }

        else
        {
         throw new CustomException("map is empty");  
        }
		
}

	@Override
	public Cache readFromFile() throws IOException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");  
		try(Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/bank","root","1234");)  	//here bank is database name, root is username and password  
		{
			  String query = "SELECT * FROM accountdata";
		      java.sql.Statement st = con.createStatement();
		      ResultSet rs = st.executeQuery(query);
		      while (rs.next())
		      {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        long accountnumber = rs.getInt("accountnumber");
		        String branch = rs.getString("branch");
		        boolean status = rs.getBoolean("status");
		        int balance = rs.getInt("balance");
		        idNo=id;
		        // print the results
		        System.out.format("%s, %s, %s, %s, %s, %s\n", id, name, status,accountnumber, branch, balance);
		      }
		      st.close();
		    }
			
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
