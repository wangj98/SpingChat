package com.wang.springboot.util;

import lombok.Data;
//按页查询数据,两个用户的id,页码,每页数据量
@Data
public class SelectPage {

    private Integer fromUid;
    private Integer toUid;
    private Integer current;
    private Integer size;

}
