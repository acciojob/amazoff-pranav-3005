package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    private HashMap<String,Order> orderHashMap=new HashMap<>();

    private HashMap<String,DeliveryPartner> deliveryPartnerHashMap=new HashMap<>();

    private HashMap<String, List<Order>> deliveryPartnerOrderMap=new HashMap<>(); //orders of a delivery man

    //<orderid,partnerid>
    private HashMap<String,String> orderDeliveryPartnerMap=new HashMap<>(); //each order's deliveryman map

    public HashMap<String, Order> getOrderHashMap() {
        return orderHashMap;
    }

    public HashMap<String, DeliveryPartner> getDeliveryPartnerHashMap() {
        return deliveryPartnerHashMap;
    }

    public HashMap<String, List<Order>> getDeliveryPartnerOrderMap() {
        return deliveryPartnerOrderMap;
    }

    public HashMap<String, String> getOrderDeliveryPartnerMap() {
        return orderDeliveryPartnerMap;
    }

    //********
    public void addOrder(String id, Order order) {
        orderHashMap.put(id,order);
        orderDeliveryPartnerMap.put(id,"none");
    }

    public void addPartner(String partnerId) {
        deliveryPartnerHashMap.put(partnerId,new DeliveryPartner(partnerId));
        deliveryPartnerOrderMap.put(partnerId,new ArrayList<>());
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Order order= orderHashMap.get(orderId);
        deliveryPartnerOrderMap.get(partnerId).add(order);
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders( deliveryPartnerHashMap.get(partnerId).getNumberOfOrders()+1 );

        orderDeliveryPartnerMap.put(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
    }

    public List<Order> getOrdersByPartnerId(String partnerId) {
        return deliveryPartnerOrderMap.get(partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time=deliveryPartnerOrderMap.get(partnerId).get( deliveryPartnerOrderMap.get(partnerId).size()-1 ).getDeliveryTime();

        return Integer.toString(time);
    }

    public void deletePartnerById(String partnerId) {
        deliveryPartnerHashMap.remove(partnerId);
        deliveryPartnerOrderMap.remove(partnerId);

        for(String i:orderDeliveryPartnerMap.keySet())
        {
            if(orderDeliveryPartnerMap.get(i).equals(partnerId))
                orderDeliveryPartnerMap.put(i,"none");
        }
    }

    public void deleteOrderById(String orderId) {

        orderHashMap.remove(orderId);

        String partner=orderDeliveryPartnerMap.get(orderId);
        orderDeliveryPartnerMap.remove(orderId);

        deliveryPartnerOrderMap.get(partner).remove(orderId);
    }
}
