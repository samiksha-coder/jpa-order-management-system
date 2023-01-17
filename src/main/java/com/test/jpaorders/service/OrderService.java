package com.test.jpaorders.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.test.jpaorders.constants.OrderErrorCode;
import com.test.jpaorders.constants.OrderStatus;
import com.test.jpaorders.constants.entity.Item;
import com.test.jpaorders.constants.entity.Order;
import com.test.jpaorders.exception.OrderException;
import com.test.jpaorders.repository.ItemRepository;
import com.test.jpaorders.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ItemRepository itemRepository;

	public Order createOrder(Order order) throws OrderException {
		validateOrder(order);
		calculatePrice(order);
		Order result = orderRepository.save(order);

		result.setItems(order.getItems().stream().map((item) -> {
			item.setOrderId(result.getOrderId());
			return itemRepository.save(item);
		}).collect(Collectors.toList()));

		return result;
	}

	public Order updateOrder(Long ID, OrderStatus status) throws OrderException {
		Order order = getOrder(ID);
		calculatePrice(order);
		if (order.getStatus() == OrderStatus.DELIVERED)
			throw new OrderException(OrderErrorCode.UPDATE_COMPLETED_ORDER,
					String.format("Cannot update already delivered order: %s", ID));

		order.setStatus(status);
		if (status == OrderStatus.DELIVERED)
			order.setCompleted(new Date());

		return orderRepository.save(order);
	}
	
	public Order updateOrder(Order order) throws OrderException {
		validateOrder(order);
		calculatePrice(order);
		return createOrder(order);
	}

	public Order deleteOrder(Long ID) throws OrderException {
		Order order = getOrder(ID);
		orderRepository.delete(order);
		itemRepository.deleteAllInBatch(order.getItems());
		return order;
	}

	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		for (Order order : orders)
			order.setItems(itemRepository.getItemsByOrderId(order.getOrderId()));

		return orders;
	}

	public Order getOrder(Long ID) throws OrderException {
		Optional<Order> result = orderRepository.findById(ID);
		if (result.isEmpty())
			throw new OrderException(OrderErrorCode.ORDER_NOT_FOUND, String.format("Cannot find order ID %s", ID));
		Order order = result.get();
		order.setItems(itemRepository.getItemsByOrderId(ID));
		return order;
	}

	public void calculatePrice(Order order) {
		Long price = 0L;
		for (Item item : order.getItems())
			price += item.getCost() * item.getQuantity();

		order.setPrice(price);
	}

	public void validateOrder(Order order) throws OrderException {
		if (CollectionUtils.isEmpty(order.getItems()))
			throw new OrderException(OrderErrorCode.EMPTY_ORDER, "Cannot create an empty order");
		for (Item item : order.getItems()) {
			if (item.getQuantity() <= 0)
				throw new OrderException(OrderErrorCode.BAD_QUANTITY,
						String.format("Quantity for %s is %s", item.getName(), item.getQuantity()));
		}
	}
}
