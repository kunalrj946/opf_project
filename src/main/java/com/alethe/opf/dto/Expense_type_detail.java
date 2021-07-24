package com.alethe.opf.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class Expense_type_detail {

	private Integer expense_id;
	private String 	expense_type;
	private String 	hsn_code;
	private String 	part_no;
	

	
	public Expense_type_detail(Integer expense_id, String expense_type, String hsn_code, String part_no) {
		super();
		this.expense_id = expense_id;
		this.expense_type = expense_type;
		this.hsn_code = hsn_code;
		this.part_no = part_no;
	}

	
	public Expense_type_detail() {
		super();
	}
	
	


}
