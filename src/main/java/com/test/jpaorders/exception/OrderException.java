package com.test.jpaorders.exception;

import com.test.jpaorders.constants.OrderErrorCode;

public class OrderException extends Exception{
	private OrderErrorCode code;

	public OrderException(OrderErrorCode code, String message) {
		super(message);
		this.code = code;
	}
	
	public OrderErrorCode getCode() {
		return this.code;
	}
}
