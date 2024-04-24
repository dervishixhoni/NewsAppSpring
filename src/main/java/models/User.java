package models;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty()
    @Size(min = 3)
    private String userName;
    @NotEmpty()
    @Email
    private String email;
    @NotEmpty()
    @Size(min = 8)
    private String password;

    @NotEmpty
    private Boolean isVerified;

    @NotEmpty
    private String VerificatonCode;

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
    @OneToMany(mappedBy = "addedArticle" , fetch = FetchType.LAZY)
    private List<Article> articles;
    @OneToMany(mappedBy = "addedInterest" , fetch = FetchType.LAZY)
    private List<Interest> interests;
    public User() {
    }

    public User(Long id, String userName, String email, String password, Date createAt, Date updateAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsVerified(Boolean )

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}