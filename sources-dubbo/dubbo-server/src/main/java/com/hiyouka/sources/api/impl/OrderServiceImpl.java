package com.hiyouka.sources.api.impl;

import com.hiyouka.sources.server.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 01/05/2018 14:01
 * @since JDK 1.8
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource(name = "DBOrderService")
    private com.hiyouka.sources.service.OrderService orderService ;

    @Override
    public int createWrongOrder(int sid) throws Exception {
        return orderService.createWrongOrder(sid);
    }

    @Override
    public int createOptimisticOrder(int sid) throws Exception {
        return orderService.createOptimisticOrder(sid);
    }

    @Override
    public int createOptimisticOrderUseRedis(int sid) throws Exception {
        return orderService.createOptimisticOrderUseRedis(sid);
    }

    @Override
    public void createOptimisticOrderUseRedisAndKafka(int sid) throws Exception {
        orderService.createOptimisticOrderUseRedisAndKafka(sid);
    }
}
