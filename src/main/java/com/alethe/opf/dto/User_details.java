package com.alethe.opf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User_details {

	private String loginid ;
	private String password;
	private String confirm_password;
	private String email;
	private Integer user_id;
	
	
}
