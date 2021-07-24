package com.alethe.opf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Created by Kunal Kumar
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SODashBoard {
	
	
	private Integer total_so=0;
	private Integer final_so=0;
	private Integer draft_mode_so=0;
	private Integer pending_so=0;
	private Integer cfo_approved_so=0;
	
}
