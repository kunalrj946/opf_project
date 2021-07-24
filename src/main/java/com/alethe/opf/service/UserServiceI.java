 package com.alethe.opf.service;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.alethe.opf.dto.AuthRequest;
import com.alethe.opf.dto.OrderResponse;
import com.alethe.opf.dto.RevisionRequest;
import com.alethe.opf.dto.Revision_Request;
import com.alethe.opf.dto.SoRequest;
import com.alethe.opf.dto.SoStatusPojo;
import com.alethe.opf.dto.User_details;
import com.alethe.opf.entity.Users;
import com.alethe.opf.exception.ResourceNotFoundException;
import com.alethe.opf.opfResponse.OpfResponse;

/**
 * Created by Kunal Kumar
 */
@Service
public interface UserServiceI {
	
	public abstract HashMap<String, Object> getToken(AuthRequest authRequest) throws Exception;
	public abstract HashMap<String, Object> logout(String token , String loginid) throws ResourceNotFoundException;
	public abstract HashMap<String, Object> addUser(Users user)throws ResourceNotFoundException;
	public abstract HashMap<String, Object> getAllUsers()throws ResourceNotFoundException;
	public abstract HashMap<String, Object> getUserById(Integer id) throws ResourceNotFoundException;
	public abstract HashMap<String, Object> updateUserById(Users user, Integer id) throws ResourceNotFoundException;
	public abstract HashMap<String, Object> deleteUserById(Integer id) throws ResourceNotFoundException;
//	public abstract HashMap<String, Object> upsertOrder(SoRequest Order, Integer id, String loginid) throws ResourceNotFoundException;
			
	
	public abstract OpfResponse updatePassWordById(User_details user, Integer id) throws ResourceNotFoundException;
	

	public abstract HashMap<String, Object> updateStatus(SoStatusPojo req, Integer id ,String loginid)throws ResourceNotFoundException;
	public abstract OpfResponse getOrderById(Integer id ,String loginid) throws ResourceNotFoundException;
	public abstract OpfResponse getAllOrders(String token)throws ResourceNotFoundException;
	public abstract HashMap<String, Object> deleteAllOrders(Integer id) throws ResourceNotFoundException;
	public abstract HashMap<String , Object> deleteItem(Integer id) throws ResourceNotFoundException;
	public abstract HashMap<String, Object> getSoGenerate(Long company_id ,String date) throws ParseException, Exception;
	public abstract HashMap<String, Object> getUsersKey(String token) throws ResourceNotFoundException;

	public abstract HashMap<String ,Object> getParent(String roleId);
	public abstract OpfResponse getDashBoardStatus(String loginid) throws Exception;
	
	
	public abstract OpfResponse addRevision(Revision_Request Order ,Integer so_id, String loginid)throws ResourceNotFoundException;
	public abstract OpfResponse getRevisionList(RevisionRequest rr,String loginid)throws ResourceNotFoundException;
	public abstract OpfResponse getRevisionDetails(RevisionRequest rr ,String loginid)throws ResourceNotFoundException;
	public abstract OpfResponse getExpenseType(Integer id)throws ResourceNotFoundException;
	public abstract OpfResponse getExpenseDetails(Integer type) throws ResourceNotFoundException;
	
}
