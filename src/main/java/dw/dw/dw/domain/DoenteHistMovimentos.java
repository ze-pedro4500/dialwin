package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DoenteHistMovimentos.
 */
@Entity
@Table(name = "doente_hist_movimentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoenteHistMovimentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "situacao")
    private String situacao;

    @Column(name = "obs")
    private String obs;

    @ManyToOne
    @JsonIgnoreProperties("doenteHistMovimentos")
    private Doente doente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public DoenteHistMovimentos data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSituacao() {
        return situacao;
    }

    public DoenteHistMovimentos situacao(String situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getObs() {
        return obs;
    }

    public DoenteHistMovimentos obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Doente getDoente() {
        return doente;
    }

    public DoenteHistMovimentos doente(Doente doente) {
        this.doente = doente;
        return this;
    }

    public void setDoente(Doente doente) {
        this.doente = doente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoenteHistMovimentos)) {
            return false;
        }
        return id != null && id.equals(((DoenteHistMovimentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoenteHistMovimentos{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", obs='" + getObs() + "'" +
            "}";
    }
}
