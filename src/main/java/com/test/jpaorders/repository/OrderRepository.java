package com.test.jpaorders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpaorders.constants.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	public Order getOrderById(Long ID);
}
