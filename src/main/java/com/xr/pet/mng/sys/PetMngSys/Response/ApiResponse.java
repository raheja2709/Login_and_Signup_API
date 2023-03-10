package com.xr.pet.mng.sys.PetMngSys.Response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.xr.pet.mng.sys.PetMngSys.Exception.LowerCaseClassNameResolver;

import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "response", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiResponse {

	private List<Object> dataArray;

	private Object data;

	private long timestamp;

	private ApiResponse() {
		timestamp = new Date().getTime();
	}

	public ApiResponse(Object object) {
		this();
		this.data = object;
	}

	public ApiResponse(List<Object> objects) {
		this();
		this.dataArray = objects;
	}

	public ApiResponse(Object object, List<Object> objects) {
		this.data = object;
		this.dataArray = objects;
	}

}

