package com.alethe.opf.entity;

import java.io.Serializable;
import java.security.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "other_expense_type_mst")
public class Other_Expense_Type  {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer expense_id;
	private String expense_type="";
	private String hsn_code="";
	private String part_no="";
	private Timestamp created_on;
	private Integer created_by;
	private Timestamp modified_on;
	private Timestamp modified_by;
	private Timestamp is_deleted;

}
