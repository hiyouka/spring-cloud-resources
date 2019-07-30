package com.hiyouka.source.mapper;

import com.hiyouka.source.model.ConfigDataEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfigDataEntryMapper {

    int deleteByPrimaryKey(String nodeId);

    int insert(ConfigDataEntry record);

    int insertSelective(ConfigDataEntry record);

    ConfigDataEntry selectByPrimaryKey(@Param("nodeId") String nodeId);

    List<ConfigDataEntry> selectByParam(ConfigDataEntry configDataEntry);

    int updateByPrimaryKeySelective(ConfigDataEntry record);

    int updateByPrimaryKey(ConfigDataEntry record);

    default int updateDefault(ConfigDataEntry dataEntry){
        return 1;
    }
}