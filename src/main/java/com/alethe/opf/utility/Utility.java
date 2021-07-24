package com.alethe.opf.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alethe.opf.entity.Rev_history;
import com.alethe.opf.entity.Revision_Emb_Id;
import com.alethe.opf.entity.Revision_history;
import com.alethe.opf.entity.Sale_order;
import com.alethe.opf.service.UserServiceImpl;

/**
 * Created by Kunal Kumar
 */
public class Utility {

	private static Logger logger = LoggerFactory.getLogger(Utility.class);

	public static Rev_history setValu(Revision_history req) {

		Rev_history rh = new Rev_history();

		rh.setSo_no(req.getRev_id().getSo_no());
		rh.setSo_rev(req.getRev_id().getSo_rev());
		rh.setSo_id(req.getSo_id());
		rh.setSo_type(req.getSo_type());
		rh.setTam(req.getTam());
		rh.setCompany_id(req.getCompany_id());
		rh.setSo_date(req.getSo_date());
		rh.setRevision_add_on(req.getRevision_add_on());
		rh.setRevision_add_by(req.getCreated_by());
		rh.setSo_reference(req.getSo_reference());
		rh.setPo_no(req.getPo_no());
		rh.setPo_date(req.getPo_date());
		rh.setSo_category_id(req.getSo_category_id());
		rh.setBusiness_unit_id(req.getBusiness_unit_id());
		rh.setAm(req.getAm());
		rh.setCustomer_id(req.getCustomer_id());
		rh.setCustomer_name(req.getCustomer_name());
		rh.setCustomer_billing_name(req.getCustomer_billing_name());
		rh.setCustomer_billing_address(req.getCustomer_billing_address());
		rh.setCustomer_dispatch_address(req.getCustomer_dispatch_address());
		rh.setCustomer_gstn(req.getCustomer_gstn());
		rh.setCustomer_segement(req.getCustomer_segement());
		rh.setBusiness_nature_id(req.getBusiness_nature_id());
		rh.setPrimary_contact_name(req.getPrimary_contact_name());
		rh.setPrimary_contact_phone(req.getPrimary_contact_phone());
		rh.setPrimary_contact_email(req.getPrimary_contact_email());
		rh.setSecondary_contact_name(req.getSecondary_contact_name());
		rh.setSecondary_contact_phone(req.getSecondary_contact_phone());
		rh.setSecondary_contact_email(req.getSecondary_contact_email());
		rh.setTotal_purchase_amount(req.getTotal_purchase_amount());
		rh.setTotal_sale_amount(req.getTotal_sale_amount());
		rh.setMargin_amount(req.getMargin_amount());
		rh.setMargin_per(req.getMargin_per());
		rh.setPo_attach1_id(req.getPo_attach1_id());
		rh.setPo_attach2_id(req.getPo_attach2_id());
		rh.setSo_attach1_id(req.getSo_attach1_id());
		rh.setSo_attach2_id(req.getSo_attach2_id());
		rh.setDelivery_instruction(req.getDelivery_instruction());
		rh.setOther_expenses(req.getOther_expenses());
		rh.setPayment_term(req.getPayment_term());
		rh.setAm_remark(req.getAm_remark());
		rh.setAm_approved_on(req.getAm_approved_on());
		rh.setTam_remark(req.getTam_remark());
		rh.setTam_approved_on(req.getTam_approved_on());
		rh.setZm_remark(req.getZm_remark());
		rh.setZm_approved_on(req.getZm_approved_on());
		rh.setCfo_remark(req.getCfo_remark());
		rh.setCfo_approved_on(req.getCfo_approved_on());
		rh.setSo_status(req.getSo_status());
		rh.setCreated_on(req.getCreated_on());
		rh.setCreated_by(req.getCreated_by());
		rh.setModified_on(req.getModified_on());
		rh.setModified_by(req.getModified_by());
		rh.setIs_deleted(req.getIs_deleted());

		return rh;
	}

