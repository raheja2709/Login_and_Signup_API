package com.xr.pet.mng.sys.PetMngSys.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "filepath")	
	private String filePath;

	@Column(name = "tag")
	private String tag;

	@Column(name = "location")
	private Boolean location;

	@Column(name = "description")
	private String description;

	private Long likes = 0L;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "post_id")
//	private List<Comment> comments = new ArrayList<>();

}
