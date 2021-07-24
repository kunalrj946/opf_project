package com.alethe.opf.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alethe.opf.dto.AuthRequest;
import com.alethe.opf.dto.Expense_type_detail;
import com.alethe.opf.dto.Expense_type_list;
import com.alethe.opf.dto.ItemResponse;
import com.alethe.opf.dto.OrderResponse;
import com.alethe.opf.dto.RevisionRequest;
import com.alethe.opf.dto.Revision_Request;
import com.alethe.opf.dto.SODashBoard;
import com.alethe.opf.dto.SoRequest;
import com.alethe.opf.dto.SoStatusPojo;
import com.alethe.opf.dto.UserMstResponse;
import com.alethe.opf.dto.UserPair;
import com.alethe.opf.dto.UserParent;
import com.alethe.opf.dto.User_details;
//import com.alethe.opf.entity.Other_Expense_Type;
import com.alethe.opf.entity.Rev_history;
import com.alethe.opf.entity.Revision_history;
import com.alethe.opf.entity.Sale_item;
import com.alethe.opf.entity.Sale_order;
import com.alethe.opf.entity.Token_destroy;
import com.alethe.opf.entity.User_role_mst;
import com.alethe.opf.entity.User_type_mst;
import com.alethe.opf.entity.Users;
import com.alethe.opf.exception.ResourceNotFoundException;
import com.alethe.opf.opfResponse.OpfResponse;
import com.alethe.opf.repository.Company_mst_repo;
import com.alethe.opf.repository.EmQuery;
import com.alethe.opf.repository.Other_expenses_repo;
import com.alethe.opf.repository.RevisionHistoryJoinRepo;
import com.alethe.opf.repository.RevisionRepo;
import com.alethe.opf.repository.SaleItemJoinRepo;
import com.alethe.opf.repository.SaleOrderJoinRepo;
import com.alethe.opf.repository.Sale_item_repo;
import com.alethe.opf.repository.Sale_order_repo;
import com.alethe.opf.repository.Token_destory_repo;
import com.alethe.opf.repository.UserRepository;
import com.alethe.opf.repository.UserRoleJoinRepo;
import com.alethe.opf.repository.User_role_mst_repo;
import com.alethe.opf.repository.User_type_mst_repository;
import com.alethe.opf.utility.JwtUtil;
import com.alethe.opf.utility.Utility;

/**
 * Created by Kunal Kumar
 */
@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Sale_order_repo saleOrder;

	@Autowired
	private Company_mst_repo comp_repo;

	@Autowired
	private Sale_item_repo saleItem;

	@Autowired
	private SaleOrderJoinRepo soRepo;

	@Autowired
	private EmQuery query;

	@Autowired
	private SaleItemJoinRepo sijr;

	@Autowired
	private UserRoleJoinRepo userRepoJoin;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private User_role_mst_repo userRoleRepo;

	@Autowired
	private User_type_mst_repository userTypeRepo;

	@Autowired
	private Token_destory_repo tokenDestroy;

	@Autowired
	private RevisionRepo revisionRepo;

	@Autowired
	private Other_expenses_repo expense_repo;

	@Autowired
	private RevisionHistoryJoinRepo rev_join;

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Transactional
	@Override
	public HashMap<String, Object> getToken(AuthRequest authRequest)
			throws UsernameNotFoundException, ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
//		HashMap<String, Object> response = new HashMap<String, Object>();
		Optional<Users> user = null;

		user = userRepo.findByLoginid(authRequest.getLoginid());
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Exist " + authRequest.getLoginid()));

//		logger.debug("db password : "+ user.get().getUser_password());
//		boolean isMatch = Utility.getBase64Decode(authRequest.getPassword()) == user.get().getUser_password();
//		logger.debug("user password : " +Utility.getBase64Decode(authRequest.getPassword()));

