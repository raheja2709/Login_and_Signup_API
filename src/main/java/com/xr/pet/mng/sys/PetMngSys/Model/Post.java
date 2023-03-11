package com.xr.pet.mng.sys.PetMngSys.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Auditable {

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

    @JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Like> like = new HashSet<>();

	@JsonIgnore
	private Integer postCreatorsId;
}
