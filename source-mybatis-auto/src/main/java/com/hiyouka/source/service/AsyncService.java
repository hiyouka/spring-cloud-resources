package com.hiyouka.source.service;

import com.hiyouka.source.mapper.ConfigDataEntryMapper;
import com.hiyouka.source.model.ConfigDataEntry;
import com.hiyouka.source.config.transaction.ConnectionHolderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Service
@Async
public class AsyncService {

    @Autowired
    private ConfigDataEntryMapper entryMapper;

    @Transactional
    @ConnectionHolderOperation(ConnectionHolderOperation.HolderAction.BIND)
    public void asyncInsert(){
        ConfigDataEntry configDataEntry = new ConfigDataEntry();
        configDataEntry.setName("async insert 777");
        configDataEntry.setNodeId("777");
        entryMapper.insert(configDataEntry);
        throw new RuntimeException("async insert throw a exception");
    }

}