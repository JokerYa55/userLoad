/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_user_attribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUserAttribute.findAll", query = "SELECT t FROM TUserAttribute t")
    , @NamedQuery(name = "TUserAttribute.findById", query = "SELECT t FROM TUserAttribute t WHERE t.id = :id")
    , @NamedQuery(name = "TUserAttribute.findByName", query = "SELECT t FROM TUserAttribute t WHERE t.name = :name")
    , @NamedQuery(name = "TUserAttribute.findByValue", query = "SELECT t FROM TUserAttribute t WHERE t.value = :value")
    , @NamedQuery(name = "TUserAttribute.findByVisibleFlag", query = "SELECT t FROM TUserAttribute t WHERE t.visibleFlag = :visibleFlag")})
public class TUserAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_user_attribute_id_seq")
    @SequenceGenerator(name = "t_user_attribute_id_seq", sequenceName = "t_user_attribute_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @Column(name = "visible_flag")
    private boolean visibleFlag;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TUsers userId;

    public TUserAttribute() {
    }

    public TUserAttribute(Long id) {
        this.id = id;
    }

    public TUserAttribute(Long id, String name, String value, boolean visibleFlag) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.visibleFlag = visibleFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getVisibleFlag() {
        return visibleFlag;
    }

    public void setVisibleFlag(boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public TUsers getUserId() {
        return userId;
    }

    public void setUserId(TUsers userId) {
        this.userId = userId;
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
        if (!(object instanceof TUserAttribute)) {
            return false;
        }
        TUserAttribute other = (TUserAttribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.bean.TUserAttribute[ id=" + id + " ]";
    }
    
}
