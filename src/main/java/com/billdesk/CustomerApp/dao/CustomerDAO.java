package com.billdesk.CustomerApp.dao;

import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.util.Properties;

import com.billdesk.CustomerApp.exception.ResponseMessage;
import com.billdesk.CustomerApp.DBConnection;
import com.billdesk.CustomerApp.model.CustomerInfo;


//FUNCTIONALITIES DEFINED FOR CUSTOMER OPERATION
public class CustomerDAO {
	
	//GENERATE RANDOM ID
	static int generateId() {
		double randomDouble = Math.random();
		randomDouble = randomDouble * 50000 + 1;
		int randomInt = (int) randomDouble;
		return randomInt;
		}
	
	//INSERTING CUSTOMER INFO		-- RETURNS RESPONSE MESAAGE OBJECT
    public ResponseMessage insert(CustomerInfo customer) throws IOException {
    	
    	//FEATCHING CONNECTION AND CREATING OBJECT
    	DBConnection con = new DBConnection();
    	Connection connection = con.getOracleConnection();
    	ResponseMessage cust = new ResponseMessage();
    	
    	int genId = generateId();
    	customer.setCustId(Integer.toString(genId));
    	    	
    	//FORMING SQL QUERY TO INSERT THE OBJECT INFORMATION
    	String checkValidity = "SELECT CUST_ID FROM CUSTOMER_INFO where CUST_ID = ?";
        String insertTableSQL = "INSERT INTO CUSTOMER_INFO (CUST_ID, CUST_NAME, CUST_AGE, VALIDITY) VALUES (?,?,?,1)";
    	String insertTableSQL2 = "INSERT INTO CUSTOMER_SIDEINFO (CUST_ID, CUST_CONTACT, CUST_ADDRESS, CUST_EMAIL) VALUES (?,?,?,?)";
    	String selectTableSQL = "SELECT * FROM CUSTOMER_INFO c1, CUSTOMER_SIDEINFO c2 WHERE c1.CUST_ID = c2.CUST_ID AND c1.CUST_ID = ?";
    	
        
    	//VALIDATING INFORMATION
    	try 
    	{
        	//PERFORMING THE INSERRT OPERATION
    		PreparedStatement ps0 = connection.prepareStatement(checkValidity);
   			ps0.setInt(1, Integer.parseInt(customer.getCustId()));
   			ResultSet rs0 = ps0.executeQuery();
   			if(!rs0.next()){  			
    			
   				PreparedStatement ps = connection.prepareStatement(insertTableSQL);
    			
   				ps.setInt(1, Integer.parseInt(customer.getCustId()));
   				ps.setString(2, customer.getCustName());
   				ps.setInt(3, Integer.parseInt(customer.getCustAge()));
    			
   				ps.executeUpdate();
    			
   				PreparedStatement ps2 = connection.prepareStatement(insertTableSQL2);
    			
   				ps2.setInt(1, Integer.parseInt(customer.getCustId()));
   				ps2.setString(2, customer.getCustContact());
   				ps2.setString(3, customer.getCustAddress());
   				ps2.setString(4, customer.getCustEmail());

   				ps2.executeUpdate();
    			
   				PreparedStatement ps3 = connection.prepareStatement(selectTableSQL);
   				ps3.setInt(1, Integer.parseInt(customer.getCustId()));
   				ResultSet rs = ps3.executeQuery();
        	
   				//TRACING RESULT SET
   				if (rs.next()){
        		
   					//SETTING DATA FROM RESULT SET
   					cust.setCustId(Integer.toString(rs.getInt(1)));
   					cust.setCustName(rs.getString(2));
   					cust.setCustAge(Integer.toString(rs.getInt(3)));
   					cust.setValidationFlag(rs.getInt(4));
   					cust.setCustContact(rs.getString(6));
   					cust.setCustAddress(rs.getString(7));
   					cust.setCustEmail(rs.getString(8));
   					cust.setMessage("Customer Created Successfully !!");
   				}
   			}
   			else{
   				cust.setCode("422");
   				cust.setMessage("Customer with same ID already exist.");
   			}
   		} 
   		catch (SQLException e) {
   			System.out.println(e.getMessage());	
   		}
    	catch (Exception e){
    		e.printStackTrace();
    		cust.setCode("422");
    		cust.setMessage("Invalid Entry");
    		return cust;
    	}
   		return cust;
    	           
    }
    
    
    
