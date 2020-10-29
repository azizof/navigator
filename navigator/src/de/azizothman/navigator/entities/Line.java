package de.azizothman.navigator.entities;

public class Line {

	private String id;
	private String name;
	private Product product;
	private int platform;

	public Line() {

	}

	public Line(String id, String name, Product product, int platform) {
		super();
		this.id = id;
		this.name = name;
		this.product = product;
		this.platform = platform;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
