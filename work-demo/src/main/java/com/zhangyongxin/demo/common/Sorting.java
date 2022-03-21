package com.zhangyongxin.demo.common;

import java.util.List;

/**
 * @Auther zhangyongxin
 * @date 2022/3/19 下午10:25
 */
public interface Sorting {

    List<Sort> getSorts();

    void addSort(Sort sort);
}
