package me.plugins.sharpeconomy.models;

import java.util.Date;

public class PlayerData {
    public String uuid;
    public String name;
    public Long balance;
    public Date last_login;
    public Date last_logout;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public void setLast_logout(Date last_logout) {
        this.last_logout = last_logout;
    }

    public PlayerData(String uuid, String name, Long balance, Date last_login, Date last_logout) {
        this.uuid = uuid;
        this.name = name;
        this.balance = balance;
        this.last_login = last_login;
        this.last_logout = last_logout;
    }

    public String getName() {
        return name;
    }

    public Date getLastLogin() {
        return last_login;
    }

    public Date getLastLogout() {
        return last_logout;
    }

    public Long getBalance() {
        return balance;
    }

    public String getUuid() {
        return uuid;
    }
}
