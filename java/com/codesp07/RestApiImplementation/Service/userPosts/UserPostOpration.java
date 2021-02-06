package com.codesp07.RestApiImplementation.Service.userPosts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesp07.RestApiImplementation.Repository.PostsRepository;


@Service
public class UserPostOpration implements UserPostService{
	@Autowired
	private PostsRepository userPosts;
}
