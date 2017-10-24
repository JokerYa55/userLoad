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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasiliy.andricov
 */
@Entity
@Table(name = "identity_provider")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IdentityProvider.findAll", query = "SELECT i FROM IdentityProvider i")
    , @NamedQuery(name = "IdentityProvider.findByInternalId", query = "SELECT i FROM IdentityProvider i WHERE i.internalId = :internalId")
    , @NamedQuery(name = "IdentityProvider.findByEnabled", query = "SELECT i FROM IdentityProvider i WHERE i.enabled = :enabled")
    , @NamedQuery(name = "IdentityProvider.findByProviderAlias", query = "SELECT i FROM IdentityProvider i WHERE i.providerAlias = :providerAlias")
    , @NamedQuery(name = "IdentityProvider.findByProviderId", query = "SELECT i FROM IdentityProvider i WHERE i.providerId = :providerId")
    , @NamedQuery(name = "IdentityProvider.findByStoreToken", query = "SELECT i FROM IdentityProvider i WHERE i.storeToken = :storeToken")
    , @NamedQuery(name = "IdentityProvider.findByAuthenticateByDefault", query = "SELECT i FROM IdentityProvider i WHERE i.authenticateByDefault = :authenticateByDefault")
    , @NamedQuery(name = "IdentityProvider.findByAddTokenRole", query = "SELECT i FROM IdentityProvider i WHERE i.addTokenRole = :addTokenRole")
    , @NamedQuery(name = "IdentityProvider.findByTrustEmail", query = "SELECT i FROM IdentityProvider i WHERE i.trustEmail = :trustEmail")
    , @NamedQuery(name = "IdentityProvider.findByFirstBrokerLoginFlowId", query = "SELECT i FROM IdentityProvider i WHERE i.firstBrokerLoginFlowId = :firstBrokerLoginFlowId")
    , @NamedQuery(name = "IdentityProvider.findByPostBrokerLoginFlowId", query = "SELECT i FROM IdentityProvider i WHERE i.postBrokerLoginFlowId = :postBrokerLoginFlowId")
    , @NamedQuery(name = "IdentityProvider.findByProviderDisplayName", query = "SELECT i FROM IdentityProvider i WHERE i.providerDisplayName = :providerDisplayName")
    , @NamedQuery(name = "IdentityProvider.findByLinkOnly", query = "SELECT i FROM IdentityProvider i WHERE i.linkOnly = :linkOnly")})
public class IdentityProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "internal_id")
    private String internalId;
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "provider_alias")
    private String providerAlias;
    @Column(name = "provider_id")
    private String providerId;
    @Basic(optional = false)
    @Column(name = "store_token")
    private boolean storeToken;
    @Basic(optional = false)
    @Column(name = "authenticate_by_default")
    private boolean authenticateByDefault;
    @Basic(optional = false)
    @Column(name = "add_token_role")
    private boolean addTokenRole;
    @Basic(optional = false)
    @Column(name = "trust_email")
    private boolean trustEmail;
    @Column(name = "first_broker_login_flow_id")
    private String firstBrokerLoginFlowId;
    @Column(name = "post_broker_login_flow_id")
    private String postBrokerLoginFlowId;
    @Column(name = "provider_display_name")
    private String providerDisplayName;
    @Basic(optional = false)
    @Column(name = "link_only")
    private boolean linkOnly;

    public IdentityProvider() {
    }

    public IdentityProvider(String internalId) {
        this.internalId = internalId;
    }

    public IdentityProvider(String internalId, boolean enabled, boolean storeToken, boolean authenticateByDefault, boolean addTokenRole, boolean trustEmail, boolean linkOnly) {
        this.internalId = internalId;
        this.enabled = enabled;
        this.storeToken = storeToken;
        this.authenticateByDefault = authenticateByDefault;
        this.addTokenRole = addTokenRole;
        this.trustEmail = trustEmail;
        this.linkOnly = linkOnly;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProviderAlias() {
        return providerAlias;
    }

    public void setProviderAlias(String providerAlias) {
        this.providerAlias = providerAlias;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean getStoreToken() {
        return storeToken;
    }

    public void setStoreToken(boolean storeToken) {
        this.storeToken = storeToken;
    }

    public boolean getAuthenticateByDefault() {
        return authenticateByDefault;
    }

    public void setAuthenticateByDefault(boolean authenticateByDefault) {
        this.authenticateByDefault = authenticateByDefault;
    }

    public boolean getAddTokenRole() {
        return addTokenRole;
    }

    public void setAddTokenRole(boolean addTokenRole) {
        this.addTokenRole = addTokenRole;
    }

    public boolean getTrustEmail() {
        return trustEmail;
    }

    public void setTrustEmail(boolean trustEmail) {
        this.trustEmail = trustEmail;
    }

    public String getFirstBrokerLoginFlowId() {
        return firstBrokerLoginFlowId;
    }

    public void setFirstBrokerLoginFlowId(String firstBrokerLoginFlowId) {
        this.firstBrokerLoginFlowId = firstBrokerLoginFlowId;
    }

    public String getPostBrokerLoginFlowId() {
        return postBrokerLoginFlowId;
    }

    public void setPostBrokerLoginFlowId(String postBrokerLoginFlowId) {
        this.postBrokerLoginFlowId = postBrokerLoginFlowId;
    }

    public String getProviderDisplayName() {
        return providerDisplayName;
    }

    public void setProviderDisplayName(String providerDisplayName) {
        this.providerDisplayName = providerDisplayName;
    }

    public boolean getLinkOnly() {
        return linkOnly;
    }

    public void setLinkOnly(boolean linkOnly) {
        this.linkOnly = linkOnly;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (internalId != null ? internalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentityProvider)) {
            return false;
        }
        IdentityProvider other = (IdentityProvider) object;
        if ((this.internalId == null && other.internalId != null) || (this.internalId != null && !this.internalId.equals(other.internalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.bean.IdentityProvider[ internalId=" + internalId + " ]";
    }
    
}
