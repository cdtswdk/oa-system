package com.cdt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_inf")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInf {
    // 自增主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String loginname;

    private String password;

    private Integer status;

    private Date createdate;

    private String username;

    private String imgname;
}