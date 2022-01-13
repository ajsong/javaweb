package com.laokema.javaweb.model.index;

import com.laokema.javaweb.model.BaseModel;

public class Category extends BaseModel {

	private Integer id;
	private String name;
	private String pic;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}
