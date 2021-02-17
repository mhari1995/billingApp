package com.nitya.billingapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="expense")
public class Expense {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="unit")
	private int unit;
	
	@Column(name="product")
	private String product;
	
	@Column(name="cost")
	private String cost;
	
	@Column(name="Total")
	private String total;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", unit=" + unit + ", product=" + product + ", cost=" + cost + "]";
	}

	public Expense( int unit, String product, String cost, String total) {
		super();
		
		this.unit = unit;
		this.product = product;
		this.cost = cost;
		this.total = total;
	}
	
	
}
