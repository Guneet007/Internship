package com.example.API.Services;

import com.example.API.Entities.Orders;
import com.example.API.Entities.Product;
import com.example.API.Exceptions.OrderException;
import com.example.API.Repository.OrdersRepository;
import javassist.NotFoundException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class OrdersService {
    @Autowired
    private OrdersRepository orderRepository;

    public Iterable<Orders> getAllOrders(){
        return orderRepository.findAll();

    }

    public Orders getOrderById(BigInteger id) {
       Optional<Orders> order= orderRepository.findById(id);
       if(!order.isPresent())
           throw new OrderException("Order with id="+id+" not found");
       return order.get();
    }

    public List<Orders> getOrders(BigInteger id){
        List<Orders> orders=new ArrayList<>();
        orderRepository.findByUserId(id)
                .forEach(orders::add);
        if(orders.isEmpty())
            throw new OrderException("No order found for user with id="+id);
        return orders;
    }

    public  List<Orders> getOrdersByDate(Date date1, Date date2)
    {
        return orderRepository.findAllByDateBetween(date1,date2);
    }

    public Orders addOrder(Orders order) {
        return orderRepository.save(order);
    }

    public Orders getOrder(BigInteger id){
        Optional<Orders> order= orderRepository.findById(id);
        if(!order.isPresent())
            throw new OrderException("No Order for with id="+id);
        return order.get();
    }

    public Orders updateOrder(Orders order, BigInteger id) throws Exception {
        Orders ordersFromDB = getOrder(id);
        if(order !=null) {
            BeanUtils.copyProperties(order,ordersFromDB);
            ordersFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return orderRepository.save(ordersFromDB);
    }

    public void deleteOrder(BigInteger id) {

        orderRepository.deleteById(id);
    }
}
