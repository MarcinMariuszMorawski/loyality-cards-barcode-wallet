package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public final class User {
    private Long id;
    private String login;
    private String password;
    private Timestamp dateTimeOfLastPasswordChange;
    private Boolean active;

    public User() {
    }

    public User(String login, String password, Timestamp dateTimeOfLastPasswordChange, Boolean active) {
        this.login = login;
        this.password = password;
        this.dateTimeOfLastPasswordChange = dateTimeOfLastPasswordChange;
        this.active = active;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "date_time_of_last_password_change")
    public Timestamp getDateTimeOfLastPasswordChange() {
        return dateTimeOfLastPasswordChange;
    }

    public void setDateTimeOfLastPasswordChange(Timestamp dateTimeOfLastPasswordChange) {
        this.dateTimeOfLastPasswordChange = dateTimeOfLastPasswordChange;
    }

    @Basic
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}