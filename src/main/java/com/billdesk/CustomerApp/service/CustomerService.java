package com.billdesk.CustomerApp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;



import com.billdesk.CustomerApp.dao.CustomerDAO;
import com.billdesk.CustomerApp.exception.ResponseMessage;
import com.billdesk.CustomerApp.exception.Validation;
import com.billdesk.CustomerApp.model.CustomerInfo;


@Path("customer")
public class CustomerService {

	private final CustomerDAO dao= new CustomerDAO();
	
	
	//ADDING CUSTOMER
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public ResponseMessage addCust(CustomerInfo cust) throws IOException{
	
		ResponseMessage c = new ResponseMessage();
		if(Validation.isEmailValid(cust.getCustEmail())&& Validation.isContactValid(cust.getCustContact()))
		{			
			c = dao.insert(cust);		
			return c;
		}
		else{
    		//SETTING ERROR SET
    		c.setCode("422");   		
    		if(!Validation.isContactValid(cust.getCustContact()))
    			c.setMessage("Check Contact");
    		else
    			c.setMessage("Check Email");
    		
    		return c;
    	}  
	}
	
	//SHOWING CUSTOMER WITH ID
	@GET
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public CustomerInfo showCust(@PathParam("id") int id) throws IOException{
		
		CustomerInfo c = new CustomerInfo();
		c = dao.show(id);
		
		return c;	
	}
	
	//DELETING CUSTOMER WITH ID
	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public CustomerInfo deleteCust(@PathParam("id") int id) throws IOException{
		
		CustomerInfo c = new CustomerInfo();
		c = dao.delete(id);
		
		return c;	
	}
	
	//SHOW ALL THE CUSTOMERS
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<CustomerInfo> showCust() throws IOException{
		
		List<CustomerInfo> c = new ArrayList<CustomerInfo>();
		c = dao.showall();
		
		return c;	
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public CustomerInfo update(CustomerInfo customer) throws IOException
	{
		System.out.println(customer);
		ResponseMessage c = new ResponseMessage();
		
		
		if(Validation.isContactValid(customer.getCustContact()) && Validation.isEmailValid(customer.getCustEmail()))
			{
			c=dao.update(customer);
			return c;
			}
		else{
			//SETTING ERROR SET
			c.setCode("422");
			if(!Validation.isContactValid(customer.getCustContact()))
				c.setMessage("Check Contact");
			else
				c.setMessage("Check Email");
			return c;    		
			}
	}
		
	
	
	@GET
	@Path("ping")
	public String ping(){
		
		return "Customer App is running!";
		
	}
		
	
}
