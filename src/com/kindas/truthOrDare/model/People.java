package com.kindas.truthOrDare.model;

import java.util.ArrayList;
import java.util.List;

public class People {

	private String name;
	private List<People> peoples;

	public People(String name) {
		this.name = name;
		this.peoples = new ArrayList<People>();
	}

	public People() {
		this.peoples = new ArrayList<People>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<People> getPeoples() {
		return peoples;
	}

	public void add(String name) {
		this.peoples.add(new People(name));
	}

	public People get(int index) {
		return this.peoples.get(index);
	}
}
