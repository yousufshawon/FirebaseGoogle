package com.shawon.yousuf.firebasegoogle.model;
//Created by Yousuf on 10/4/2016.

import com.shawon.yousuf.firebasegoogle.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Student {

    private String name;
    private long roll;
    private int age;

    private long updatedAt;
    private long createdAt;

    // Default constructor required for calls to DataSnapshot.getValue(.class)
    public Student() {
    }

    public Student(String name, long roll, int age) {
        this.name = name;
        this.roll = roll;
        this.age = age;
        updatedAt = System.currentTimeMillis();
        createdAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoll() {
        return roll;
    }

    public void setRoll(long roll) {
        this.roll = roll;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.TAG_NAME, name);
        result.put(Constants.TAG_ROLL, roll);
        result.put(Constants.TAG_AGE, age);
        result.put(Constants.TAG_UPDATED_AT, updatedAt);
        result.put(Constants.TAG_CREATED_AT, createdAt);

        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