	public static Sale_order setValu(Sale_order so, Rev_history order, String flag) {

		logger.debug(" utility so update req data :" + so + " \n" + " order " + order);
		if (flag == "update") {

			so.setTam(order.getTam());
			so.setSo_no(order.getSo_no());
			so.setCompany_id(order.getCompany_id());
			so.setSo_date(order.getSo_date());
			so.setSo_rev(order.getSo_rev());
			so.setSo_reference(order.getSo_reference());
			so.setPo_no(order.getPo_no());
			so.setPo_date(order.getPo_date());
			so.setSo_category_id(order.getSo_category_id());
			so.setBusiness_unit_id(order.getBusiness_unit_id());
			so.setAm(order.getAm());
			so.setCustomer_id(order.getCustomer_id());
			so.setCustomer_name(order.getCustomer_name());
			so.setCustomer_billing_name(order.getCustomer_billing_name());
			so.setCustomer_billing_address(order.getCustomer_billing_address());
			so.setCustomer_dispatch_address(order.getCustomer_dispatch_address());
			so.setCustomer_gstn(order.getCustomer_gstn());
			so.setCustomer_segement(order.getCustomer_segement());
			so.setBusiness_nature_id(order.getBusiness_nature_id());
			so.setPrimary_contact_name(order.getPrimary_contact_name());
			so.setPrimary_contact_phone(order.getPrimary_contact_phone());
			so.setPrimary_contact_email(order.getPrimary_contact_email());
			so.setSecondary_contact_name(order.getSecondary_contact_name());
			so.setSecondary_contact_phone(order.getSecondary_contact_phone());
			so.setSecondary_contact_email(order.getSecondary_contact_email());
			so.setTotal_purchase_amount(order.getTotal_purchase_amount());
			so.setTotal_sale_amount(order.getTotal_sale_amount());
			so.setMargin_amount(order.getMargin_amount());
			so.setMargin_per(order.getMargin_per());
			so.setPo_attach1_id(order.getPo_attach1_id());
			so.setPo_attach2_id(order.getPo_attach2_id());
			so.setSo_attach1_id(order.getSo_attach1_id());
			so.setSo_attach2_id(order.getSo_attach2_id());
			so.setDelivery_instruction(order.getDelivery_instruction());
			so.setOther_expenses(order.getOther_expenses());
			so.setPayment_term(order.getPayment_term());
			so.setAm_remark(order.getAm_remark());
			so.setAm_approved_on(order.getAm_approved_on());
			so.setTam_remark(order.getTam_remark());
			so.setTam_approved_on(order.getTam_approved_on());
			so.setZm_remark(order.getZm_remark());
			so.setZm_approved_on(order.getZm_approved_on());
			so.setCfo_remark(order.getCfo_remark());
			so.setCfo_approved_on(order.getCfo_approved_on());
			so.setSo_status(order.getSo_status());
			so.setModified_on(order.getModified_on());
			so.setModified_by(order.getModified_by());
			so.setIs_deleted(order.getIs_deleted());
			logger.debug("so : " + so);
		}

		if (flag == "insert") {

			so.setTam(order.getTam());
			so.setSo_no(order.getSo_no());
			so.setCompany_id(order.getCompany_id());
			so.setSo_date(order.getSo_date());
			so.setSo_rev(order.getSo_rev());
			so.setSo_reference(order.getSo_reference());
			so.setPo_no(order.getPo_no());
			so.setPo_date(order.getPo_date());
			so.setSo_category_id(order.getSo_category_id());
			so.setBusiness_unit_id(order.getBusiness_unit_id());
			so.setAm(order.getAm());
			so.setCustomer_id(order.getCustomer_id());
			so.setCustomer_name(order.getCustomer_name());
			so.setCustomer_billing_name(order.getCustomer_billing_name());
			so.setCustomer_billing_address(order.getCustomer_billing_address());
			so.setCustomer_dispatch_address(order.getCustomer_dispatch_address());
			so.setCustomer_gstn(order.getCustomer_gstn());
			so.setCustomer_segement(order.getCustomer_segement());
			so.setBusiness_nature_id(order.getBusiness_nature_id());
			so.setPrimary_contact_name(order.getPrimary_contact_name());
			so.setPrimary_contact_phone(order.getPrimary_contact_phone());
			so.setPrimary_contact_email(order.getPrimary_contact_email());
			so.setSecondary_contact_name(order.getSecondary_contact_name());
			so.setSecondary_contact_phone(order.getSecondary_contact_phone());
			so.setSecondary_contact_email(order.getSecondary_contact_email());
			so.setTotal_purchase_amount(order.getTotal_purchase_amount());
			so.setTotal_sale_amount(order.getTotal_sale_amount());
			so.setMargin_amount(order.getMargin_amount());
			so.setMargin_per(order.getMargin_per());
			so.setPo_attach1_id(order.getPo_attach1_id());
			so.setPo_attach2_id(order.getPo_attach2_id());
			so.setSo_attach1_id(order.getSo_attach1_id());
			so.setSo_attach2_id(order.getSo_attach2_id());
			so.setDelivery_instruction(order.getDelivery_instruction());
			so.setOther_expenses(order.getOther_expenses());
			so.setPayment_term(order.getPayment_term());
			so.setAm_remark(order.getAm_remark());
			so.setAm_approved_on(order.getAm_approved_on());
			so.setTam_remark(order.getTam_remark());
			so.setTam_approved_on(order.getTam_approved_on());
			so.setZm_remark(order.getZm_remark());
			so.setZm_approved_on(order.getZm_approved_on());
			so.setCfo_remark(order.getCfo_remark());
			so.setCfo_approved_on(order.getCfo_approved_on());
			so.setSo_status(order.getSo_status());
			so.setCreated_on(order.getCreated_on());
			so.setCreated_by(order.getCreated_by());
			so.setIs_deleted(order.getIs_deleted());
		}

		return so;

	}

