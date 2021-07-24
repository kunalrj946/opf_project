package com.alethe.opf.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Created by Kunal Kumar
 */
@Getter
@AllArgsConstructor
@Data
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private boolean error;
	private String details;
	private Integer errorCode;

}
