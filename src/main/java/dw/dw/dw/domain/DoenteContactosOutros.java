package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DoenteContactosOutros.
 */
@Entity
@Table(name = "doente_contactos_outros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoenteContactosOutros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "parentesco")
    private String parentesco;

    @Column(name = "coabita")
    private Boolean coabita;

    @Column(name = "telef")
    private Integer telef;

    @Column(name = "ocupacao")
    private String ocupacao;

    @Column(name = "obs")
    private String obs;

    @Column(name = "preferencial")
    private Boolean preferencial;

    @ManyToOne
    @JsonIgnoreProperties("doenteContactosOutros")
    private Doente doente;

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

    public DoenteContactosOutros nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getParentesco() {
        return parentesco;
    }

    public DoenteContactosOutros parentesco(String parentesco) {
        this.parentesco = parentesco;
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Boolean isCoabita() {
        return coabita;
    }

    public DoenteContactosOutros coabita(Boolean coabita) {
        this.coabita = coabita;
        return this;
    }

    public void setCoabita(Boolean coabita) {
        this.coabita = coabita;
    }

    public Integer getTelef() {
        return telef;
    }

    public DoenteContactosOutros telef(Integer telef) {
        this.telef = telef;
        return this;
    }

    public void setTelef(Integer telef) {
        this.telef = telef;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public DoenteContactosOutros ocupacao(String ocupacao) {
        this.ocupacao = ocupacao;
        return this;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getObs() {
        return obs;
    }

    public DoenteContactosOutros obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Boolean isPreferencial() {
        return preferencial;
    }

    public DoenteContactosOutros preferencial(Boolean preferencial) {
        this.preferencial = preferencial;
        return this;
    }

    public void setPreferencial(Boolean preferencial) {
        this.preferencial = preferencial;
    }

    public Doente getDoente() {
        return doente;
    }

    public DoenteContactosOutros doente(Doente doente) {
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
        if (!(o instanceof DoenteContactosOutros)) {
            return false;
        }
        return id != null && id.equals(((DoenteContactosOutros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoenteContactosOutros{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", coabita='" + isCoabita() + "'" +
            ", telef=" + getTelef() +
            ", ocupacao='" + getOcupacao() + "'" +
            ", obs='" + getObs() + "'" +
            ", preferencial='" + isPreferencial() + "'" +
            "}";
    }
}
