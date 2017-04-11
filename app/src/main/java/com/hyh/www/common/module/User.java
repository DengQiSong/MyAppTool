package com.hyh.www.common.module;

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
    private String uio;
    private int tempUsageCount; // not persisted
    @Generated(hash = 1508352746)
    public User(Long id, String name, String uio, int tempUsageCount) {
        this.id = id;
        this.name = name;
        this.uio = uio;
        this.tempUsageCount = tempUsageCount;
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
    public String getUio() {
        return this.uio;
    }
    public void setUio(String uio) {
        this.uio = uio;
    }
    public int getTempUsageCount() {
        return this.tempUsageCount;
    }
    public void setTempUsageCount(int tempUsageCount) {
        this.tempUsageCount = tempUsageCount;
    }


}
