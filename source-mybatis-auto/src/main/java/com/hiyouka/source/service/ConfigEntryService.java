package com.hiyouka.source.service;

import com.hiyouka.source.mapper.ConfigDataEntryMapper;
import com.hiyouka.source.model.ConfigDataEntry;
import com.hiyouka.source.properties.WeChat;
import com.hiyouka.source.config.transaction.ConnectionHolderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Service
@Transactional
public class ConfigEntryService {

    @Autowired
    private WeChat weChat;

    @Autowired
    private ConfigDataEntryMapper entryMapper;

    @Autowired
    private AsyncService asyncService;

    @ConnectionHolderOperation
    public void insertAsync(){
        ConfigDataEntry configDataEntry = new ConfigDataEntry();
        configDataEntry.setName("normal insert 666");
        configDataEntry.setNodeId("666");
        entryMapper.insert(configDataEntry);
        asyncService.asyncInsert();
    }

    public String getConfig(String id) {

        System.out.println(weChat.getApplication() + "=============");
        ConfigDataEntry configDataEntry = entryMapper.selectByPrimaryKey(id);
        return configDataEntry.toString();
    }


    public String getConfig(ConfigDataEntry configDataEntry) {
        List<ConfigDataEntry> configDataEntries = entryMapper.selectByParam(configDataEntry);
        return configDataEntries.toString();
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method test = TestClass.class.getDeclaredMethod("test");
        int modifiers = test.getModifiers();
        boolean b = (test.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC;
        System.out.println(b);
        System.out.println(1&1033);
        Double aDouble = 0.8;
        System.out.println(aDouble + "");
    }


}