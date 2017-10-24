/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "broker_link")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BrokerLink.findAll", query = "SELECT b FROM BrokerLink b")
    , @NamedQuery(name = "BrokerLink.findByIdentityProvider", query = "SELECT b FROM BrokerLink b WHERE b.brokerLinkPK.identityProvider = :identityProvider")
    , @NamedQuery(name = "BrokerLink.findByStorageProviderId", query = "SELECT b FROM BrokerLink b WHERE b.storageProviderId = :storageProviderId")
    , @NamedQuery(name = "BrokerLink.findByRealmId", query = "SELECT b FROM BrokerLink b WHERE b.realmId = :realmId")
    , @NamedQuery(name = "BrokerLink.findByBrokerUserId", query = "SELECT b FROM BrokerLink b WHERE b.brokerUserId = :brokerUserId")
    , @NamedQuery(name = "BrokerLink.findByBrokerUsername", query = "SELECT b FROM BrokerLink b WHERE b.brokerUsername = :brokerUsername")
    , @NamedQuery(name = "BrokerLink.findByToken", query = "SELECT b FROM BrokerLink b WHERE b.token = :token")
    , @NamedQuery(name = "BrokerLink.findByUserId", query = "SELECT b FROM BrokerLink b WHERE b.brokerLinkPK.userId = :userId")})
public class BrokerLink implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BrokerLinkPK brokerLinkPK;
    @Column(name = "storage_provider_id")
    private String storageProviderId;
    @Basic(optional = false)
    @Column(name = "realm_id")
    private String realmId;
    @Column(name = "broker_user_id")
    private String brokerUserId;
    @Column(name = "broker_username")
    private String brokerUsername;
    @Column(name = "token")
    private String token;

    public BrokerLink() {
    }

    public BrokerLink(BrokerLinkPK brokerLinkPK) {
        this.brokerLinkPK = brokerLinkPK;
    }

    public BrokerLink(BrokerLinkPK brokerLinkPK, String realmId) {
        this.brokerLinkPK = brokerLinkPK;
        this.realmId = realmId;
    }

    public BrokerLink(String identityProvider, String userId) {
        this.brokerLinkPK = new BrokerLinkPK(identityProvider, userId);
    }

    public BrokerLinkPK getBrokerLinkPK() {
        return brokerLinkPK;
    }

    public void setBrokerLinkPK(BrokerLinkPK brokerLinkPK) {
        this.brokerLinkPK = brokerLinkPK;
    }

    public String getStorageProviderId() {
        return storageProviderId;
    }

    public void setStorageProviderId(String storageProviderId) {
        this.storageProviderId = storageProviderId;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getBrokerUserId() {
        return brokerUserId;
    }

    public void setBrokerUserId(String brokerUserId) {
        this.brokerUserId = brokerUserId;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public void setBrokerUsername(String brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (brokerLinkPK != null ? brokerLinkPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BrokerLink)) {
            return false;
        }
        BrokerLink other = (BrokerLink) object;
        if ((this.brokerLinkPK == null && other.brokerLinkPK != null) || (this.brokerLinkPK != null && !this.brokerLinkPK.equals(other.brokerLinkPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.bean.BrokerLink[ brokerLinkPK=" + brokerLinkPK + " ]";
    }
    
}