//		logger.debug("is matching the password : " + isMatch);

		int userid = user.get().getUser_id();
		logger.debug("userId :" + userid);
		String role = user.get().getUser_role();
		String type = user.get().getUser_type();
		String userName = "", isUser_active = "";

		if (user.get().getIs_active() != 0) {

			isUser_active = "Active";
		} else {

			isUser_active = "Inactive";
		}

		if (!user.get().getUser_fname().isEmpty() && !user.get().getUser_lname().isEmpty()) {

			userName = user.get().getUser_fname() + " " + user.get().getUser_lname();

		} else if (user.get().getUser_fname().isEmpty() && !user.get().getUser_lname().isEmpty()) {

			userName = user.get().getUser_lname();

		} else if (!user.get().getUser_fname().isEmpty() && user.get().getUser_lname().isEmpty()) {

			userName = user.get().getUser_fname();

		} else {

			userName = "Unkonwn User ";
		}

		if (type.equalsIgnoreCase("ADM")) {

			Optional<User_type_mst> userType = userTypeRepo.findByUserTypeId(type);
			userType.orElseThrow(() -> new ResourceNotFoundException(
					"user type not found for this login_id -> " + authRequest.getLoginid()));

			logger.debug("userType :" + userType);
			logger.debug("usertype :" + userType.get().getUserTypeName());

			content.put("user_id", userid);
			content.put("user_type", userType.get().getUserTypeName());
			content.put("user_name", userName);
			content.put("user_status", isUser_active);

		} else if (type.equalsIgnoreCase("USR")) {

			Optional<User_type_mst> userType = userTypeRepo.findByUserTypeId(type);
			userType.orElseThrow(() -> new ResourceNotFoundException(
					"user type not found for this loginid -> " + authRequest.getLoginid()));

			logger.debug("userType :" + userType);

			Optional<User_role_mst> userRole = userRoleRepo.findByUserRoleId(role);
			userRole.orElseThrow(() -> new ResourceNotFoundException(
					"user role  not found for this login_id -> " + authRequest.getLoginid()));
			logger.debug("userRole :" + userRole);

			logger.debug("usertype :" + userType.get().getUserTypeName());

			logger.debug("userrole :" + userRole.get().getUserRoleName());

			logger.debug("Role is : " + role + " , " + "type is : " + type + "user type : "
					+ userType.get().getUserTypeName() + " ," + "user role : " + userRole.get().getUserRoleName());

			content.put("user_id", userid);
			content.put("user_role", userRole.get().getUserRoleName());
			content.put("user_type", userType.get().getUserTypeName());
			content.put("user_name", userName);
			content.put("user_status", isUser_active);

		} else if (type.equalsIgnoreCase("VWR")) {

			Optional<User_type_mst> userType = userTypeRepo.findByUserTypeId(type);
			userType.orElseThrow(() -> new ResourceNotFoundException(
					"user type  not found for this login_id -> " + authRequest.getLoginid()));

			logger.debug("userType :" + userType);

			content.put("user_id", userid);
			content.put("user_type", userType.get().getUserTypeName());
			content.put("user_name", userName);
			content.put("user_status", isUser_active);
		}

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getLoginid(), authRequest.getPassword()));
		String token = jwtUtil.generateToken(authRequest.getLoginid());

		logger.debug("<<<access_token>>> is : " + token);

		content.put("access_token", token);

		Token_destroy token1 = new Token_destroy();
		token1.setId(userid);
		token1.setToken(token);
		token1.setUsername(authRequest.getLoginid());
		tokenDestroy.save(token1);

		content.put("error", Boolean.FALSE);

