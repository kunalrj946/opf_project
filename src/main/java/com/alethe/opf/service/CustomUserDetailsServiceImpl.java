package com.alethe.opf.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alethe.opf.entity.Users;
import com.alethe.opf.repository.UserRepository;

@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
		Optional<Users> user = userRepo.findByLoginid(loginid);

		user.orElseThrow(() -> new UsernameNotFoundException("user does not exist " + loginid));
		return user.map(UserCustomImpl::new).get();

	}

}
