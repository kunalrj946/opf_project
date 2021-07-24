package com.alethe.opf.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Kunal Kumar
 */
@NoArgsConstructor
@Setter
@Getter
@Data
@ToString
public class UserPair {

	private Integer user_id; // | integer | | not null | nextval('user_mst_user_id_seq'::regclass)
	private String loginid; // | character varying(200) | | not null |
	
	public UserPair(Integer user_id, String loginid) {
		super();
		this.user_id = user_id;
		this.loginid = loginid;
	}

}
