/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasiliy.andricov
 */
@Entity
@Table(name = "federated_identity", catalog = "db_users_keycloak", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FederatedIdentity.findAll", query = "SELECT f FROM FederatedIdentity f")
    , @NamedQuery(name = "FederatedIdentity.findByIdentityProvider", query = "SELECT f FROM FederatedIdentity f WHERE f.federatedIdentityPK.identityProvider = :identityProvider")
    , @NamedQuery(name = "FederatedIdentity.findByRealmId", query = "SELECT f FROM FederatedIdentity f WHERE f.realmId = :realmId")
    , @NamedQuery(name = "FederatedIdentity.findByFederatedUserId", query = "SELECT f FROM FederatedIdentity f WHERE f.federatedUserId = :federatedUserId")
    , @NamedQuery(name = "FederatedIdentity.findByFederatedUsername", query = "SELECT f FROM FederatedIdentity f WHERE f.federatedUsername = :federatedUsername")
    , @NamedQuery(name = "FederatedIdentity.findByToken", query = "SELECT f FROM FederatedIdentity f WHERE f.token = :token")
    , @NamedQuery(name = "FederatedIdentity.findByUserId", query = "SELECT f FROM FederatedIdentity f WHERE f.federatedIdentityPK.userId = :userId")})
public class FederatedIdentity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FederatedIdentityPK federatedIdentityPK;
    @Column(name = "realm_id")
    private String realmId;
    @Column(name = "federated_user_id")
    private String federatedUserId;
    @Column(name = "federated_username")
    private String federatedUsername;
    private String token;

    public FederatedIdentity() {
    }

    public FederatedIdentity(FederatedIdentityPK federatedIdentityPK) {
        this.federatedIdentityPK = federatedIdentityPK;
    }

    public FederatedIdentity(String identityProvider, String userId) {
        this.federatedIdentityPK = new FederatedIdentityPK(identityProvider, userId);
    }

    public FederatedIdentityPK getFederatedIdentityPK() {
        return federatedIdentityPK;
    }

    public void setFederatedIdentityPK(FederatedIdentityPK federatedIdentityPK) {
        this.federatedIdentityPK = federatedIdentityPK;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getFederatedUserId() {
        return federatedUserId;
    }

    public void setFederatedUserId(String federatedUserId) {
        this.federatedUserId = federatedUserId;
    }

    public String getFederatedUsername() {
        return federatedUsername;
    }

    public void setFederatedUsername(String federatedUsername) {
        this.federatedUsername = federatedUsername;
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
        hash += (federatedIdentityPK != null ? federatedIdentityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FederatedIdentity)) {
            return false;
        }
        FederatedIdentity other = (FederatedIdentity) object;
        if ((this.federatedIdentityPK == null && other.federatedIdentityPK != null) || (this.federatedIdentityPK != null && !this.federatedIdentityPK.equals(other.federatedIdentityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.bean.FederatedIdentity[ federatedIdentityPK=" + federatedIdentityPK + " ]";
    }
    
}
