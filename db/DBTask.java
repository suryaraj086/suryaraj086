package db;
import java.sql.*;
import java.util.*;


public class DBTask {

	public void insertQuery() {
		try{   
			Class.forName("com.mysql.cj.jdbc.Driver");  
			try(Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/test","root","1234");)
			{
			//here test is database name, root is username and password  
			Statement stmt=con.createStatement();  
		String query1 = "INSERT INTO test1 " + "VALUES ('John', 34)";
		stmt.executeUpdate(query1);
		con.close();
	}
	}
		catch(Exception e){ 
			System.out.println(e);
			}  
	}
	
	public void insertQueryWithPrep(String name,int age) {
		try{   
			Class.forName("com.mysql.cj.jdbc.Driver");  
			try(Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/test","root","1234");)
			{
			//here test is database name, root is username and password  
			PreparedStatement stmt=con.prepareStatement("INSERT INTO test1 " + "VALUES (?, ?)");  
		stmt.setInt(2,age);
		stmt.setString(1,name); //1 specifies the first parameter in the query   
		//stmt.executeUpdate(query1);
		con.close();
	}
	}
		catch(Exception e){ 
			System.out.println(e);
			}  
	}
	
	public void updateQuery() {
		try{   
			Class.forName("com.mysql.cj.jdbc.Driver");  
			try(Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","1234");)
			{
			//here test is database name, root is username and 1234 password  
			Statement stmt=con.createStatement();  
		String query1 = "UPDATE test1 SET name = 'Mary',age='12'"+ "WHERE name ='Mary'  ";
		stmt.executeUpdate(query1);
		}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public void createTable() {
		try{   
			Class.forName("com.mysql.cj.jdbc.Driver");  
			try(Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","1234");)
			{
			//here test is database name, root is username and 1234 password  
			Statement stmt=con.createStatement();  
		String query1 = "CREATE TABLE student(id int,name varchar(255),age int,subject varchar(255)) ";
		stmt.executeUpdate(query1);
		}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void selectQuery(String table) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			try(Connection con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/test","root","1234");)
			{
			//here test is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from "+table);  
			while(rs.next())  
			System.out.println(rs.getString(1)+"  "+rs.getInt(2));  
			}
		}
		catch(Exception e){ 
			System.out.println(e);
			}  
		}
	
	public static void main(String[] args)
	{
		DBTask db=new DBTask();
	//	db.insertQueryWithPrep("surya",23);
		//db.selectQuery();
		System.out.println("1.create new table\n2.insert into table\n3.update table\n4.view table");
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the value");
		int choice=scan.nextInt();
		switch (choice) {
		case 1: {
			db.createTable();
			break;
		}
		case 2: {
			db.insertQueryWithPrep("surya",23);
			break;
		}
		case 3: {
			db.updateQuery();
			break;
		}
		case 4: {
			System.out.println("Enter the table name");
			String table=scan.nextLine();
		    //test1-table
			db.selectQuery(table);
			break;
		}
		default:
			System.out.println("Unexpected value: ");
		}
	scan.close();
	}
}
