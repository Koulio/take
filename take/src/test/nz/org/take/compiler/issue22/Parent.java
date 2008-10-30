package test.nz.org.take.compiler.issue22;

import java.util.*;

public class Parent {
	private String name = null;
	private List<Child> children = new ArrayList<Child>();

	public List<Child> getChildren() {
		return children;
	}

	public void setChildren(List<Child> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
