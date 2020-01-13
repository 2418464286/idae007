package com.hp.listeners;

import com.hp.service.IndexService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @Autowired
    private IndexService indexService;
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "ly.create.index.queue", durable = "true"),
        exchange = @Exchange(
                value = "ly.item.exchange",
                ignoreDeclarationExceptions = "true",
                type = ExchangeTypes.TOPIC),
        key = {"item.insert", "item.update"}))
    public void listenCreate(Long  id){
        //更新索引
        indexService.createIndex(id);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.create.index.queue2", durable = "true"),
            exchange = @Exchange(
                    value = "ly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void listenDelete(Long  id){
        //更新索引
        indexService.deleteIndex(id);
    }
}
