package com.tbxx.wpct.entity.wxpush;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxMsgTemplateHasten
 * @Description TODO
 * @Author ZXX
 * @DATE 2022/10/12 13:49
 */


/**
 * 定义消息模板参数实体
 */

@Getter
public class WxMsgTemplateHasten {

    /*账单日期*/
    private Map<String, String> time31;

    /*房间名称*/
    private Map<String, String> thing30;

    /*订单号*/
    private Map<String, String> character_string29;

    /*总欠金额*/
    private Map<String, String> amount28;

    /*本次交易*/
    private Map<String, String> amount27;



    public HashMap<String, String> getFormat(String str) {
        return new HashMap<String, String>() {{
            put("value", str);
        }};
    }


    public void setTime31(String time31) {
        this.time31 = getFormat(time31);
    }

    public void setThing30(String thing30) {
        this.thing30 = getFormat(thing30);
    }

    public void setCharacter_string29(String character_string29) {
        this.character_string29 = getFormat(character_string29);
    }

    public void setAmount28(String amount28) {
        this.amount28 = getFormat(amount28);
    }

    public void setAmount27(String amount27) {
        this.amount27 = getFormat(amount27);
    }

}
