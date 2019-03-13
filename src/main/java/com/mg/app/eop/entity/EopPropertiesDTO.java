package com.mg.app.eop.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/3/5 17:32
 * @功能说明： 功能描述
 */
public class EopPropertiesDTO {

    String name               ;
    String apiCode            ;
    String apiResName         ;
    String apiCenterName      ;
    String apiProdUrl         ;
    String apiPath            ;
    String apiResTime;

    public String getApiResTime() {
        return apiResTime;
    }

    public void setApiResTime(String apiResTime) {
        this.apiResTime = apiResTime;
    }

    List<EopActionPropertiesDTO>  listActiondto =new ArrayList<>();

    public List<EopActionPropertiesDTO> getListActiondto() {
        return listActiondto;
    }

    public void setListActiondto(List<EopActionPropertiesDTO> listActiondto) {
        this.listActiondto = listActiondto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getApiResName() {
        return apiResName;
    }

    public void setApiResName(String apiResName) {
        this.apiResName = apiResName;
    }

    public String getApiCenterName() {
        return apiCenterName;
    }

    public void setApiCenterName(String apiCenterName) {
        this.apiCenterName = apiCenterName;
    }

    public String getApiProdUrl() {
        return apiProdUrl;
    }

    public void setApiProdUrl(String apiProdUrl) {
        this.apiProdUrl = apiProdUrl;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}
