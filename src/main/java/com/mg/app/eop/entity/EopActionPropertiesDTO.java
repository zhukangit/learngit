package com.mg.app.eop.entity;

/**
 * 类名描述
 *
 * @author ideal
 * @version Revision 版本号
 * @版权： 版权所有 (c) 2018
 * @创建日期： 2019/3/5 17:36
 * @功能说明： 功能描述
 */
public class EopActionPropertiesDTO {
   String apiCode;
   String apiAction;
   String apiResPath;

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getApiAction() {
        return apiAction;
    }

    public void setApiAction(String apiAction) {
        this.apiAction = apiAction;
    }

    public String getApiResPath() {
        return apiResPath;
    }

    public void setApiResPath(String apiResPath) {
        this.apiResPath = apiResPath;
    }
}
