package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import dw.dw.dw.domain.enumeration.Situacao;

/**
 * A Doente.
 */
@Entity
@Table(name = "doente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Doente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private Situacao situacao;

    @OneToMany(mappedBy = "doente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HorarioDoente> horarioDoentes = new HashSet<>();

    @OneToMany(mappedBy = "doente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteDiagnosticoSocial> doenteDiagnosticoSocials = new HashSet<>();

    @OneToMany(mappedBy = "doente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteRegistosIntervencoes> doenteRegistosIntervencoes = new HashSet<>();

    @OneToMany(mappedBy = "doente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteHistMovimentos> doenteHistMovimentos = new HashSet<>();

    @OneToMany(mappedBy = "doente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteContactosOutros> doenteContactosOutros = new HashSet<>();

    @OneToOne(mappedBy = "doente")
    @JsonIgnore
    private DoenteIdentidade doenteIdentidade;

    @OneToOne(mappedBy = "doente")
    @JsonIgnore
    private DoenteContactos doenteContactos;

    @OneToOne(mappedBy = "doente")
    @JsonIgnore
    private DoenteSocioFamiliar doenteSocioFamiliar;

    @ManyToOne
    @JsonIgnoreProperties("doentes")
    private Turnos turnos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Doente situacao(Situacao situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Set<HorarioDoente> getHorarioDoentes() {
        return horarioDoentes;
    }

    public Doente horarioDoentes(Set<HorarioDoente> horarioDoentes) {
        this.horarioDoentes = horarioDoentes;
        return this;
    }

    public Doente addHorarioDoente(HorarioDoente horarioDoente) {
        this.horarioDoentes.add(horarioDoente);
        horarioDoente.setDoente(this);
        return this;
    }

    public Doente removeHorarioDoente(HorarioDoente horarioDoente) {
        this.horarioDoentes.remove(horarioDoente);
        horarioDoente.setDoente(null);
        return this;
    }

    public void setHorarioDoentes(Set<HorarioDoente> horarioDoentes) {
        this.horarioDoentes = horarioDoentes;
    }

    public Set<DoenteDiagnosticoSocial> getDoenteDiagnosticoSocials() {
        return doenteDiagnosticoSocials;
    }

    public Doente doenteDiagnosticoSocials(Set<DoenteDiagnosticoSocial> doenteDiagnosticoSocials) {
        this.doenteDiagnosticoSocials = doenteDiagnosticoSocials;
        return this;
    }

    public Doente addDoenteDiagnosticoSocial(DoenteDiagnosticoSocial doenteDiagnosticoSocial) {
        this.doenteDiagnosticoSocials.add(doenteDiagnosticoSocial);
        doenteDiagnosticoSocial.setDoente(this);
        return this;
    }

    public Doente removeDoenteDiagnosticoSocial(DoenteDiagnosticoSocial doenteDiagnosticoSocial) {
        this.doenteDiagnosticoSocials.remove(doenteDiagnosticoSocial);
        doenteDiagnosticoSocial.setDoente(null);
        return this;
    }

    public void setDoenteDiagnosticoSocials(Set<DoenteDiagnosticoSocial> doenteDiagnosticoSocials) {
        this.doenteDiagnosticoSocials = doenteDiagnosticoSocials;
    }

    public Set<DoenteRegistosIntervencoes> getDoenteRegistosIntervencoes() {
        return doenteRegistosIntervencoes;
    }

    public Doente doenteRegistosIntervencoes(Set<DoenteRegistosIntervencoes> doenteRegistosIntervencoes) {
        this.doenteRegistosIntervencoes = doenteRegistosIntervencoes;
        return this;
    }

    public Doente addDoenteRegistosIntervencoes(DoenteRegistosIntervencoes doenteRegistosIntervencoes) {
        this.doenteRegistosIntervencoes.add(doenteRegistosIntervencoes);
        doenteRegistosIntervencoes.setDoente(this);
        return this;
    }

    public Doente removeDoenteRegistosIntervencoes(DoenteRegistosIntervencoes doenteRegistosIntervencoes) {
        this.doenteRegistosIntervencoes.remove(doenteRegistosIntervencoes);
        doenteRegistosIntervencoes.setDoente(null);
        return this;
    }

    public void setDoenteRegistosIntervencoes(Set<DoenteRegistosIntervencoes> doenteRegistosIntervencoes) {
        this.doenteRegistosIntervencoes = doenteRegistosIntervencoes;
    }

    public Set<DoenteHistMovimentos> getDoenteHistMovimentos() {
        return doenteHistMovimentos;
    }

    public Doente doenteHistMovimentos(Set<DoenteHistMovimentos> doenteHistMovimentos) {
        this.doenteHistMovimentos = doenteHistMovimentos;
        return this;
    }

    public Doente addDoenteHistMovimentos(DoenteHistMovimentos doenteHistMovimentos) {
        this.doenteHistMovimentos.add(doenteHistMovimentos);
        doenteHistMovimentos.setDoente(this);
        return this;
    }

    public Doente removeDoenteHistMovimentos(DoenteHistMovimentos doenteHistMovimentos) {
        this.doenteHistMovimentos.remove(doenteHistMovimentos);
        doenteHistMovimentos.setDoente(null);
        return this;
    }

    public void setDoenteHistMovimentos(Set<DoenteHistMovimentos> doenteHistMovimentos) {
        this.doenteHistMovimentos = doenteHistMovimentos;
    }

    public Set<DoenteContactosOutros> getDoenteContactosOutros() {
        return doenteContactosOutros;
    }

    public Doente doenteContactosOutros(Set<DoenteContactosOutros> doenteContactosOutros) {
        this.doenteContactosOutros = doenteContactosOutros;
        return this;
    }

    public Doente addDoenteContactosOutros(DoenteContactosOutros doenteContactosOutros) {
        this.doenteContactosOutros.add(doenteContactosOutros);
        doenteContactosOutros.setDoente(this);
        return this;
    }

    public Doente removeDoenteContactosOutros(DoenteContactosOutros doenteContactosOutros) {
        this.doenteContactosOutros.remove(doenteContactosOutros);
        doenteContactosOutros.setDoente(null);
        return this;
    }

    public void setDoenteContactosOutros(Set<DoenteContactosOutros> doenteContactosOutros) {
        this.doenteContactosOutros = doenteContactosOutros;
    }

    public DoenteIdentidade getDoenteIdentidade() {
        return doenteIdentidade;
    }

    public Doente doenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidade = doenteIdentidade;
        return this;
    }

    public void setDoenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidade = doenteIdentidade;
    }

    public DoenteContactos getDoenteContactos() {
        return doenteContactos;
    }

    public Doente doenteContactos(DoenteContactos doenteContactos) {
        this.doenteContactos = doenteContactos;
        return this;
    }

    public void setDoenteContactos(DoenteContactos doenteContactos) {
        this.doenteContactos = doenteContactos;
    }

    public DoenteSocioFamiliar getDoenteSocioFamiliar() {
        return doenteSocioFamiliar;
    }

    public Doente doenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliar = doenteSocioFamiliar;
        return this;
    }

    public void setDoenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliar = doenteSocioFamiliar;
    }

    public Turnos getTurnos() {
        return turnos;
    }

    public Doente turnos(Turnos turnos) {
        this.turnos = turnos;
        return this;
    }

    public void setTurnos(Turnos turnos) {
        this.turnos = turnos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doente)) {
            return false;
        }
        return id != null && id.equals(((Doente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Doente{" +
            "id=" + getId() +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
