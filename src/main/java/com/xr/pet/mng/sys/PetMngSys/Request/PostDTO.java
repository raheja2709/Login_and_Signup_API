package com.xr.pet.mng.sys.PetMngSys.Request;

import javax.persistence.Column;

import lombok.Data;

@Data
public class PostDTO {

	@Column(name = "tag")
	private String tag;

	@Column(name = "location")
	private Boolean location;

	@Column(name = "description")
	private String description;

}
