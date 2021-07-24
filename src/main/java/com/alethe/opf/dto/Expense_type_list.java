package com.alethe.opf.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@Data
@ToString
public class Expense_type_list {
	
	
	 private Integer expense_id;
	 private String expense_type;
	
	 
	 public Expense_type_list(Integer expense_id, String expense_type) {
		super();
		this.expense_id = expense_id;
		this.expense_type = expense_type;
	} 
	

	 
}
