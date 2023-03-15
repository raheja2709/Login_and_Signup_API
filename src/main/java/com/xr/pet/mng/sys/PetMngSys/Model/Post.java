package com.xr.pet.mng.sys.PetMngSys.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

	@JsonIgnore
	@OneToMany(targetEntity = Like.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "postId", referencedColumnName = "id")
	private List<Like> postLikes = new ArrayList<Like>();

	@JsonIgnore
	@OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "postId", referencedColumnName = "id")
	private List<Comment> postComments;

	private Integer postCreatorsId;
}
