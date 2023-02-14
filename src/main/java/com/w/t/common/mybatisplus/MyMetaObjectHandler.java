package com.w.t.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        this.setFieldValByName("createAt", currentTime, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Timestamp currentTime=new Timestamp(System.currentTimeMillis());
        this.setFieldValByName("updateAt", currentTime, metaObject);
    }

}
