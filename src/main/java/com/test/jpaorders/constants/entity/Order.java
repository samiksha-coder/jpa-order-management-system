package com.test.jpaorders.constants.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.test.jpaorders.constants.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Column(name = "completed")
	private Date completed;

	@Column(name = "created")
	@CreationTimestamp
	private Date created;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "orderId")
	List<Item> items;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Long price;

	@Column(name = "status")
	private OrderStatus status;

	public Order() {
		this("", OrderStatus.ORDERED);
	}

	public Order(String name, OrderStatus status) {
		super();
		this.name = name;
		this.status = status;
		this.created = new Date();
	}

	public Date getCompleted() {
		return completed;
	}

	public Date getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public List<Item> getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	public Long getOrderId() {
		return id;
	}

	public Long getPrice() {
		return this.price;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
