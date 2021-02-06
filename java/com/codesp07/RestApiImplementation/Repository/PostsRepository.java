package com.codesp07.RestApiImplementation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codesp07.RestApiImplementation.model.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer>{
	
}
