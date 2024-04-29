package com.xhoni.NewsApp.models;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String key_word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User addedInterest;
    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateAt;

    @PrePersist
    protected void onCreate(){
        this.createAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updateAt = new Date();
    }

    public Interest(Long id, String key_word, User addedInterest, Date createAt, Date updateAt) {
        this.id = id;
        this.key_word = key_word;
        this.addedInterest = addedInterest;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Interest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey_Word() {
        return key_word;
    }

    public void setKey_Word(String key_word) {
        this.key_word = key_word;
    }

    public User getAddedInterest() {
        return addedInterest;
    }

    public void setAddedInterest(User addedInterest) {
        this.addedInterest = addedInterest;
    }
}

