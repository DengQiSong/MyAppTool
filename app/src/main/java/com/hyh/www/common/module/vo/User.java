package com.hyh.www.common.module.vo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 作者：Denqs on 2017/2/8.
 */

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private int age;
    private String careers;
    @Generated(hash = 1549616111)
    public User(Long id, String name, int age, String careers) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.careers = careers;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getCareers() {
        return this.careers;
    }
    public void setCareers(String careers) {
        this.careers = careers;
    }



}
