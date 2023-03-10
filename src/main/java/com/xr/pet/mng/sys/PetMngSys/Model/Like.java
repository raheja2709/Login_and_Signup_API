package com.xr.pet.mng.sys.PetMngSys.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long postId;

	private int userId;

	private Long likes;

}
