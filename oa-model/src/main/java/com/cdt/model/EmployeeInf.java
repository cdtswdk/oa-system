package com.cdt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String imgname;
}