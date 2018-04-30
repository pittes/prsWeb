package com.prs.business.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prs.business.vendor.Vendor;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int vendorID;
	@ManyToOne
	@JoinColumn(name="VendorID")
	private Vendor vendor;
	@Column(name="PartNumber")
	private String vendorPartNumber;
	private String name;
	private double price;
	private String unit;
	private String photoPath;
	
	public Product() {
		super();
		vendor = null;
		vendorPartNumber = "";
		name = "";
		price = 0.0;
		unit = "";
		photoPath = "";
	}

	public Product(int id, Vendor vendor, String vendorPartNumber, String name, double price, String unit,
			String photoPath) {
		super();
		this.id = id;
		this.vendor = vendor;
		this.vendorPartNumber = vendorPartNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public int getVendorID() {
//		return vendorID;
//	}
//
//	public void setVendorID(int vendorID) {
//		this.vendorID = vendorID;
//	}

	public String getVendorPartNumber() {
		return vendorPartNumber;
	}

	public void setVendorPartNumber(String vendorPartNumber) {
		this.vendorPartNumber = vendorPartNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", " + vendor.getSummary() + ", vendorPartNumber=" + vendorPartNumber + ", name="
				+ name + ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath + "]";
	}
	
	

}
