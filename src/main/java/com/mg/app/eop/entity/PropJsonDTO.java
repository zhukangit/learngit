package com.mg.app.eop.entity;

import com.alibaba.fastjson.JSONObject;

public class PropJsonDTO {
    private String name;
    private String value;

    public PropJsonDTO(PropertiesDTO jsonDTO) {
        this.name = jsonDTO.getName();
        this.value = jsonDTO.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }

    public static void main(String[] args) {
//        PropertiesDTO d = new PropertiesDTO();
//        d.setName("test");
//        d.setValue("234");
//        System.out.println(JSON.toJSONString(new PropJsonDTO(d)));
        String a = "{name:test,value:234}";
        JSONObject jo = new JSONObject();
        jo.put("a", "b");
        System.out.println(jo.toJSONString());
    }
}
