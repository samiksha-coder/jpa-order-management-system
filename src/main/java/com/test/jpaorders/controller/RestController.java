
package com.test.jpaorders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.test.jpaorders.constants.OrderStatus;
import com.test.jpaorders.constants.entity.Order;
import com.test.jpaorders.exception.OrderException;
import com.test.jpaorders.service.OrderService;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	OrderService orderService;

	@GetMapping("/")
	String getIndex() {
		return "hello";
	}

	@GetMapping("/all")
	ResponseEntity<List<Order>> getAllOrders() {
		List<Order> result = orderService.getAllOrders();
		if (!CollectionUtils.isEmpty(result))
			return new ResponseEntity<>(result, HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/order/{orderID}")
	ResponseEntity<Order> getAllOrders(@PathVariable Long orderID) {
		try {
			Order order = orderService.getOrder(orderID);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (OrderException e) {
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("MESSAGE", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	ResponseEntity<Order> saveOrders(@RequestBody Order input) {
		try {
			Order order = orderService.createOrder(input);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (OrderException | RuntimeException e) {
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("MESSAGE", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@PutMapping("/updateOrder")
	ResponseEntity<Order> updateOrders(@RequestBody Order input) {
		try {
			Order order = orderService.updateOrder(input);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (OrderException | RuntimeException e) {
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("MESSAGE", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping("/updateOrderStatus")
	ResponseEntity<Order> updateOrders(Long ID, Integer intstatus) {
		try {
			if (ObjectUtils.isEmpty(ID) || ObjectUtils.isEmpty(intstatus) || OrderStatus.values().length <= intstatus)
				new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			OrderStatus status = OrderStatus.values()[intstatus];
			return new ResponseEntity<Order>(orderService.updateOrder(ID, status), HttpStatus.OK);
		} catch (OrderException e) {
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("MESSAGE", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/delete")
	ResponseEntity<Order> updateOrders(Long ID) {
		try {
			if (ObjectUtils.isEmpty(ID))
				new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return new ResponseEntity<Order>(orderService.deleteOrder(ID), HttpStatus.OK);
		} catch (OrderException e) {
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("MESSAGE", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
