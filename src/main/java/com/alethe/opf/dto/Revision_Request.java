package com.alethe.opf.dto;

import java.util.List;

import com.alethe.opf.entity.Rev_history;
import com.alethe.opf.entity.Sale_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Revision_Request {

	private Rev_history sale_order;
	private List<Sale_item> sale_item;

}
