package com.xr.pet.mng.sys.PetMngSys.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String error="UserDetails Already Exist";
	
}
