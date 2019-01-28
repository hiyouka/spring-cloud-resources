package com.hiyouka.sources.dao;

import com.hiyouka.sources.pojo.StockOrder;
import com.hiyouka.sources.pojo.StockOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockOrderMapper {
    int countByExample(StockOrderExample example);

    int deleteByExample(StockOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StockOrder record);

    int insertSelective(StockOrder record);

    List<StockOrder> selectByExample(StockOrderExample example);

    StockOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StockOrder record, @Param("example") StockOrderExample example);

    int updateByExample(@Param("record") StockOrder record, @Param("example") StockOrderExample example);

    int updateByPrimaryKeySelective(StockOrder record);

    int updateByPrimaryKey(StockOrder record);
}