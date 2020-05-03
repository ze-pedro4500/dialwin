package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserPermissions.
 */
@Entity
@Table(name = "user_permissions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne(mappedBy = "userPermissions")
    @JsonIgnore
    private UserExtra userExtra;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDemograf() {
        return demograf;
    }

    public UserPermissions demograf(Integer demograf) {
        this.demograf = demograf;
        return this;
    }

    public void setDemograf(Integer demograf) {
        this.demograf = demograf;
    }

    public Integer getSocial() {
        return social;
    }

    public UserPermissions social(Integer social) {
        this.social = social;
        return this;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public Integer getProcClin() {
        return procClin;
    }

    public UserPermissions procClin(Integer procClin) {
        this.procClin = procClin;
        return this;
    }

    public void setProcClin(Integer procClin) {
        this.procClin = procClin;
    }

    public Integer getDialEnf() {
        return dialEnf;
    }

    public UserPermissions dialEnf(Integer dialEnf) {
        this.dialEnf = dialEnf;
        return this;
    }

    public void setDialEnf(Integer dialEnf) {
        this.dialEnf = dialEnf;
    }

    public Integer getDialStat() {
        return dialStat;
    }

    public UserPermissions dialStat(Integer dialStat) {
        this.dialStat = dialStat;
        return this;
    }

    public void setDialStat(Integer dialStat) {
        this.dialStat = dialStat;
    }

    public Integer getQualiStat() {
        return qualiStat;
    }

    public UserPermissions qualiStat(Integer qualiStat) {
        this.qualiStat = qualiStat;
        return this;
    }

    public void setQualiStat(Integer qualiStat) {
        this.qualiStat = qualiStat;
    }

    public Integer getLabLink() {
        return labLink;
    }

    public UserPermissions labLink(Integer labLink) {
        this.labLink = labLink;
        return this;
    }

    public void setLabLink(Integer labLink) {
        this.labLink = labLink;
    }

    public Integer getGidLink() {
        return gidLink;
    }

    public UserPermissions gidLink(Integer gidLink) {
        this.gidLink = gidLink;
        return this;
    }

    public void setGidLink(Integer gidLink) {
        this.gidLink = gidLink;
    }

    public Integer getAsterixFarma() {
        return asterixFarma;
    }

    public UserPermissions asterixFarma(Integer asterixFarma) {
        this.asterixFarma = asterixFarma;
        return this;
    }

    public void setAsterixFarma(Integer asterixFarma) {
        this.asterixFarma = asterixFarma;
    }

    public Integer getAsterixGabMed() {
        return asterixGabMed;
    }

    public UserPermissions asterixGabMed(Integer asterixGabMed) {
        this.asterixGabMed = asterixGabMed;
        return this;
    }

    public void setAsterixGabMed(Integer asterixGabMed) {
        this.asterixGabMed = asterixGabMed;
    }

    public UserExtra getUserExtra() {
        return userExtra;
    }

    public UserPermissions userExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPermissions)) {
            return false;
        }
        return id != null && id.equals(((UserPermissions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserPermissions{" +
            "id=" + getId() +
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
