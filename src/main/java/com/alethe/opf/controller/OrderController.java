package com.alethe.opf.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.alethe.opf.dto.RevisionRequest;
import com.alethe.opf.dto.Revision_Request;
import com.alethe.opf.dto.SoRequest;
import com.alethe.opf.dto.SoStatusPojo;
import com.alethe.opf.exception.ResourceNotFoundException;
import com.alethe.opf.opfResponse.BaseOpfResponse;
import com.alethe.opf.opfResponse.OpfResponse;
import com.alethe.opf.repository.Token_destory_repo;
import com.alethe.opf.service.UserServiceI;
import com.alethe.opf.utility.JwtUtil;

/**
 * Created by Kunal Kumar
 */
@RestController
public class OrderController {

	@Autowired
	private UserServiceI orderService;

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private Token_destory_repo tokenDestroy;

	private static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@PreAuthorize("hasRole('ADM') or hasRole('COD') or hasRole('TAM') or hasRole('CFO') or hasRole('CSD') or hasRole('FSO')")
	@CrossOrigin
	@GetMapping("/sale_order/getById/{order_id}")
	public Object getOrderById(@PathVariable(value = "order_id") Integer orderId,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {
		OpfResponse result = null;
		String loginid = null;
		String serverToken = "";
		boolean isEqual = false;

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		loginid = jwtutil.extractUsername(token);

		try {
			serverToken = tokenDestroy.getToken(token);

		} catch (NullPointerException n) {
			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("Your Session Expired , Because you are logged in another device !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}

		if (serverToken != null) {
			isEqual = serverToken.contentEquals(token.trim());
			logger.debug("is token matching  : " + isEqual);
		}

		if (isEqual && serverToken != null) {
			result = orderService.getOrderById(orderId, loginid);
		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("You are Logged in another devices !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);

		}
		return ResponseEntity.ok().body(result);
	}


	@PreAuthorize("hasRole('ADM') or hasRole('TAM') or hasRole('CFO') or hasRole('FSO') or hasRole('CSD')")
	@CrossOrigin
	@PutMapping("/sale_order/updateStatusById/{so_id}")
	public Object updateStatus(@PathVariable(value = "so_id") Integer so_id, @RequestBody SoStatusPojo req,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {
		String loginid = null;
		HashMap<String, Object> result = new HashMap<>();
		String serverToken = "";
		boolean isEqual = false;

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		loginid = jwtutil.extractUsername(token);

		logger.debug(req.toString());

		try {
			serverToken = tokenDestroy.getToken(token);

		} catch (NullPointerException n) {

			result.put("message", "session expired !");
		}

		if (serverToken != null) {
			isEqual = serverToken.contentEquals(token.trim());
			logger.debug("is token matching  : " + isEqual);
		}

		if (isEqual && serverToken != null) {

			if (req.getSo_status() != null) {

				logger.debug("so_id request :" + so_id + " , loginid req : " + loginid);
				result = orderService.updateStatus(req, so_id, loginid);
			} else {

				result.put("message", "so_status must be present !");
			}
		} else {

			result.put("message", "You are Logged in another devices !");

			return result;

		}

		return ResponseEntity.ok(result);
	}

	@CrossOrigin
	@DeleteMapping("/sale_order/deleteById/{order_id}")
	public HashMap<String, Object> deleteOrder(@PathVariable(value = "order_id") Integer orderId,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {
		HashMap<String, Object> result = new HashMap<>();
		String serverToken = "";
		boolean isEqual = false;

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
//		String loginid = jwtutil.extractUsername(token);

		try {
			serverToken = tokenDestroy.getToken(token);

		} catch (NullPointerException n) {

			result.put("message", "You are Logged in another devices !");
		}

		if (serverToken != null) {
			isEqual = serverToken.contentEquals(token.trim());
			logger.debug("is token matching  : " + isEqual);
		}

		if (isEqual && serverToken != null) {
			result = orderService.deleteAllOrders(orderId);
		} else {

			result.put("message", "You are Logged in another devices !");

			return result;
		}
		return result;
	}

	@CrossOrigin
	@DeleteMapping("/sale_item/deleteById/{item_id}")
	public HashMap<String, Object> deleteItem(@PathVariable(value = "item_id") Integer itemId,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {
		HashMap<String, Object> result = new HashMap<>();

		result = orderService.deleteItem(itemId);

		return result;
	}

	@PreAuthorize("hasRole('ADM') or hasRole('COD') or hasRole('TAM') or hasRole('CFO') or hasRole('CSD') or hasRole('FSO')")
	@CrossOrigin
	@GetMapping("/sale_order/getAllOrder")
	public Object getOrder(@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {

		String loginid = null;
//		HashMap<String, Object> result = new HashMap<>();
		String serverToken = "";
		boolean isEqual = false;

		if (token != null && token.startsWith("Bearer ")) {
			
			token = token.substring(7);
		}
		loginid = jwtutil.extractUsername(token);

		try {
			serverToken = tokenDestroy.getToken(token);

		} catch (NullPointerException n) {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("You are Logged in another devices !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}

		if (serverToken != null) {
			isEqual = serverToken.contentEquals(token.trim());
			logger.debug("is token matching  : " + isEqual);
		}

		if (isEqual) {

			return orderService.getAllOrders(loginid);

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("You are Logged in another devices !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);

		}

	}

	@PreAuthorize("hasRole('ADM') or hasRole('COD') or hasRole('TAM') or hasRole('CFO') or hasRole('CSD') or hasRole('FSO')")
	@CrossOrigin
	@GetMapping("/getDashBoardStatus")
	public ResponseEntity<? extends BaseOpfResponse> Dashboard(@RequestHeader(name = "Authorization") String token) throws Exception {
		String loginid = "";
		OpfResponse result = new OpfResponse();
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		loginid = jwtutil.extractUsername(token);
		result = orderService.getDashBoardStatus(loginid);

		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);
	}

	@PreAuthorize("hasRole('ADM') or hasRole('COD') or hasRole('TAM') or hasRole('CFO') or hasRole('CSD') or hasRole('FSO')")
	@CrossOrigin
	@PostMapping("/sale_order/updateRevisionBySoId")
	public ResponseEntity<? extends BaseOpfResponse> revisionAdd(@RequestBody Revision_Request order,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {

		OpfResponse result = null;
		String serverToken = "";
		serverToken = getToken(token).get("serverToken").toString();
		if (serverToken != null) {

			result = orderService.addRevision(order, order.getSale_order().getSo_id(),
					getToken(token).get("loginid").toString());

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("You are Logged in another devices !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);
	}

	@CrossOrigin
	@PostMapping("/sale_order/getRevisionBySoIdAndNo")
	public ResponseEntity<? extends BaseOpfResponse> getRevisionById(@RequestBody RevisionRequest rr,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {

		OpfResponse result = null;
		String serverToken = "";
		serverToken = getToken(token).get("serverToken").toString();

		if (serverToken != null) {

			logger.debug("request body  : " + rr.toString());
			try {
				result = orderService.getRevisionList(rr, getToken(token).get("loginid").toString());
			
			} catch (ResourceNotFoundException e) {
				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(107);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			
			} catch (Exception e) {
				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(000);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			}

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("Can not Logged in multiple devices !");
			response.setError(true);
			response.setErrorcode(105);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);

	}

	@CrossOrigin
	@PostMapping(value = "/sale_order/getRevisionDetailsByRevisionIdAndSoNo")
	public ResponseEntity<? extends BaseOpfResponse> getRevisionDetails(@RequestBody RevisionRequest rr,
			@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {

		OpfResponse result = null;
		String serverToken = "";
		serverToken = getToken(token).get("serverToken").toString().trim();

		logger.debug("\n" + serverToken + "\n" + token);

		if (serverToken != null) {

			logger.debug("request body  : " + rr.toString());

			try {
				result = orderService.getRevisionDetails(rr, getToken(token).get("loginid").toString());
			
			} catch (ResourceNotFoundException e) {
				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(107);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			
			} catch (Exception e) {

				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(000);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			}

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("Invalid token !");
			response.setErrorcode(105);
			response.setError(true);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);

	}

	@CrossOrigin
	@GetMapping(value = "/sale_order/getExpenseType")
	public ResponseEntity<? extends BaseOpfResponse> getExpenseType(@RequestHeader(name = "Authorization") String token)
			throws ResourceNotFoundException {

		OpfResponse result = null;
		String serverToken = "";
		serverToken = getToken(token).get("serverToken").toString().trim();

		logger.debug("\n" + serverToken + "\n" + token);

		if (serverToken != null) {

			try {
			
				result = orderService.getExpenseType(0);
			
			} catch (ResourceNotFoundException e) {
				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(107);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			
			} catch (Exception e) {

				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(000);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			}

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("Invalid token !");
			response.setErrorcode(105);
			response.setError(true);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);

	}

	@CrossOrigin
	@GetMapping(value = "/sale_order/getExpenseType/{id}")
	public ResponseEntity<? extends BaseOpfResponse> getExpenseDetails(
			@RequestHeader(name = "Authorization") String token,
			@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {

		OpfResponse result = null;
		String serverToken = "";
		serverToken = getToken(token).get("serverToken").toString().trim();

		logger.debug("\n" + serverToken + "\n" + token);

		if (id == null) {
			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("param 'type' is not found !");
			response.setError(true);
			response.setErrorcode(000);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}

		if (serverToken != null) {

			try {
				result = orderService.getExpenseDetails(id);
			
			} catch (ResourceNotFoundException e) {
				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.getMessage());
				response.setError(true);
				response.setErrorcode(107);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			
			} catch (Exception e) {

				BaseOpfResponse response = new BaseOpfResponse();
				response.setMessage(e.toString());
				response.setError(true);
				response.setErrorcode(000);
				return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
			}

		} else {

			BaseOpfResponse response = new BaseOpfResponse();
			response.setMessage("Invalid token !");
			response.setErrorcode(105);
			response.setError(true);
			return new ResponseEntity<BaseOpfResponse>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<OpfResponse>(result, HttpStatus.ACCEPTED);

	}

	private HashMap<String, Object> getToken(String token) {

		HashMap<String, Object> result = new HashMap<>();

		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		result.put("loginid", jwtutil.extractUsername(token));
		try {

			result.put("serverToken", tokenDestroy.getToken(token));
			result.put("token", token);

		} catch (NullPointerException n) {

			result.put("message", "session expired !");
		}
		return result;

	}

}
