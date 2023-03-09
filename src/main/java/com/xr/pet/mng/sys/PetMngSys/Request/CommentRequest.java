package com.xr.pet.mng.sys.PetMngSys.Request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class CommentRequest {

	@NotBlank
	@Size(max = 1000)
	private String text;

}
