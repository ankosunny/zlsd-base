package  com.zhilingsd.base.common.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * key value 封装类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyValueBean implements Serializable {

    private static final long serialVersionUID = 4983561853866668930L;
    private String code;
    private String name;
}
