package com.alethe.opf.dto;

import java.sql.Timestamp;

import com.alethe.opf.entity.User_role_mst;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kunal Kumar
 */
@Setter
@Getter
@Data
@ToString
public class SoStatusPojo {

	private Integer so_status;
	private String am_remark="";
	private Timestamp am_approved_on;
	private String tam_remark="";
	private Timestamp tam_approved_on;
	private String zm_remark="";
	private Timestamp zm_approved_on;
	private String cfo_remark="";
	private Timestamp cfo_approved_on;

}
