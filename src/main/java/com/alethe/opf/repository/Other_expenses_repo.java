package com.alethe.opf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alethe.opf.dto.Expense_type_detail;
import com.alethe.opf.dto.Expense_type_list;
import com.alethe.opf.entity.Other_Expense_Type;

@Repository
public interface Other_expenses_repo extends JpaRepository<Other_Expense_Type, Integer> {

	@Query("SELECT new com.alethe.opf.dto.Expense_type_list(r.expense_id ,r.expense_type) FROM Other_Expense_Type  r ")
	public List<Expense_type_list> getExpenseTypeList();
	
	
	@Query("SELECT new com.alethe.opf.dto.Expense_type_detail(r.expense_id, r.expense_type, r.hsn_code, r.part_no) FROM Other_Expense_Type  r WHERE r.expense_id=:id ")
	public Expense_type_detail getExpenseDetails(@Param("id") Integer id);
	
	
//	@Query("SELECT r FROM Other_Expense_Type AS r WHERE r.expense_type=:type")
//	public Other_Expense_Type getExpenseDetails(@Param("type") String type);

}