    //VIEWING PERTICULAR CUSTOMER INFO BASED ON ID		-- RETURNS CUSTOMER OBJECT
    public ResponseMessage show(int id) throws IOException {
    	
    	//CREATING CONNECTION AND OBJECT
    	DBConnection con = new DBConnection();
    	Connection connection = con.getOracleConnection();
    	ResponseMessage customer = new ResponseMessage();
    	
        String displayTableSQL = "SELECT * FROM CUSTOMER_INFO c1, CUSTOMER_SIDEINFO c2 WHERE c1.CUST_ID = c2.CUST_ID AND c1.CUST_ID =? AND VALIDITY=1 " ;
    	
        try 
        {	
        	//EXECUTING QUERIES        	
        	PreparedStatement ps = connection.prepareStatement(displayTableSQL);
        	ps.setInt(1, id);
        	
        	ResultSet rs = ps.executeQuery();
        	
        	//READING RESULT SET
        	if (rs.next()){
        		
        		//SETTING DATA FROM RESULT SET 
        		customer.setCustId(Integer.toString(rs.getInt(1)));
        		customer.setCustName(rs.getString(2));
        		customer.setCustAge(Integer.toString(rs.getInt(3)));
        		customer.setValidationFlag(rs.getInt(4));
        		customer.setCustContact(rs.getString(6));
        		customer.setCustAddress(rs.getString(7));
        		customer.setCustEmail(rs.getString(8));	
        		customer.setMessage("User Found Successfully.");

        	}
        	else{
        		//SETTING ERROR SET
        		customer.setCode("404");
        		customer.setMessage("Not Found");
        	}
        } 
        catch (SQLException e) {
        	System.out.println(e.getMessage());	
        }
        catch (Exception e){
        	e.printStackTrace();
        	
        }
        
        return customer;  
    }
    
    
    //DELETING PERTICULAR CUSTOMER BASED ON ID		-- RETURNS CUSTOMER OBJECT
    public ResponseMessage delete(int id) throws IOException {
    	
    	//CREATING CONNECTION AND OBJECT
    	DBConnection con = new DBConnection();
    	Connection connection = con.getOracleConnection();
    	ResponseMessage customer = new ResponseMessage();
    	
    	String checkValidity = "SELECT CUST_ID FROM CUSTOMER_INFO where CUST_ID = ?";
        String deleteTableSQL = "UPDATE CUSTOMER_INFO SET VALIDITY = 0 WHERE CUST_ID = ?";
        String selectTableSQL = "SELECT * FROM CUSTOMER_INFO c1, CUSTOMER_SIDEINFO c2 WHERE c1.CUST_ID = c2.CUST_ID AND c1.CUST_ID = ?" ;

        try 
        {	
        	//EXECUTING QUERIES    
        	PreparedStatement ps0 = connection.prepareStatement(checkValidity);
   			ps0.setInt(1, id);
   			ResultSet rs0 = ps0.executeQuery();
   			
        	PreparedStatement ps = connection.prepareStatement(deleteTableSQL);
        	ps.setInt(1, id);
        	ps.executeUpdate();
        	
        	PreparedStatement ps2 = connection.prepareStatement(selectTableSQL);
        	ps2.setInt(1, id);
        	
        	ResultSet rs = ps2.executeQuery();
        	
        	//READING RESULT SET
        	if (rs.next() && !rs0.next()){
        		
        		//SETTING DATA FROM RESULT SET 1
        		customer.setCustId(Integer.toString(rs.getInt(1)));
        		customer.setCustName(rs.getString(2));
        		customer.setCustAge(Integer.toString(rs.getInt(3)));
        		customer.setValidationFlag(rs.getInt(4));       		
        		customer.setCustContact(rs.getString(6));
        		customer.setCustAddress(rs.getString(7));
        		customer.setCustEmail(rs.getString(8));
        		customer.setMessage("Customer Deleted Successfully !!");
        	}
        	else{
        		//SETTING ERROR SET
        		customer.setCode("404");
        		customer.setMessage("Not Found");
        	}
        } 
        catch (SQLException e) {
        	System.out.println(e.getMessage());	
        }
        
        return customer;  
    }
    
    
  //VIEWING ALL CUSTOMER INFO   	-- RETURNS LIST OF CUSTOMER OBJECT
    public List<CustomerInfo> showall() throws IOException
    {
    	
    	//CREATING CONNECTION AND OBJECT
    	DBConnection con = new DBConnection();
    	Connection connection = con.getOracleConnection();
        String viewTableSQL = "SELECT * FROM CUSTOMER_INFO c1, CUSTOMER_SIDEINFO c2 where c1.CUST_ID=c2.CUST_ID AND VALIDITY = 1";
        List<CustomerInfo> customerlist= new ArrayList<>();
    	
    	 try 
         {         	
         	Statement st = connection.createStatement();
         	ResultSet rs3 = st.executeQuery(viewTableSQL);
         	
         	//READING RESULTSET
         	while(rs3.next()) {
         		
         		CustomerInfo customer = new CustomerInfo();
         		
         		customer.setCustId(Integer.toString(rs3.getInt(1)));
         		customer.setCustName(rs3.getString(2));
         		customer.setCustAge(Integer.toString(rs3.getInt(3)));
         		customer.setValidationFlag(rs3.getInt(4));
         		customer.setCustContact(rs3.getString(6));
         		customer.setCustAddress(rs3.getString(7));
         		customer.setCustEmail(rs3.getString(8));
         		
         		//ADDING NEW CUSTOMER TO CUSTOMER LIST
         		customerlist.add(customer);
         	}
             
         } 
         catch (SQLException e) {    	
         	System.out.println(e.getMessage());
         }
    	 
    	return customerlist;
    	
    }
    
    
    //UPDATE CUSTOMER INFO   	-- RETURNS RESPONSE MESSAGE OBJECT
    public ResponseMessage update(CustomerInfo customer) throws IOException {
    	
    	//FEATCHING CONNECTION
    	DBConnection con = new DBConnection();
    	Connection connection = con.getOracleConnection();
    	ResponseMessage verify_customer = new ResponseMessage();
    	
    	//FORMING SQL QUERY AND UPDATEING THE OBJECT
    	String checkValidity = "SELECT CUST_ID FROM CUSTOMER_INFO where CUST_ID = ?";
        String verifyTableSQL = "SELECT * FROM CUSTOMER_INFO c1, CUSTOMER_SIDEINFO c2 WHERE c1.CUST_ID = c2.CUST_ID AND c1.CUST_ID = ?";
        
		try 
		{
			PreparedStatement ps0 = connection.prepareStatement(checkValidity);
   			ps0.setInt(1, Integer.parseInt(customer.getCustId()));
   			ResultSet rs0 = ps0.executeQuery();
	
   			
   			
			PreparedStatement ps = connection.prepareStatement(verifyTableSQL);
        	ps.setInt(1, Integer.parseInt(customer.getCustId()));
        	ResultSet rs = ps.executeQuery();					  
     	       	
			//READING RESULTSET AND CHECKING FOR CHANGE IN UPDATED FIELD
			if(rs.next() && !rs0.next()) {    		
				if(customer.getCustId() != null)
					verify_customer.setCustId(customer.getCustId());
				else
					verify_customer.setCustId(Integer.toString(rs.getInt("CUST_ID")));
				
				if(customer.getCustName() != null)
					verify_customer.setCustName(customer.getCustName());
				else
					verify_customer.setCustName(rs.getString("CUST_NAME"));
				
				if(customer.getCustAge() != null)
					verify_customer.setCustAge(customer.getCustAge());
				else
					verify_customer.setCustAge(Integer.toString(rs.getInt("CUST_AGE")));
				
				verify_customer.setValidationFlag(rs.getInt("VALIDITY"));
				
				if(customer.getCustContact()!=null)
					verify_customer.setCustContact(customer.getCustContact());
				else
					verify_customer.setCustContact(rs.getString("CUST_CONTACT"));
				
				if(customer.getCustAddress()!=null)
					verify_customer.setCustAddress(customer.getCustAddress());
				else
					verify_customer.setCustAddress(rs.getString("CUST_ADDRESS"));
					
				if(customer.getCustEmail()!=null)
					verify_customer.setCustEmail(customer.getCustEmail());
				else
					verify_customer.setCustEmail(rs.getString("CUST_EMAIL"));
				
				System.out.println(verify_customer);
				
				String updateTable1 = "UPDATE CUSTOMER_INFO SET CUST_NAME = ?, CUST_AGE = ?  WHERE CUST_ID=?" ;
		        String updateTable2 = "UPDATE CUSTOMER_SIDEINFO SET CUST_ADDRESS = ?, CUST_CONTACT = ?,CUST_EMAIL = ?  WHERE CUST_ID=?"  ;

		        PreparedStatement ps2 = connection.prepareStatement(updateTable1);
	        	ps2.setString(1, verify_customer.getCustName());
	        	ps2.setInt(2, Integer.parseInt(verify_customer.getCustAge()));
	        	ps2.setInt(3, Integer.parseInt(verify_customer.getCustId()));
	        	ps2.executeUpdate();	
	        	
	        	PreparedStatement ps3 = connection.prepareStatement(updateTable2);
	        	ps3.setString(1, verify_customer.getCustAddress());
	        	ps3.setString(2, verify_customer.getCustContact());
	        	ps3.setString(3, verify_customer.getCustEmail());
	        	ps3.setInt(4, Integer.parseInt(verify_customer.getCustId()));
	        	ps3.executeUpdate();		        	
		
			}
						
			else{
				verify_customer.setCode("404");
				verify_customer.setMessage("Not Found");
				return verify_customer;
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());	
		}
		catch (Exception e){
			e.printStackTrace();
			verify_customer.setCode("422");
    		verify_customer.setMessage("Invalid Entry");
    		return verify_customer;
		}
    	    	  
    	return verify_customer;
    }

    
  //CLOSING ORACLE CONNECTION		-- RETURNS STATUS
//	private boolean closeOracleDriver(Connection connection) {
//		try {
//			connection.close();
//			return true;
//		} catch (SQLException exception) {
//			exception.printStackTrace();
//			return false;
//		}
//
//	}
	
}
