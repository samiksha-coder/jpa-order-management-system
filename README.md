# jpa-order-management-system

Order Management System that uses Springboot JPA with embedded H2 to manage orders in 3 status -- ORDERED, OUT_FOR_DELIVERY, DELIVERED. Users can create orders with different items.

Fed with dummy data to facilitate better understanding of the code.

Exposed APIs -- 
1. GET: /all
2. GET: /order/{orderID}
3. POST: /create
4. PUT: /updateOrder
5. PUT: /updateOrderStatus
6. DELETE: /delete

Hosted on http://localhost:8080/ordersApp

Sample get data

{
    "completed": null,
    "created": "2022-03-31T18:30:00.000+00:00",
    "id": 4,
    "items": [
        {
            "id": 8,
            "name": "Spoons",
            "cost": 11,
            "quantity": 4,
            "orderId": 4
        }
    ],
    "name": "Eric",
    "status": "ORDERED",
    "orderId": 4
}

Sample create data

{
    "items": [
        {
            "name": "Shampoo",
            "cost": 7,
            "quantity": 1
        },
        {
            "name": "Candy",
            "cost": 2,
            "quantity": 2
        }
    ],
    "name": "Daniel"
}
