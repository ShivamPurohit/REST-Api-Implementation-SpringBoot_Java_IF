package com.codesp07.RestApiImplementation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codesp07.RestApiImplementation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

		public User findByEmail(String emailId);
		
		User findByRollno(int rollNo);
}
