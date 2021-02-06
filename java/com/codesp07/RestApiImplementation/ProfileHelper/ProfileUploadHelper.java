package com.codesp07.RestApiImplementation.ProfileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProfileUploadHelper {
	public final String UPLOAD_DIR = "C:\\Users\\Admin\\Desktop\\shivamUpesDocs\\internityFoundation\\JAVAEE_SPRING\\RestApiImplementation\\src\\main\\resources\\static\\image";
//	public final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();

	public ProfileUploadHelper() throws IOException
	{
		
	}
	
	public boolean uploadFile(MultipartFile multipartFile) {
	
		boolean f = false;
		
		try {
//			InputStream is = multipartFile.getInputStream();
//			byte data[] = new byte[is.available()];
//			is.read(data);
//			
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
//		fos.write(data);
//			
//		fos.flush();
//		fos.close();
			
		Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		return f = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return f;
		
	}
}