//		response.put("content", content);

		return content;
	}

	@Override
	@Transactional
	public HashMap<String, Object> logout(String token, String loginid) throws ResourceNotFoundException {

		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
//		Optional<Users> user = null;

		tokenDestroy.deleteToken(token, loginid);
		content.put("message", "server logout successfull !");

		response.put("content", content);

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> addUser(Users user) throws ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = "";

		Users result = userRepo.save(user);

		if (result != null) {
			errorMsg = "user added !";
			hasError = false;
			content.put("collection", result);
			content.put("error", hasError);
			content.put("message", errorMsg);

			response.put("content", content);
		} else {

			throw new ResourceNotFoundException("data not found !");
		}
		return response;
	}

	@Transactional
	@Override
	public HashMap<String, Object> getAllUsers() throws ResourceNotFoundException {

		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;

//		List<Users> result = userRepo.findAll();
		List<UserMstResponse> result = userRepoJoin.getAllUserJoin();

		if (!result.isEmpty()) {
			errorMsg = "data is found !";
			hasError = false;
			content.put("collection", result);
			content.put("error", hasError);
			content.put("message", errorMsg);

			response.put("content", content);
		} else {

			throw new ResourceNotFoundException("data not found !");
		}
		return response;
	}

	@Transactional
	@Override
	public HashMap<String, Object> getUserById(Integer id) throws ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;

		Optional<Users> result = userRepo.findById(id);
		result.orElseThrow(() -> new ResourceNotFoundException("user not found for this id " + id));

		errorMsg = "data is found !";
		hasError = false;
		content.put("result", result);
		content.put("error", hasError);
		content.put("message", errorMsg);

		response.put("content", content);

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> updateUserById(Users user, Integer id) throws ResourceNotFoundException {

		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;
		Users data = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found for this id :: " + id));

		data.setLoginid(user.getLoginid());
		data.setUser_fname(user.getUser_fname());
		data.setUser_lname(user.getUser_lname());
		data.setUser_email(user.getUser_email());
		data.setUser_contact(user.getUser_contact());
		data.setUser_password(user.getUser_password());
		data.setUser_type(user.getUser_type());
		data.setUser_role(user.getUser_role());
		data.setUser_parent(user.getUser_parent());
		data.setModified_on(user.getModified_on());
		data.setModified_by(user.getModified_by());
		data.setIs_deleted(user.getIs_deleted());
		data.setIs_active(user.getIs_active());

		final Users result = userRepo.save(data);

		errorMsg = "user updated !";
		hasError = false;
		content.put("collection", result);
		content.put("error", hasError);
		content.put("message", errorMsg);
		response.put("content", content);
		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> deleteUserById(Integer id) throws ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;
		Users um = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found for this id :: " + id));

		userRepo.delete(um);
		errorMsg = "delted successfull !";
		content.put("error", hasError);
		content.put("message", errorMsg);
		response.put("content", content);

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> updateStatus(SoStatusPojo req, Integer so_id, String loginid)
			throws ResourceNotFoundException {
//		HashMap<String, Object> result= new HashMap<String, Object>();
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		Optional<Users> user = null;
		String so_status = null;
		Integer status = null;
		Integer reslt = null;
		boolean hasError = false;
		String errorMsg = null;

		logger.debug("data is : " + req);
		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Exist " + loginid));
		logger.debug("role is : " + user.get().getUser_role() + " and type is :" + user.get().getUser_type());

		if (user.get().getUser_role().equalsIgnoreCase("CFO") || user.get().getUser_type().equalsIgnoreCase("ADM")) {
			status = saleOrder.getStatus(so_id);

			if (status != 31 & status == 15 & req.getSo_status() == 16) {
				reslt = saleOrder.updateCfoRemark(req.getSo_status() + (status), req.getCfo_remark(),
						req.getCfo_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 31) {

					so_status = "cfo_approved";
					errorMsg = "status of CFO , updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "status of cfo , couldn't updated !";
					hasError = true;
					so_status = "cfo_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}
			} else if (status == 31) {

				errorMsg = "Already approved by CFO !";
				hasError = true;
				so_status = "approved !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 0) {

				errorMsg = "so is in draft mode ! please do final first ! ";
				hasError = true;
				so_status = "pending !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status <= 7) {

				reslt = saleOrder.updateCfoRemark(31, req.getCfo_remark(), req.getCfo_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 31) {

					so_status = "cfo_approved";
					errorMsg = "status of CFO , updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "status of cfo , couldn't updated !";
					hasError = true;
					so_status = "cfo_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}

			} else {
				errorMsg = "something went wrong !";
				hasError = true;
				content.put("message", errorMsg);
				content.put("error", hasError);

			}
		} else if (user.get().getUser_role().equalsIgnoreCase("CSD")) {

			status = saleOrder.getStatus(so_id);

			if (status != 15 & status == 7 & req.getSo_status() == 8) {

				reslt = saleOrder.updateZmRemark(req.getSo_status() + (status), req.getZm_remark(),
						req.getZm_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 15) {
					so_status = "zm_approved";
					errorMsg = "status of ZM , updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "so_status of ZM , couldn't updated !";
					hasError = true;
					so_status = "zm_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}
			} else if (status >= 15) {

				errorMsg = "Already approved by ZM !";
				hasError = true;
				so_status = "Approved !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 0) {

				errorMsg = "so is in draft mode ! please do final first ! ";
				hasError = true;
				so_status = "pending !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 1 || status == 3) {

				reslt = saleOrder.updateZmRemark(15, req.getZm_remark(), req.getZm_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 15) {
					so_status = "zm_approved";
					errorMsg = "status of ZM , updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "so_status of ZM , couldn't updated !";
					hasError = true;
					so_status = "zm_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}

			} else {
				errorMsg = "something wrong !";
				hasError = true;
				content.put("message", errorMsg);
				content.put("error", hasError);

			}

		} else if (user.get().getUser_role().equalsIgnoreCase("TAM")) {

			logger.debug(" welcome tam !");
			status = saleOrder.getStatus(so_id);
			logger.debug(" current status is : " + status);

			if (status != 7 & status == 3 & req.getSo_status() == 4) {
				reslt = saleOrder.updateTamRemark(req.getSo_status() + (status), req.getTam_remark(),
						req.getTam_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 7) {

					so_status = "tam_approved";
					errorMsg = "so_status of TAM updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "so_status of TAM couldn't updated !";
					hasError = true;
					so_status = "tam_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}
			} else if (status >= 7) {

				errorMsg = "Already approved by Team Area Manager !";
				hasError = true;
				so_status = "approved !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 0) {

				errorMsg = "so is in draft mode ! please do final first ! Or contact with admin";
				hasError = true;
				so_status = "pending !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 1) {

				reslt = saleOrder.updateTamRemark(7, req.getTam_remark(), req.getTam_approved_on(), so_id);
				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 7) {

					so_status = "tam_approved";
					errorMsg = "so_status of TAM updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {
					errorMsg = "so_status of TAM couldn't updated !";
					hasError = true;
					so_status = "tam_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}

			} else {
				errorMsg = "something wrong !";
				hasError = true;
				content.put("message", errorMsg);
				content.put("error", hasError);

			}
		} else if (user.get().getUser_role().equalsIgnoreCase("FSO")) {

			status = saleOrder.getStatus(so_id);
			logger.debug("current so_status in db : " + status);
			logger.debug("so_status req :" + req.getSo_status() + " am_remark req :" + req.getAm_remark()
					+ " am approved on : " + req.getAm_approved_on() + " so_id is : " + so_id);

			if (status != 3 && status == 1 & req.getSo_status() == 2) {
				reslt = saleOrder.updateAmRemark(req.getSo_status() + (status), req.getAm_remark(),
						req.getAm_approved_on(), so_id);

				status = saleOrder.getStatus(so_id);

				if (reslt != 0 & status == 3) {

					so_status = "am_approved";
					errorMsg = "so_status of AM , updated !";
					hasError = false;
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				} else {

					errorMsg = "so_status of AM , couldn't updated !";
					hasError = true;
					so_status = "am_approval pending !";
					content.put("so_status", so_status);
					content.put("message", errorMsg);
					content.put("error", hasError);

				}
			} else if (status >= 3) {

				errorMsg = "Already approved by Area Manager !";
				hasError = true;
				so_status = "approved !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else if (status == 0) {

				errorMsg = "so is in draft mode ! please do final first !";
				hasError = true;
				so_status = "pending !";
				content.put("so_status", so_status);
				content.put("message", errorMsg);
				content.put("error", hasError);

			} else {
				errorMsg = "something wrong !";
				hasError = true;
				content.put("message", errorMsg);
				content.put("error", hasError);

			}
		}

		response.put("content", content);
		return response;
	}

	@Override
	@Transactional
	public OpfResponse getOrderById(Integer id, String loginid) throws ResourceNotFoundException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> content = new HashMap<String, Object>();

		OpfResponse response = new OpfResponse();
		Optional<Users> user = null;

		List<ItemResponse> res2 = null;
		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Exist " + loginid));

		OrderResponse findByid = soRepo.getById(id);

		res2 = sijr.getItemDataBySoNo(findByid.getSo_no());

		result.put("sale_order", findByid);
		result.put("sale_item", res2);

		if (!result.isEmpty()) {
			content.put("collection", result);
			response.setContent(content);

		} else {

			content.put("collection", result);
			response.setError(true);
			response.setErrorcode(100001);
			response.setMessage("data not found !");
			response.setContent(content);
		}
		return response;
	}

	@Override
	@Transactional
	public OpfResponse getAllOrders(String loginid) throws ResourceNotFoundException {
		List<OrderResponse> result = new ArrayList<OrderResponse>();
		List<OrderResponse> res = null;
		HashMap<String, Object> content = new HashMap<String, Object>();
		OpfResponse response = new OpfResponse();

		Optional<Users> user = null;

		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Exist " + loginid));

		logger.debug("login_id  for getAllOrders is : " + loginid);
		logger.debug(
				"user id is :" + user.get().getUser_id() + " and " + "user_type is : " + user.get().getUser_type());

		if (user.get().getUser_role().equalsIgnoreCase("COD")) {

			logger.debug("Getting all data for COD ...");
			res = soRepo.getAllOrder();
			result.addAll(res);
		}

		if (user.get().getUser_type().equalsIgnoreCase("ADM") || user.get().getUser_role().equalsIgnoreCase("CFO")) {

			res = soRepo.getAllOrder();
			result.addAll(res);

		}

		if (user.get().getUser_role().equalsIgnoreCase("CSD")) {
			List<Integer> tamid = null;

			res = soRepo.getOrderById(user.get().getUser_id());
			result.addAll(res);

			try {
				tamid = userRepo.getChildList(user.get().getUser_id());
				logger.debug("child of zm (tam_id):" + tamid);

				for (Integer integer : tamid) {
					res = soRepo.getTamOrder(integer);
					result.addAll(res);
				}

			} catch (NullPointerException e) {
				tamid.add(-1);
			}

		}

		if (user.get().getUser_role().equalsIgnoreCase("TAM")) {
			List<Integer> amid = null;

			res = soRepo.getOrderById(user.get().getUser_id());
			result.addAll(res);

			try {
				amid = userRepo.getChildList(user.get().getUser_id());
				logger.debug("child of tam :(am_id) :" + amid);
				for (Integer integer : amid) {

					res = soRepo.getAmOrder(integer);
					result.addAll(res);
					logger.debug("data is : " + result.toString());
				}

				logger.debug("data is : " + result.toString());

			} catch (NullPointerException e) {

				amid.add(-1);
			}

		}

		if (user.get().getUser_role().equalsIgnoreCase("FSO")) {
			res = soRepo.getAmOrder(user.get().getUser_id());
			result.addAll(res);
		}

		if (!result.isEmpty()) {
			content.put("collection", result);
//			content.put("error", false);
//			content.put("message", "data is found !");
			response.setContent(content);

		} else {
//			content.put("error", true);
//			content.put("message", "data not found !");
			response.setError(true);
			response.setErrorcode(100001);
			response.setMessage("data not found !");
			response.setContent(content);
		}

		return response;

	}

	@Override
	@Transactional
	public HashMap<String, Object> deleteAllOrders(Integer id) throws ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
//		String errorMsg = null;
		Sale_order so = saleOrder.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found for this id :: " + id));

		saleOrder.delete(so);
		saleItem.deleteItem(so.getSo_no(), so.getSo_rev());

		hasError = false;
		content.put("deleted", Boolean.TRUE);
		content.put("error", hasError);
		response.put("content", content);

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> deleteItem(Integer id) throws ResourceNotFoundException {
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
//		String errorMsg = null;
		Sale_item si = saleItem.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found for this id :: " + id));

		saleItem.delete(si);

		hasError = false;
		content.put("deleted", Boolean.TRUE);
		content.put("error", hasError);
		response.put("content", content);

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> getSoGenerate(Long company_id, String date) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;
		String soinitial, so_date, nxtval, fy, final_fy = "";
		int financial_year;
		BigInteger val;
		try {
			val = query.getNextval();
			logger.debug("nextval : " + val);
			nxtval = String.format("%05d", val);

			soinitial = comp_repo.getSoIntial(company_id);
			soinitial.trim();
			logger.debug("so_initial : " + soinitial);

			so_date = date.substring(0, 10);
			logger.debug("so_date : " + so_date);

			financial_year = Utility.getFY(so_date.toString());
			fy = String.valueOf(financial_year);
			fy = fy.substring(2, 4);
			final_fy = so_date.toString().substring(0, 4) + "-" + fy;

			logger.debug("financial year : " + final_fy);
			soinitial = soinitial + "/" + final_fy + "/" + nxtval;

			logger.info("So Generate : " + soinitial);

			result.put("so_generate", soinitial);

			errorMsg = "data is found !";
			hasError = false;
			content.put("collection", result);
			content.put("error", hasError);
			content.put("message", errorMsg);
			response.put("content", content);

		} catch (NullPointerException e) {

			throw new ResourceNotFoundException("so_initial not found for this company_id : " + company_id);
		}

		return response;
	}

	@Override
	@Transactional
	public HashMap<String, Object> getUsersKey(String loginid) throws ResourceNotFoundException {

		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;
		Optional<Users> user = null;
		UserPair userk = new UserPair();
		List<UserPair> userkey = new ArrayList<UserPair>();

		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Exist " + loginid));

		if (user.get().getUser_fname().isEmpty()) {

			userk.setLoginid(user.get().getLoginid());
			userk.setUser_id(user.get().getUser_id());
			userkey.add(userk);

		} else if (!user.get().getUser_fname().isEmpty() && !user.get().getUser_lname().isEmpty()) {

			userk.setLoginid(user.get().getUser_fname() + " " + user.get().getUser_lname());
			userk.setUser_id(user.get().getUser_id());
			userkey.add(userk);

		} else if (!user.get().getUser_fname().isEmpty() && user.get().getUser_fname().isEmpty()) {

			userk.setLoginid(user.get().getUser_fname());
			userk.setUser_id(user.get().getUser_id());
			userkey.add(userk);

		} else {

			userk.setLoginid("Unknown");
			userk.setUser_id(user.get().getUser_id());
			userkey.add(userk);

		}

		if (user.get().getUser_role().equalsIgnoreCase("COD") || user.get().getUser_type().equalsIgnoreCase("ADM")) {

			List<UserPair> userkey1 = userRepo.getCodParentUserPair();

			if (!userkey1.isEmpty()) {
				errorMsg = "data is found !";
				hasError = false;
				content.put("collection", userkey1);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			} else {

				throw new ResourceNotFoundException("data not found !");
			}

		} else {

			errorMsg = "data is found !";
			hasError = false;
			content.put("collection", userkey);
			content.put("error", hasError);
			content.put("message", errorMsg);
			response.put("content", content);
		}

		return response;
	}

	@Override
	public HashMap<String, Object> getParent(String roleId) {

		HashMap<String, Object> content = new HashMap<String, Object>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		boolean hasError = false;
		String errorMsg = null;

		if (roleId.equalsIgnoreCase("FSO")) {

			List<UserParent> amParent = userRepo.getFsoParent();

			if (!amParent.isEmpty()) {
				errorMsg = "data is found !";
				hasError = false;
				content.put("collection", amParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			} else {

				errorMsg = "data is not found !";
				hasError = true;
				content.put("collection", amParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			}

		} else if (roleId.equalsIgnoreCase("TAM")) {

			List<UserParent> tamParent = userRepo.getTamParent();

			if (!tamParent.isEmpty()) {
				errorMsg = "data is found !";
				hasError = false;
				content.put("collection", tamParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			} else {

				errorMsg = "data is not found !";
				hasError = true;
				content.put("collection", tamParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			}
		} else if (roleId.equalsIgnoreCase("CSD") || roleId.equalsIgnoreCase("CFO")) {

			List<UserParent> zmParent = userRepo.getCsdParent();

			if (!zmParent.isEmpty()) {
				errorMsg = "data is found !";
				hasError = false;
				content.put("collection", zmParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			} else {

				errorMsg = "data is not found !";
				hasError = true;
				content.put("collection", zmParent);
				content.put("error", hasError);
				content.put("message", errorMsg);
				response.put("content", content);
			}
		} else if (roleId.equalsIgnoreCase("COD")) {

			errorMsg = "There is no Parent of 'COD' !";
			hasError = true;
			content.put("error", hasError);
			content.put("message", errorMsg);
			response.put("content", content);

		}
		return response;
	}

	@Override
	@Transactional
	public OpfResponse getDashBoardStatus(String loginid) throws Exception {

		HashMap<String, Object> content = new HashMap<String, Object>();
		OpfResponse response = new OpfResponse();

		Optional<Users> user = null;
		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("invalid token !"));
		SODashBoard dashboard = new SODashBoard();

		if (user.get().getUser_role().equalsIgnoreCase("COD")) {

			logger.debug("COD dashboard ...");

			dashboard.setTotal_so(saleOrder.getTotalSo());
			dashboard.setDraft_mode_so(saleOrder.getTotalDraftSo());
			dashboard.setFinal_so(saleOrder.getTotalFinalSo());
			dashboard.setPending_so(saleOrder.getTotalPendingSo());
			dashboard.setCfo_approved_so(saleOrder.getTotalCfoApprovedSo());

		}

		if (user.get().getUser_role().equalsIgnoreCase("FSO")) {
			logger.debug("AM dashboard ...");
			dashboard.setTotal_so(saleOrder.getTotalSoByFsoId(user.get().getUser_id()));
			dashboard.setDraft_mode_so(saleOrder.getTotalDraftSoByFsoId(user.get().getUser_id()));
			dashboard.setFinal_so(saleOrder.getTotalFinalSoByFsoId(user.get().getUser_id()));
			dashboard.setPending_so(saleOrder.getTotalPendingSoByFsoId(user.get().getUser_id()));
			dashboard.setCfo_approved_so(saleOrder.getTotalCfoApprovedSoByFsoId(user.get().getUser_id()));
		}

		if (user.get().getUser_role().equalsIgnoreCase("TAM")) {

			int total_so = 0, draft_so = 0, final_so = 0, pending_so = 0, cfo_approved = 0;
			List<Integer> amid = null;

			try {
				amid = userRepo.getChildList(user.get().getUser_id());
				for (Integer integer : amid) {

					total_so += saleOrder.getTotalSoByFsoId(integer);
					draft_so += saleOrder.getTotalDraftSoByFsoId(integer);
					final_so += saleOrder.getTotalFinalSoByFsoId(integer);
					pending_so += saleOrder.getTotalPendingSoByFsoId(integer);
					cfo_approved += saleOrder.getTotalCfoApprovedSoByFsoId(integer);

					logger.debug("amid : " + integer);
					logger.debug("totol_so : " + total_so);

				}

			} catch (NullPointerException e) {
				amid.add(-1);
			}

			logger.debug("increament : " + total_so);
			total_so += saleOrder.getTotalSoByUserId(user.get().getUser_id());
			draft_so += saleOrder.getTotalDraftSoByUserId(user.get().getUser_id());
			final_so += saleOrder.getTotalFinalSoByUserId(user.get().getUser_id());
			pending_so += saleOrder.getTotalPendingSoByUserId(user.get().getUser_id());
			cfo_approved += saleOrder.getTotalCfoApprovedSoByUserId(user.get().getUser_id());
			logger.debug("increament : " + total_so);

			dashboard.setTotal_so(total_so);
			dashboard.setDraft_mode_so(draft_so);
			dashboard.setFinal_so(final_so);
			dashboard.setPending_so(pending_so);
			dashboard.setCfo_approved_so(cfo_approved);
		}

		if (user.get().getUser_role().equalsIgnoreCase("CSD")) {

			int total_so = 0, draft_so = 0, final_so = 0, pending_so = 0, cfo_approved = 0;
			List<Integer> tamid = null;

			try {
				tamid = userRepo.getChildList(user.get().getUser_id());
				for (Integer integer : tamid) {

					total_so += saleOrder.getTotalSoByTamId(integer);
					draft_so += saleOrder.getTotalDraftSoByTamId(integer);
					final_so += saleOrder.getTotalFinalSoByTamId(integer);
					pending_so += saleOrder.getTotalPendingSoByTamId(integer);
					cfo_approved += saleOrder.getTotalCfoApprovedSoByTamId(integer);

//					try {
//						amid = userRepo.getChildList(integer);
//
//						for (Integer integer2 : amid) {
//
//							total_so += saleOrder.getTotalSoByFsoId(integer2);
//							draft_so += saleOrder.getTotalDraftSoByFsoId(integer2);
//							final_so += saleOrder.getTotalFinalSoByFsoId(integer2);
//							pending_so += saleOrder.getTotalPendingSoByFsoId(integer2);
//							cfo_approved += saleOrder.getTotalCfoApprovedSoByFsoId(integer2);
//						}
//
//					} catch (NullPointerException e) {
//						amid.add(-1);
//					}

				}

			} catch (NullPointerException e) {
				tamid.add(-1);
			}
			logger.debug("total so  : " + total_so);

			total_so += saleOrder.getTotalSoByUserId(user.get().getUser_id());
			draft_so += saleOrder.getTotalDraftSoByUserId(user.get().getUser_id());
			final_so += saleOrder.getTotalFinalSoByUserId(user.get().getUser_id());
			pending_so += saleOrder.getTotalPendingSoByUserId(user.get().getUser_id());
			cfo_approved += saleOrder.getTotalCfoApprovedSoByUserId(user.get().getUser_id());

			logger.debug("total so  : " + total_so);
			logger.debug("tamid : " + tamid);

			dashboard.setTotal_so(total_so);
			dashboard.setDraft_mode_so(draft_so);
			dashboard.setFinal_so(final_so);
			dashboard.setPending_so(pending_so);
			dashboard.setCfo_approved_so(cfo_approved);

		}

		if (user.get().getUser_role().equalsIgnoreCase("CFO")) {

			logger.debug("CFO dashboard ...");
			dashboard.setTotal_so(saleOrder.getTotalSo());
			dashboard.setDraft_mode_so(saleOrder.getTotalDraftSo());
			dashboard.setFinal_so(saleOrder.getTotalFinalSo());
			dashboard.setPending_so(saleOrder.getTotalPendingSo());
			dashboard.setCfo_approved_so(saleOrder.getTotalCfoApprovedSo());

		}

		if (user.get().getUser_type().equalsIgnoreCase("ADM")) {

			logger.debug("ADM dashboard ...");
			dashboard.setTotal_so(saleOrder.getTotalSo());
			dashboard.setDraft_mode_so(saleOrder.getTotalDraftSo());
			dashboard.setFinal_so(saleOrder.getTotalFinalSo());
			dashboard.setPending_so(saleOrder.getTotalPendingSo());
			dashboard.setCfo_approved_so(saleOrder.getTotalCfoApprovedSo());

		}

		content.put("collection", dashboard);
		response.setContent(content);
		return response;
	}

	@Override
	@Transactional
	public OpfResponse addRevision(Revision_Request req, Integer so_id, String loginid)
			throws ResourceNotFoundException {

		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> content = new HashMap<String, Object>();
		OpfResponse response = new OpfResponse();
		Optional<Users> user = null;
		Sale_order res = null;

		user = userRepo.findByLoginid(loginid);
		user.orElseThrow(() -> new UsernameNotFoundException("invalid token !"));

//		Integer so_status = saleOrder.getStatus(so_id);

		Rev_history order = req.getSale_order();

		List<Sale_item> item = new ArrayList<Sale_item>();
		logger.debug("req ..." + req.toString());

		if (order.getSo_id() != null) {
			logger.debug(">>> UPDATE method going to executed ...On so_id :" + order.getSo_id());

			Sale_order so = saleOrder.findById(so_id)
					.orElseThrow(() -> new ResourceNotFoundException("Data not found so_id :: " + so_id + "\n"));

			logger.debug("so data for so update : " + so);

			so = Utility.setValu(so, order, "update");

//			so.setSo_status(so_id);

			res = saleOrder.save(so);
			result.put("sale_order", res);

			logger.debug("OpfApplication ::" + "sale order updated !" + "\n");

		}
		if (order.getSo_id() == null) {

			logger.debug(">>> INSERT method going to executed ...");

			Sale_order so = new Sale_order();
			so = Utility.setValu(so, order, "insert");
			logger.debug("so data for so insert : " + so);
			res = saleOrder.save(so);
			result.put("sale_order", res);
			
			logger.debug(" so inserted : " + res);

		}

		if (order.getRev_flag() == 1) {
			Revision_history rh = new Revision_history();

			rh = Utility.setValu(order, res.getSo_id());

			logger.debug("INSERT revision history : " + rh);

			revisionRepo.save(rh);
		}

		List<Sale_item> si = req.getSale_item();
		Sale_item result2 = new Sale_item();

		for (Sale_item sale_item : si) {

			if (sale_item.getItem_id() == null) {
				
				logger.debug("INSERTING ITEM  : " );


				sale_item.setSo_no(order.getSo_no());
				sale_item.setSo_rev(order.getSo_rev());
				sale_item.setCreated_by(req.getSale_order().getCreated_by());
				sale_item.setCreated_on(req.getSale_order().getCreated_on());

				result2 = saleItem.save(sale_item);

				item.add(result2);
				result.put("sale_item", item);

			}else {

				logger.debug("DELETING ITEM  : " );
				
				int isdelted = itemDeleteById(sale_item.getItem_id());
				
				logger.debug("delted item " +isdelted );
				
				logger.debug(" item data req " +sale_item );
				
				Sale_item si2 = new Sale_item();
	
				si2.setSo_no(sale_item.getSo_no());
				si2.setSo_rev(sale_item.getSo_rev());
				si2.setHsn_code(sale_item.getHsn_code());
				si2.setPart_no(sale_item.getPart_no());
				si2.setDescription(sale_item.getDescription());
				si2.setTotal_qty(sale_item.getTotal_qty());
				si2.setUnit_sale_price(sale_item.getUnit_sale_price());
				si2.setSale_amt_wot(sale_item.getSale_amt_wot());
				si2.setSale_gst(sale_item.getSale_gst());
				si2.setSale_wt(sale_item.getSale_wt());
				si2.setUnit_purchase_price(sale_item.getUnit_purchase_price());
				si2.setPurchase_amt_wot(sale_item.getPurchase_amt_wot());
				si2.setPurchase_gst(sale_item.getPurchase_gst());
				si2.setPurchase_wt(sale_item.getPurchase_wt());
				si2.setProfit(sale_item.getProfit());
				si2.setProfit_per(sale_item.getProfit_per());
				si2.setQuotation1_id(sale_item.getQuotation1_id());
				si2.setQuotation2_id(sale_item.getQuotation2_id());
				si2.setSort_order(sale_item.getSort_order());
				
				sale_item.setCreated_by(req.getSale_order().getCreated_by());
				sale_item.setCreated_on(Utility.getCurrentTime());
				
				si2.setIs_deleted(req.getSale_order().getIs_deleted());
				si2.setExpense_type(sale_item.getExpense_type());

				logger.debug("item data is  :" + si2 + "\n");
				
				result2 = saleItem.save(si2);
				logger.debug("updated sale item :" + result2 + "\n");
				item.add(result2);
				result.put("sale_item", item);

			}

		}

		
		content.put("collection", result);
		response.setContent(content);

		return response;
	}

	@Override
	@Transactional
	public OpfResponse getRevisionList(RevisionRequest rr, String loginid) throws ResourceNotFoundException {

		OpfResponse response = new OpfResponse();
		HashMap<String, Object> content = new HashMap<String, Object>();
		List<Revision_history> result = revisionRepo.findAllRevisionBySoId(rr.getSo_no());
		Rev_history res = null;

		List<Rev_history> ress = new ArrayList<Rev_history>();

		for (Revision_history revision_history : result) {

			res = Utility.setValu(revision_history);
			ress.add(res);

		}

		logger.debug("So_no " + rr.getSo_no());
		if (!result.isEmpty()) {
			logger.debug("data is : " + result);

			content.put("collection", ress);
			response.setContent(content);

		} else {
			response.setContent(ress);
			response.setErrorcode(100001);
			response.setError(true);
			response.setMessage("data not found !");
		}

		return response;
	}

	@Override
	@Transactional
	public OpfResponse getRevisionDetails(RevisionRequest rr, String loginid) throws ResourceNotFoundException {
		OpfResponse response = new OpfResponse();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> r = new HashMap<String, Object>();
		List<ItemResponse> res2 = null;
//		Revision_history result = revisionRepo.findAllRevisionDetails(rr.getSo_rev(), rr.getSo_no());
//		Rev_history rh = Utility.setValu(result);

		OrderResponse rh = rev_join.getHistoryBySoAndRevNo(rr.getSo_rev(), rr.getSo_no());

		if (rh != null) {

			res2 = sijr.getItemDataBySoAndRevNo(rh.getSo_no(), rh.getSo_rev());

			r.put("sale_item", res2);
			r.put("revision_data", rh);

		}

		if (!r.isEmpty()) {

			map.put("collection", r);
			response.setContent(map);

		} else {

			map.put("collection", rh);
			response.setContent(map);
			response.setError(true);
			response.setErrorcode(100001);
			response.setMessage("data not found !");

		}

		return response;
	}

	@Override
	@Transactional
	public OpfResponse getExpenseType(Integer id) throws ResourceNotFoundException {

		OpfResponse response = new OpfResponse();
		HashMap<String, Object> content = new HashMap<String, Object>();
		List<Expense_type_list> res = expense_repo.getExpenseTypeList();

		if (res != null) {
			content.put("collection", res);
			response.setContent(content);

		} else {

			content.put("collection", res);
			response.setContent(content);
			response.setError(true);
			response.setErrorcode(100001);
			response.setMessage("data not found !");

		}

		return response;
	}

	@Transactional
	@Override
	public OpfResponse getExpenseDetails(Integer id) throws ResourceNotFoundException {

		OpfResponse response = new OpfResponse();
		HashMap<String, Object> content = new HashMap<String, Object>();
		Expense_type_detail res = expense_repo.getExpenseDetails(id);
//		Other_Expense_Type  res= expense_repo.getExpenseDetails(type);

		if (res != null) {
			content.put("collection", res);
			response.setContent(content);

		} else {
			content.put("collection", "data not available !");
			response.setContent(content);
			response.setError(true);
			response.setErrorcode(100001);
			response.setMessage("data not found !");
		}

		return response;
	}

	@Transactional
	@Override
	public OpfResponse updatePassWordById(User_details user, Integer id) throws ResourceNotFoundException {

		HashMap<String, Object> content = new HashMap<String, Object>();
		OpfResponse response = new OpfResponse();

		if (user.getPassword() != user.getConfirm_password()) {

			content.put("message", "password do not matching !");
			response.setContent(content);
			response.setError(true);
			response.setMessage("password not updated !");

		} else {

			int result = userRepo.updatePassword(user.getPassword(), id);

			if (result != 0) {

				content.put("message", "password changed !");
				response.setContent(content);
				response.setMessage("password  updated !");

			} else {
				response.setError(true);
				response.setErrorcode(100002);
				response.setMessage("password not updated !");
			}
		}

		return response;
	}

	
	@Transactional
	public Integer itemDeleteById(Integer id) throws ResourceNotFoundException {
		
		
		Sale_item si = saleItem.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data not found for this id :: " + id));

		 saleItem.delete(si);


		return 1;
	}
	
	
}