	public static Revision_history setValu(Rev_history req, Integer so_id) {

		Revision_history rh = new Revision_history();
		Revision_Emb_Id id = new Revision_Emb_Id();

			id.setSo_no(req.getSo_no());
			id.setSo_rev(req.getSo_rev());
			rh.setRev_id(id);

			rh.setSo_id(so_id);
			rh.setSo_type("SO");
			rh.setTam(req.getTam());
			rh.setCompany_id(req.getCompany_id());
			rh.setSo_date(req.getSo_date());
			rh.setRevision_add_on(getCurrentTime());
			rh.setRevision_add_by(req.getCreated_by());
			rh.setSo_reference(req.getSo_reference());
			rh.setPo_no(req.getPo_no());
			rh.setPo_date(req.getPo_date());
			rh.setSo_category_id(req.getSo_category_id());
			rh.setBusiness_unit_id(req.getBusiness_unit_id());
			rh.setAm(req.getAm());
			rh.setCustomer_id(req.getCustomer_id());
			rh.setCustomer_name(req.getCustomer_name());
			rh.setCustomer_billing_name(req.getCustomer_billing_name());
			rh.setCustomer_billing_address(req.getCustomer_billing_address());
			rh.setCustomer_dispatch_address(req.getCustomer_dispatch_address());
			rh.setCustomer_gstn(req.getCustomer_gstn());
			rh.setCustomer_segement(req.getCustomer_segement());
			rh.setBusiness_nature_id(req.getBusiness_nature_id());
			rh.setPrimary_contact_name(req.getPrimary_contact_name());
			rh.setPrimary_contact_phone(req.getPrimary_contact_phone());
			rh.setPrimary_contact_email(req.getPrimary_contact_email());
			rh.setSecondary_contact_name(req.getSecondary_contact_name());
			rh.setSecondary_contact_phone(req.getSecondary_contact_phone());
			rh.setSecondary_contact_email(req.getSecondary_contact_email());
			rh.setTotal_purchase_amount(req.getTotal_purchase_amount());
			rh.setTotal_sale_amount(req.getTotal_sale_amount());
			rh.setMargin_amount(req.getMargin_amount());
			rh.setMargin_per(req.getMargin_per());
			rh.setPo_attach1_id(req.getPo_attach1_id());
			rh.setPo_attach2_id(req.getPo_attach2_id());
			rh.setSo_attach1_id(req.getSo_attach1_id());
			rh.setSo_attach2_id(req.getSo_attach2_id());
			rh.setDelivery_instruction(req.getDelivery_instruction());
			rh.setOther_expenses(req.getOther_expenses());
			rh.setPayment_term(req.getPayment_term());
			rh.setAm_remark(req.getAm_remark());
			rh.setAm_approved_on(req.getAm_approved_on());
			rh.setTam_remark(req.getTam_remark());
			rh.setTam_approved_on(req.getTam_approved_on());
			rh.setZm_remark(req.getZm_remark());
			rh.setZm_approved_on(req.getZm_approved_on());
			rh.setCfo_remark(req.getCfo_remark());
			rh.setCfo_approved_on(req.getCfo_approved_on());
			rh.setSo_status(req.getSo_status());
			rh.setCreated_on(req.getCreated_on());
			rh.setCreated_by(req.getCreated_by());
			rh.setModified_on(req.getModified_on());
			rh.setModified_by(req.getModified_by());
			rh.setIs_deleted(req.getIs_deleted());

			return rh;

	}

	public static Timestamp getCurrentTime() {

		Date date = new Date(); // getTime() returns current time in milliseconds
		long time = date.getTime();// Passed the milliseconds to constructor of Timestamp class
		Timestamp ts = new Timestamp(time);

		return ts;

	}

	// compress the image bytes before storing it in the database

	public static byte[] compressBytes(byte[] data) {

		Deflater deflater = new Deflater();

		deflater.setInput(data);

		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		byte[] buffer = new byte[1024];

		while (!deflater.finished()) {

			int count = deflater.deflate(buffer);

			outputStream.write(buffer, 0, count);

		}

		try {

			outputStream.close();

		} catch (IOException e) {

		}

		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();

	}

	// uncompress the image bytes before returning it to the angular application

	public static byte[] decompressBytes(byte[] data) {

		Inflater inflater = new Inflater();

		inflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

		byte[] buffer = new byte[1024];

		try {

			while (!inflater.finished()) {

				int count = inflater.inflate(buffer);

				outputStream.write(buffer, 0, count);

			}

			outputStream.close();

		} catch (IOException ioe) {

		} catch (DataFormatException e) {

		}

		return outputStream.toByteArray();

	}

	public static Object getCrc(long filebyte, InputStream in) throws IOException {

		CRC32 crcMaker = new CRC32();
		byte[] buffer = new byte[(int) filebyte];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			crcMaker.update(buffer, 0, bytesRead);
		}
		long crc = crcMaker.getValue();

		return crc;
	}

	public static int getFY(String date) throws Exception {

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		int month;
		int year;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		month = cal.get(Calendar.MONTH);

		int advance = (month < 3) ? 0 : 1;
		year = cal.get(Calendar.YEAR) + advance;

		return year;
	}

	private static String getBase64Decode(String password) {

		byte[] decoded = Base64.decodeBase64(password.getBytes());

		// Print the decoded array
		System.out.println(Arrays.toString(decoded));

		// Convert the decoded byte[] back to the original string and print
		// the result.
		String decodedString = new String(decoded);
		System.out.println(password + " = " + decodedString.trim());
// 
		return decodedString;

	}

}
