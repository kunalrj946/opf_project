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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RevisionRequest {

	private String so_no;
	private Integer so_rev ;
	private Integer item_id;

}
