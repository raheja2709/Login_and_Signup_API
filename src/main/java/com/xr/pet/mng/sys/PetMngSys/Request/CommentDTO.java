package com.xr.pet.mng.sys.PetMngSys.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

	private String firstName;
	private String lastName;
	private String text;
}
