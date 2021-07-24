package com.alethe.opf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alethe.opf.entity.Token_destroy;

@Repository
public interface Token_destory_repo  extends JpaRepository<Token_destroy, String>{
	

	@Modifying
	@Query(value = "DELETE FROM token_destroy WHERE token = :token AND username =:username" , nativeQuery = true)
	void deleteToken(@Param("token") String token ,@Param("username") String username);
	
	
	@Query(value = "SELECT token from token_destroy WHERE token =:token" , nativeQuery = true)
	String getToken(@Param("token") String token);
	
}
