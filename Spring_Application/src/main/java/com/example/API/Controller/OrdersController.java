package com.example.API.Controller;

import com.example.API.Entities.Orders;
import com.example.API.Entities.Product;
import com.example.API.Entities.User;
import com.example.API.Services.OrdersService;
import com.example.API.Services.ProductService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/rest")
@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ProductService productService;
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

    @ApiOperation(value="Search an order")
    @RequestMapping(method = RequestMethod.POST,value=("/order")
    )    public List<Orders> getOrderByDate(@RequestBody List<String> Date)throws  Exception{

        JSONObject jo = new JSONObject(Date); //
        JSONArray ja = jo.getJSONArray("date"); // get the JSONArray
        List<String> dates = new ArrayList<>();

        for(int i=0;i<ja.length();i++){
            dates.add(ja.getString(i)); // iterate the JSONArray and extract the keys
        }

        Date Date1=new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(0));
        Date Date2=new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(1));
        return ordersService.getOrdersByDate(Date1,Date2);
    }

    @ApiOperation(value="Adds a new Order")
    @RequestMapping(method = RequestMethod.POST,value = "/users/{userId}/orders")
    public @ResponseBody Orders addOrder(@PathVariable BigInteger userId,@RequestBody List<Product> products) {
        Orders orders=new Orders();
        orders.setUser(new User(userId," ",0000000000L," "));
        ordersService.addOrder(orders);
        BigInteger oId=orders.getId();
        for(int i=0;i<products.size();i++)
            products.get(i).setOrders(new Orders(oId,new Date(),userId));
        productService.addProduct(products);
        return orders;
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
