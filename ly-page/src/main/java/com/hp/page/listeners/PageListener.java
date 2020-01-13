package com.hp.page.listeners;

import com.hp.page.service.FileService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PageListener {

    @Autowired
     private FileService fileService;

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "ly.create.page.queue", durable = "true"),
        exchange = @Exchange(
                value = "ly.item.exchange",
                ignoreDeclarationExceptions = "true",
                type = ExchangeTypes.TOPIC),
        key = {"item.insert", "item.update"}))
    public void listenCreatePage(Long  id){
        //创建静态页面
        fileService.syncCreateHtml(id);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ly.create.page2.queue", durable = "true"),
            exchange = @Exchange(
                    value = "ly.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void listenDeletePage(Long  id){
        fileService.deleteHtml(id);//112
    }
}
