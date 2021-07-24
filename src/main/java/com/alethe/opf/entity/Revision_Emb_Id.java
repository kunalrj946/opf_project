package com.alethe.opf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class Revision_Emb_Id  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "so_no")
	private String so_no; 
	
	@Column(name = "so_rev")
	private Integer so_rev;
	
	public String getSo_no() {
		return so_no;
	}
	public void setSo_no(String so_no) {
		this.so_no = so_no;
	}
	public Integer getSo_rev() {
		return so_rev;
	}
	public void setSo_rev(Integer so_rev) {
		this.so_rev = so_rev;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((so_no == null) ? 0 : so_no.hashCode());
		result = prime * result + ((so_rev == null) ? 0 : so_rev.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revision_Emb_Id other = (Revision_Emb_Id) obj;
		if (so_no == null) {
			if (other.so_no != null)
				return false;
		} else if (!so_no.equals(other.so_no))
			return false;
		if (so_rev == null) {
			if (other.so_rev != null)
				return false;
		} else if (!so_rev.equals(other.so_rev))
			return false;
		return true;
	}
	public Revision_Emb_Id(String so_no, Integer so_rev) {
		super();
		this.so_no = so_no;
		this.so_rev = so_rev;
	}
	public Revision_Emb_Id() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
