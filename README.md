# spring-microservices

## Prerequisites
Run the sql files in your mysql to crete the database and insert initial values
change the username and password of mysql in the application.properties in product-services, 
inventory-services, and order-services

### Product Services
1. http://localhost:8080/api/product use HTTP Get method to view all product. <br />
2. http://localhost:8080/api/product use HTTP Post method to add product. <br />
Sample of the JSON input for adding new product as follow: <br />
   {
   "productCode":"IPHONE-17",
   "name":"Iphone 17",
   "description":"Latest Iphone 17",
   "price": 1700,
   "quantity": 170
   }
3. http://localhost:8080/api/product?productCode=IPHONE-17 use HTTP Delete method to delete product. <br />
4. http://localhost:8080/api/product/modifyprice?productCode=IPHONE-13&price=1330 use HTTP Put method to update the product price by product code <br />

### Inventory Services
1. http://localhost:8080/api/inventory/modifyquantity?productCode=IPHONE-14&quantity=144 use HTTP Put method to update the inventory quantity by product code <br />

### Order Services
1. http://localhost:8080/api/order/createorder use HTTP Post method to add product. <br />
Sample of the JSON input for create new order as follow: <br />
{
   "orderItemDtoList": [
   {
   "productCode":"SAMSUNG-S1",
   "quantity":17,
   "unitPrice":1000.00
   },
   {
   "productCode":"SAMSUNG-S2",
   "quantity":12,
   "unitPrice":1050.00
   },
   {
   "productCode":"IPHONE-13",
   "quantity":5,
   "unitPrice":1310.00
   },
   {
   "productCode":"IPHONE-14",
   "quantity":12,
   "unitPrice":1400.00
   }
   ]
}
2. http://localhost:8080/api/order use HTTP Get method to view all product. <br />

### RabbitMQ for cloud bus configuration
First make sure you downloaded docker and run your docker desktop then run the following commands<br />
docker pull rabbitmq:3.11.0 <br />
run docker run --rm -it -p 5672:5672 rabbitmq:3.11.0

### Zipkin
docker run --rm -it --name zipkin -p 9411:9411 openzipkin/zipkin <br/>