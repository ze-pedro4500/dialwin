package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "morada")
    private String morada;

    @Column(name = "cod_p")
    private String codP;

    @Column(name = "telef")
    private String telef;

    @Column(name = "permiss_change")
    private Boolean permissChange;

    @Column(name = "nif")
    private Integer nif;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private UserPermissions userPermissions;

    @ManyToOne
    @JsonIgnoreProperties("userExtras")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActivo() {
        return activo;
    }

    public UserExtra activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNome() {
        return nome;
    }

    public UserExtra nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public UserExtra morada(String morada) {
        this.morada = morada;
        return this;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCodP() {
        return codP;
    }

    public UserExtra codP(String codP) {
        this.codP = codP;
        return this;
    }

    public void setCodP(String codP) {
        this.codP = codP;
    }

    public String getTelef() {
        return telef;
    }

    public UserExtra telef(String telef) {
        this.telef = telef;
        return this;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }

    public Boolean isPermissChange() {
        return permissChange;
    }

    public UserExtra permissChange(Boolean permissChange) {
        this.permissChange = permissChange;
        return this;
    }

    public void setPermissChange(Boolean permissChange) {
        this.permissChange = permissChange;
    }

    public Integer getNif() {
        return nif;
    }

    public UserExtra nif(Integer nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPermissions getUserPermissions() {
        return userPermissions;
    }

    public UserExtra userPermissions(UserPermissions userPermissions) {
        this.userPermissions = userPermissions;
        return this;
    }

    public void setUserPermissions(UserPermissions userPermissions) {
        this.userPermissions = userPermissions;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserExtra userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", activo='" + isActivo() + "'" +
            ", nome='" + getNome() + "'" +
            ", morada='" + getMorada() + "'" +
            ", codP='" + getCodP() + "'" +
            ", telef='" + getTelef() + "'" +
            ", permissChange='" + isPermissChange() + "'" +
            ", nif=" + getNif() +
            "}";
    }
}
