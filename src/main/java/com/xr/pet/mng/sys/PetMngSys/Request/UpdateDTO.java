package com.xr.pet.mng.sys.PetMngSys.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {

	private String firstName;
	private String lastName;
	private String email;
}
