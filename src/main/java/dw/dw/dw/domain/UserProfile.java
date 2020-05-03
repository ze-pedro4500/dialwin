package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "demograf")
    private Integer demograf;

    @Column(name = "social")
    private Integer social;

    @Column(name = "proc_clin")
    private Integer procClin;

    @Column(name = "dial_enf")
    private Integer dialEnf;

    @Column(name = "dial_stat")
    private Integer dialStat;

    @Column(name = "quali_stat")
    private Integer qualiStat;

    @Column(name = "lab_link")
    private Integer labLink;

    @Column(name = "gid_link")
    private Integer gidLink;

    @Column(name = "asterix_farma")
    private Integer asterixFarma;

    @Column(name = "asterix_gab_med")
    private Integer asterixGabMed;

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserExtra> userExtras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public UserProfile nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDemograf() {
        return demograf;
    }

    public UserProfile demograf(Integer demograf) {
        this.demograf = demograf;
        return this;
    }

    public void setDemograf(Integer demograf) {
        this.demograf = demograf;
    }

    public Integer getSocial() {
        return social;
    }

    public UserProfile social(Integer social) {
        this.social = social;
        return this;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public Integer getProcClin() {
        return procClin;
    }

    public UserProfile procClin(Integer procClin) {
        this.procClin = procClin;
        return this;
    }

    public void setProcClin(Integer procClin) {
        this.procClin = procClin;
    }

    public Integer getDialEnf() {
        return dialEnf;
    }

    public UserProfile dialEnf(Integer dialEnf) {
        this.dialEnf = dialEnf;
        return this;
    }

    public void setDialEnf(Integer dialEnf) {
        this.dialEnf = dialEnf;
    }

    public Integer getDialStat() {
        return dialStat;
    }

    public UserProfile dialStat(Integer dialStat) {
        this.dialStat = dialStat;
        return this;
    }

    public void setDialStat(Integer dialStat) {
        this.dialStat = dialStat;
    }

    public Integer getQualiStat() {
        return qualiStat;
    }

    public UserProfile qualiStat(Integer qualiStat) {
        this.qualiStat = qualiStat;
        return this;
    }

    public void setQualiStat(Integer qualiStat) {
        this.qualiStat = qualiStat;
    }

    public Integer getLabLink() {
        return labLink;
    }

    public UserProfile labLink(Integer labLink) {
        this.labLink = labLink;
        return this;
    }

    public void setLabLink(Integer labLink) {
        this.labLink = labLink;
    }

    public Integer getGidLink() {
        return gidLink;
    }

    public UserProfile gidLink(Integer gidLink) {
        this.gidLink = gidLink;
        return this;
    }

    public void setGidLink(Integer gidLink) {
        this.gidLink = gidLink;
    }

    public Integer getAsterixFarma() {
        return asterixFarma;
    }

    public UserProfile asterixFarma(Integer asterixFarma) {
        this.asterixFarma = asterixFarma;
        return this;
    }

    public void setAsterixFarma(Integer asterixFarma) {
        this.asterixFarma = asterixFarma;
    }

    public Integer getAsterixGabMed() {
        return asterixGabMed;
    }

    public UserProfile asterixGabMed(Integer asterixGabMed) {
        this.asterixGabMed = asterixGabMed;
        return this;
    }

    public void setAsterixGabMed(Integer asterixGabMed) {
        this.asterixGabMed = asterixGabMed;
    }

    public Set<UserExtra> getUserExtras() {
        return userExtras;
    }

    public UserProfile userExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
        return this;
    }

    public UserProfile addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setUserProfile(this);
        return this;
    }

    public UserProfile removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setUserProfile(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        this.userExtras = userExtras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", demograf=" + getDemograf() +
            ", social=" + getSocial() +
            ", procClin=" + getProcClin() +
            ", dialEnf=" + getDialEnf() +
            ", dialStat=" + getDialStat() +
            ", qualiStat=" + getQualiStat() +
            ", labLink=" + getLabLink() +
            ", gidLink=" + getGidLink() +
            ", asterixFarma=" + getAsterixFarma() +
            ", asterixGabMed=" + getAsterixGabMed() +
            "}";
    }
}
