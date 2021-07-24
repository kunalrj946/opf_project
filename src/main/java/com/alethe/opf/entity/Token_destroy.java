package com.alethe.opf.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
@Table(name = "token_destroy")
public class Token_destroy {
	
	@Id
	private String username;
	private String token;
	private Integer id;

}
