package com.driver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository=new OrderRepository();
    public void addOrder(Order order) {
        orderRepository.addOrder(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<Order> getAllOrders() {
        HashMap<String,Order> orderMap=orderRepository.getOrderHashMap();

        List<Order> orders=new ArrayList<>();
        for(String i:orderMap.keySet())
        {
            orders.add(orderMap.get(i));
        }

        return orders;

    }

    public Integer getCountOfUnassignedOrders() {
        HashMap<String , String> orderPartnerMap=orderRepository.getOrderDeliveryPartnerMap();

        int count=0;
        for(String i: orderPartnerMap.keySet())
        {
            if(orderPartnerMap.get(i).equals("none"))
                count++;
        }

        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        HashMap<String,List<Order>>partnerOrderMap=orderRepository.getDeliveryPartnerOrderMap();

        int count=0;
        int time1= Integer.parseInt(time);
        for(Order order: partnerOrderMap.get(partnerId))
        {
            if(order.getDeliveryTime()>time1)
                count++;
        }

        return count;

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
