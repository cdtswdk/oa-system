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

@Table(name = "employee_inf")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeInf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String password;

    private Integer deptId;

    private Integer jobId;

    private String name;

    private String cardId;

    private String phone;

    private String email;

    private Integer sex;

    private String party;

    private String race;

    private String education;

    private Date createDate;

    private String imgname;
}