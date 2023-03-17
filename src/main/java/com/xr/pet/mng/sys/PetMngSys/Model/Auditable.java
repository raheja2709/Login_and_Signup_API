package com.xr.pet.mng.sys.PetMngSys.Model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@MappedSuperclass
@Data
public class Auditable {

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public long creationDate;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public long lastModifiedDate;

	public Auditable() {
	}

	@PrePersist
	public void onCreate() {
		this.creationDate = new Date().getTime();
		this.onUpdate();
	}

	@PreUpdate
	public void onUpdate() {
		this.lastModifiedDate = new Date().getTime();
	}
}
