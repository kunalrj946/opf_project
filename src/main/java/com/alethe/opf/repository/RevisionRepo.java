package com.alethe.opf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.alethe.opf.entity.Revision_history;

@Repository
public interface RevisionRepo extends JpaRepository<Revision_history, Integer> {

	@Query("SELECT h FROM Revision_history  AS h  WHERE h.rev_id.so_no = :so_no")
	List<Revision_history> findAllRevisionBySoId(@Param("so_no") String so_no);

	
	@Query("SELECT h  FROM Revision_history AS h  WHERE (h.rev_id.so_rev = :so_rev  AND h.rev_id.so_no =:so_no)")
	Revision_history findAllRevisionDetails(@Param("so_rev") Integer so_rev, @Param("so_no") String so_no);

}
