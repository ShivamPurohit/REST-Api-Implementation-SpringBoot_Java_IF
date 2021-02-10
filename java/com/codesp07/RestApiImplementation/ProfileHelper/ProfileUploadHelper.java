package com.codesp07.RestApiImplementation.ProfileHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Component
public class ProfileUploadHelper {
	private final String UPLOAD_DIR = "C:\\Users\\Admin\\Desktop\\shivamUpesDocs\\internityFoundation\\JAVAEE_SPRING\\RestApiImplementation\\src\\main\\resources\\static\\image";
//	private final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();
	public ProfileUploadHelper() throws IOException {}
	
	
	public String uploadEncodedImage(String encodedImage,String filename) {	
		String path = null;
		try {
			System.out.println("reading");
			//System.out.println(encodedImage);
			byte contains[] = Base64.getDecoder().decode(encodedImage);
			String directory = UPLOAD_DIR+File.separator+filename+".jpeg";
			System.out.println("Start writing >>>>>>>>");
			
			new FileOutputStream(directory).write(contains);
			
			System.out.println("End Writing>>>>>>>>>>");
			path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(filename+".jpeg").toUriString();
		} catch (Exception e) {
			return path;
		}
		return path;
	}
}
