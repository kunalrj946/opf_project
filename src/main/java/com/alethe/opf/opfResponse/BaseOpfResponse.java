package com.alethe.opf.opfResponse;

import lombok.Data;

@Data
public class BaseOpfResponse {

	private String message = "data is found !";
	private Integer errorcode =0;
	private Boolean error = false;

}
