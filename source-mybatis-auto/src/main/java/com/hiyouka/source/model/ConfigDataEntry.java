package com.hiyouka.source.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigDataEntry {
    private String nodeId;

    private String name;

    private String upperLimit;

    private String lowerLimit;

    private String line;

    private String category;

    private String categoryName;

    private String type;

    private String typeName;

    private String nodeType;

    @JsonProperty("item.orgNo")
    private String orgNo;

    private String alarmConfig;

    @JsonProperty("item.orderNo")
    private Integer orderNo;


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit == null ? null : upperLimit.trim();
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit == null ? null : lowerLimit.trim();
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line == null ? null : line.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getAlarmConfig() {
        return alarmConfig;
    }

    public void setAlarmConfig(String alarmConfig) {
        this.alarmConfig = alarmConfig == null ? null : alarmConfig.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }


    @Override
    public String toString() {
        return "ConfigDataEntry{" +
                "nodeId='" + nodeId + '\'' +
                ", name='" + name + '\'' +
                ", upperLimit='" + upperLimit + '\'' +
                ", lowerLimit='" + lowerLimit + '\'' +
                ", line='" + line + '\'' +
                ", category='" + category + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", orgNo='" + orgNo + '\'' +
                ", alarmConfig='" + alarmConfig + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }
}