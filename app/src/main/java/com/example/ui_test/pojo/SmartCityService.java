package com.example.ui_test.pojo;

public class SmartCityService {

    private int id;
    private String serviceName;
    private String serviceDesc;
    private String serviceType;
    private String imgUrl;
    private int sort;

    public SmartCityService(int id, String serviceName, String serviceDesc, String serviceType, String imgUrl, int sort) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceDesc = serviceDesc;
        this.serviceType = serviceType;
        this.imgUrl = imgUrl;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SmartCityService{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDesc='" + serviceDesc + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", sort=" + sort +
                '}';
    }
}
