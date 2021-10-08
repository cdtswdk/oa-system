package com.cdt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @Auther: chendongtao
 * @Date: 2021/9/27 17:00
 * @Description:
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SingleToken {
    private String token;
    private Map<String, String> tokenMap;

    private String[] roles;
    private String introduction;
    private String avatar;
    private String name;
}
