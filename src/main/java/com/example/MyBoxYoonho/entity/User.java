package com.example.MyBoxYoonho.entity;

import jakarta.persistence.*;
import org.apache.catalina.users.GenericRole;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="used_storage")
    private double usedStorage;

    @Column(name="remaining_storage")
    private double remainingStorage;

    public User(){

    }

    public User(double usedStorage, double remainingStorage){
        this.usedStorage = usedStorage;
        this.remainingStorage = remainingStorage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(double usedStorage) {
        this.usedStorage = usedStorage;
    }

    public double getRemainingStorage() {
        return remainingStorage;
    }

    public void setRemainingStorage(double remainingStorage) {
        this.remainingStorage = remainingStorage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usedStorage=" + usedStorage +
                ", remainingStorage=" + remainingStorage +
                '}';
    }
}
