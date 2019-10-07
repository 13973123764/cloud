package com.cloud.search.mq;

import com.cloud.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zf
 * @date 2019-10-04-17:29
 */
@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue", durable = "true"),
            exchange = @Exchange(value = "cloud.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listen(Long spuId){
        if(spuId == null){
            return;
        }
        searchService.createOrUpdateIndex(spuId);
    }

}
