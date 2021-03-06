package com.codesp07.RestApiImplementation.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codesp07.RestApiImplementation.model.Posts;
import com.codesp07.RestApiImplementation.model.User;

import io.swagger.annotations.ApiOperation;

import com.codesp07.RestApiImplementation.ProfileHelper.ProfileUploadHelper;
import com.codesp07.RestApiImplementation.Service.UserService;
import com.codesp07.RestApiImplementation.Service.userPosts.UserPostService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userservice;
	
	@Autowired
	private ProfileUploadHelper fileUploadHelper;

	@Autowired
    UserPostService userpostservice;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return this.userservice.getUsers();
	}
	 @ApiOperation(value = "Register User ", tags = "Login/Register")
	@PostMapping("/register")
	public String addUser(@RequestParam String email,@RequestParam String pass,@RequestParam String username,@RequestParam int rollno)
{

		if(email!=null && !"".equals(email))
		{
			User userObj=userservice.fetchByUserEmailId(email);
			
			if(userObj!=null) {
		    return "User with "+email+" is already exist";
		     }
			 
			
		}
		     User user =new User(rollno,username,email,pass);
				userservice.addUser(user);
		
		return "User registered Successfully!";
}
	
	 @ApiOperation(value = "Login User ", tags = "Login/Register")
	@GetMapping("/login")
	public ResponseEntity<String> getVaildUser(@RequestParam  String email,@RequestParam  String pass) 
    {     
    	 User userObj=userservice.getValidUser(email,pass);
    	 if(userObj==null) {
    		 return new ResponseEntity<>("Invalid password or username OR User is not Registered!",HttpStatus.BAD_REQUEST);
    }
         if(userObj.getEmail().equals(email)) {
    	 if(userObj.getPass().equals(pass))
         return new ResponseEntity<>("Successfully Logged in. Hello "+userObj.getUsername(),HttpStatus.OK);
         }
	 	return new ResponseEntity<>("Incorrect Password or Username",HttpStatus.BAD_REQUEST);
     
    }
	
	
	 @ApiOperation(value = "Admin - Password for particular email  ", tags = "Admin-Login-Password")
	@GetMapping("/Admin-forgotPassword")
	public String forgotPassword(@RequestParam String email) {
		String checkUserExist = this.userservice.getValidatedUserEmail(email);
		return checkUserExist;
	}
	
	
	 @ApiOperation(value = "Reset Password - Generate OTP ", tags = "Reset Password")
	   @PostMapping("/resetPasswrod")
	    public ResponseEntity<String> resetPasswd(@RequestParam String email)
	    { 
		  
		    User userObj=userservice.fetchByUserEmailId(email);
	        
		   if(userObj!=null)
		   {
				int myotp=(int) (Math.random()*9000)+1000;
				userObj.setOtp(myotp);
				  LocalTime time=LocalTime.now(); 
		          LocalDate date=LocalDate.now();
		   
	 	          userObj.setLocalDate(date);
	 	          userObj.setLocalTime(time);
		      
				userservice.addUser(userObj); 
			    boolean status = userservice.sendEmail(myotp,email);
			    if(!status) 
				{
			    return new ResponseEntity<>("Otp is not sending",HttpStatus.BAD_REQUEST);
				}
				
		          
				return new ResponseEntity<>("Otp sent on " + userObj.getEmail(),HttpStatus.OK);				
		   }

				return new ResponseEntity<>("Email is invaild",HttpStatus.BAD_REQUEST);
			  
	    }
	
	  @ApiOperation(value = "Confirm OTP ", tags = "Reset Password")
		   
	   @PostMapping("/resetPassword/verify") 
	    public ResponseEntity<String> resetPasswdWithVerify(@RequestParam String email,@RequestParam int otp, @RequestParam String pass)
	    {
		  
		   User userObj=userservice.fetchByUserEmailId(email);
		   if (userObj==null)
			   return new ResponseEntity<>("Incorrect User",HttpStatus.BAD_REQUEST); 
		       int myotp=userObj.getOtp();
			  int t=LocalTime.now().minusMinutes(2).compareTo(userObj.getLocalTime());
		      int d=userObj.getLocalDate().compareTo(LocalDate.now());
		      
		      if(d!=0 || t>=0)
			  return new ResponseEntity<>("Otp expired! Please otp generate again.",HttpStatus.BAD_REQUEST);
            		   
		     if(myotp!=otp)
		    return new ResponseEntity<>("Incorrect otp",HttpStatus.BAD_REQUEST);
		   
		   // reset pass
		    userservice.updatePass(userObj,pass);
          
		   return new ResponseEntity<>("Password Changed Successfully ",HttpStatus.OK);
	   }  
	  
	

	
	

	
	  
	  
	 @ApiOperation(value = "Create New Post ", tags = "Post")
     @PostMapping("/create_post")
     public ResponseEntity<String> createUserPost(@RequestParam int Rollno,@RequestParam String title,@RequestParam String content)
     {
             
    	 User userObj=userservice.findByrollno(Rollno);
    	 Posts userpost=new Posts(title,content);
    	 userObj.getPosts().add(userpost);
    	 userservice.addUser(userObj);
	         return new ResponseEntity<>("Post created successfully! ",HttpStatus.OK);
     }
 
     
 @ApiOperation(value = "Retrive all post ", tags = "Post")
 @GetMapping("/all_post")
 public List<Posts> getAllPost(@RequestParam int Rollno) throws Exception
 {
   	
	User userObj=userservice.findByrollno(Rollno);
	 
	 
   List<Posts> userpost=userObj.getPosts();
   return userpost;  
 }

 
 
 @ApiOperation(value = "Update profile picture Of Existing User", tags = "Profile-Photo")
	@PutMapping("/update-profile/{rollno}")
	public ResponseEntity<Object> updateProfile(@RequestBody String encodedString,@PathVariable("rollno") int rollno){
		
		User userFetch = null;
		userFetch = userservice.findByrollno(rollno);
		if(userFetch == null) {
			throw new NoSuchElementException("null");
		}
		
		try {
			String profile = fileUploadHelper.uploadEncodedImage(encodedString, userFetch.getUsername()+"_"+"profile");
			if(profile == null) { 
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!!!!!!!!");
			}
			userFetch.setProfile(profile);
			System.out.println(profile);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please select valid path of profile");
		}
		
		
		try {
			userFetch=userservice.registerUser(userFetch);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("Profile Updated !!");
	}

	@ApiOperation(value = "Get profile picture of existing user", tags = "Profile-Photo")
	@GetMapping("/profile/{rollno}")
	public ResponseEntity<Object> getProfile(@PathVariable("rollno") int rollno) {
		User userFetch = null;
		
		try{
			userFetch = userservice.findByrollno(rollno); 
		} catch (Exception e) {
			throw new NoSuchElementException("User Not registered");
		}
		
		
		
		return ResponseEntity.ok(userFetch.getProfile());
	}
 
 
 
	
}
