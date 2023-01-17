package com.test.jpaorders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpaorders.constants.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	public Item getItemById(Long ID);
	
	public List<Item> getItemsByOrderId(Long ID);
}
