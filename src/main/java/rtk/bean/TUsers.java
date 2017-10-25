/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUsers.findAll", query = "SELECT t FROM TUsers t")
    , @NamedQuery(name = "TUsers.findById", query = "SELECT t FROM TUsers t WHERE t.id = :id")
    , @NamedQuery(name = "TUsers.findByCreateDate", query = "SELECT t FROM TUsers t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TUsers.findByDescription", query = "SELECT t FROM TUsers t WHERE t.description = :description")
    , @NamedQuery(name = "TUsers.findByEmail", query = "SELECT t FROM TUsers t WHERE t.email = :email")
    , @NamedQuery(name = "TUsers.findByEnabled", query = "SELECT t FROM TUsers t WHERE t.enabled = :enabled")
    , @NamedQuery(name = "TUsers.findByFirstname", query = "SELECT t FROM TUsers t WHERE t.firstname = :firstname")
    , @NamedQuery(name = "TUsers.findByHashType", query = "SELECT t FROM TUsers t WHERE t.hashType = :hashType")
    , @NamedQuery(name = "TUsers.findByLastname", query = "SELECT t FROM TUsers t WHERE t.lastname = :lastname")
    , @NamedQuery(name = "TUsers.findByPassword", query = "SELECT t FROM TUsers t WHERE t.password = :password")
    , @NamedQuery(name = "TUsers.findByPhone", query = "SELECT t FROM TUsers t WHERE t.phone = :phone")
    , @NamedQuery(name = "TUsers.findBySalt", query = "SELECT t FROM TUsers t WHERE t.salt = :salt")
    , @NamedQuery(name = "TUsers.findByThirdname", query = "SELECT t FROM TUsers t WHERE t.thirdname = :thirdname")
    , @NamedQuery(name = "TUsers.findByUpdateDate", query = "SELECT t FROM TUsers t WHERE t.updateDate = :updateDate")
    , @NamedQuery(name = "TUsers.findByUserRegion", query = "SELECT t FROM TUsers t WHERE t.userRegion = :userRegion")
    , @NamedQuery(name = "TUsers.findByUserStatus", query = "SELECT t FROM TUsers t WHERE t.userStatus = :userStatus")
    , @NamedQuery(name = "TUsers.findByUsername", query = "SELECT t FROM TUsers t WHERE t.username = :username")})
public class TUsers implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<TUserAttribute> tUserAttributeCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_id_seq")
    @SequenceGenerator(name = "t_users_id_seq", sequenceName = "t_users_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "description")
    private String description;
    @Column(name = "email")
    private String email;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "hash_type")
    private String hashType;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "salt")
    private String salt;
    @Column(name = "thirdname")
    private String thirdname;
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "user_region")
    private Integer userRegion;
    @Column(name = "user_status")
    private int userStatus;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    public TUsers() {
    }

    public TUsers(Long id) {
        this.id = id;
    }

    public TUsers(Long id, Date createDate, boolean enabled, int userStatus, String username) {
        this.id = id;
        this.createDate = createDate;
        this.enabled = enabled;
        this.userStatus = userStatus;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getHashType() {
        return hashType;
    }

    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(Integer userRegion) {
        this.userRegion = userRegion;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TUsers)) {
            return false;
        }
        TUsers other = (TUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TUsers{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", thirdname=" + thirdname + ", username=" + username + '}';
    }

    @XmlTransient
    public Collection<TUserAttribute> getTUserAttributeCollection() {
        return tUserAttributeCollection;
    }

    public void setTUserAttributeCollection(Collection<TUserAttribute> tUserAttributeCollection) {
        this.tUserAttributeCollection = tUserAttributeCollection;
    }

    

}
