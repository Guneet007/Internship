package com.example.API.Controller;

import com.example.API.Entities.Orders;
import com.example.API.Entities.User;
import com.example.API.Services.OrdersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;


@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @ApiOperation(value="Show all orders")
    @RequestMapping("/orders")
    public Iterable<Orders> getAllOrders()
    {
        return ordersService.getAllOrders();
    }

    @ApiOperation(value="Show a particular Order")
    @RequestMapping("/orders/{orderId}")
    public Orders getOrderById(@PathVariable BigInteger orderId)
    {
        return ordersService.getOrderById(orderId);
    }

    @ApiOperation(value="Show all orders of a particular User")
    @RequestMapping("/users/{id}/orders")
    public List<Orders> getOrders(@PathVariable BigInteger id) {
        return ordersService.getOrders(id);
    }

    @ApiOperation(value="Show an order for a User")
    @RequestMapping("/users/{userId}/orders/{id}")
    public Orders getOrder(@PathVariable BigInteger id){
        return ordersService.getOrder(id);
    }

    @ApiOperation(value="Adds a new Order")
    @RequestMapping(method = RequestMethod.POST,value = "/users/{userId}/orders")
    public Orders addOrder(@PathVariable BigInteger userId) {
        Orders orders=new Orders();
        orders.setUser(new User(userId," ",0000000000L," "));
        return ordersService.addOrder(orders);
    }

    @ApiOperation(value="Alter an Order")
    @RequestMapping(method = RequestMethod.PUT, value="/users/{userId}/orders/{id}")
    public Orders updateOrder(@RequestBody Orders order,@PathVariable BigInteger id,@PathVariable BigInteger userId)throws Exception {
        order.setUser(new User(userId," ",0000000000L," "));
        return ordersService.updateOrder(order, id);
    }

    @ApiOperation(value="Delete an Order")
    @RequestMapping(method = RequestMethod.DELETE, value="/orders/{id}")
    public void deleteOrder(@PathVariable BigInteger id){
        ordersService.deleteOrder(id);
    }

}
