/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author vasil
 */
@Embeddable
public class BrokerLinkPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "identity_provider")
    private String identityProvider;
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;

    public BrokerLinkPK() {
    }

    public BrokerLinkPK(String identityProvider, String userId) {
        this.identityProvider = identityProvider;
        this.userId = userId;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identityProvider != null ? identityProvider.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BrokerLinkPK)) {
            return false;
        }
        BrokerLinkPK other = (BrokerLinkPK) object;
        if ((this.identityProvider == null && other.identityProvider != null) || (this.identityProvider != null && !this.identityProvider.equals(other.identityProvider))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.bean.BrokerLinkPK[ identityProvider=" + identityProvider + ", userId=" + userId + " ]";
    }
    
}
