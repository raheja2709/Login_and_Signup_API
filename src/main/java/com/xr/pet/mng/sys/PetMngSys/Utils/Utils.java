package com.xr.pet.mng.sys.PetMngSys.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.xr.pet.mng.sys.PetMngSys.Security.UserPrincipal;

public class Utils {

	public static String saveFile(MultipartFile file, String title) throws IOException {

		System.out.println("File = " + file);
		System.out.println("title = " + title);
		String path = Constants.UPLOAD_PATH + title + new Date().getTime() + "."
				+ FilenameUtils.getExtension(file.getOriginalFilename());

		System.out.println("path = " + path);
		String storedFilePath = Constants.BASE_URL;

		File convFile = new File(path);

		convFile.createNewFile();

		FileOutputStream fos = new FileOutputStream(convFile);

		fos.write(file.getBytes());

		fos.close();

		storedFilePath = storedFilePath + convFile.getName();

		return storedFilePath;
	}

	public static long getJwtUserId() {

		UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getId();

	}
}
