package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import dw.dw.dw.domain.enumeration.Habilitacoes;

import dw.dw.dw.domain.enumeration.EstCivil;

/**
 * A DoenteSocioFamiliar.
 */
@Entity
@Table(name = "doente_socio_familiar")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoenteSocioFamiliar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "habilitacoes")
    private Habilitacoes habilitacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "est_civil")
    private EstCivil estCivil;

    @Column(name = "conjuge_nome")
    private String conjugeNome;

    @Column(name = "conjuge_prof")
    private String conjugeProf;

    @Column(name = "activ_temp_liv")
    private String activTempLiv;

    @Column(name = "habitacao_obs")
    private String habitacaoObs;

    @Column(name = "grau_conforto_obs")
    private String grauConfortoObs;

    @Column(name = "resposta_social")
    private String respostaSocial;

    @OneToOne
    @JoinColumn(unique = true)
    private Doente doente;

    @ManyToOne
    @JsonIgnoreProperties("doenteSocioFamiliars")
    private Vitalidade vitalidade;

    @ManyToOne
    @JsonIgnoreProperties("doenteSocioFamiliars")
    private Habit habit;

    @ManyToOne
    @JsonIgnoreProperties("doenteSocioFamiliars")
    private GrauConf grauConf;

    @ManyToOne
    @JsonIgnoreProperties("doenteSocioFamiliars")
    private Profissao profissao;

    @ManyToOne
    @JsonIgnoreProperties("doenteSocioFamiliars")
    private SitProf sitProf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habilitacoes getHabilitacoes() {
        return habilitacoes;
    }

    public DoenteSocioFamiliar habilitacoes(Habilitacoes habilitacoes) {
        this.habilitacoes = habilitacoes;
        return this;
    }

    public void setHabilitacoes(Habilitacoes habilitacoes) {
        this.habilitacoes = habilitacoes;
    }

    public EstCivil getEstCivil() {
        return estCivil;
    }

    public DoenteSocioFamiliar estCivil(EstCivil estCivil) {
        this.estCivil = estCivil;
        return this;
    }

    public void setEstCivil(EstCivil estCivil) {
        this.estCivil = estCivil;
    }

    public String getConjugeNome() {
        return conjugeNome;
    }

    public DoenteSocioFamiliar conjugeNome(String conjugeNome) {
        this.conjugeNome = conjugeNome;
        return this;
    }

    public void setConjugeNome(String conjugeNome) {
        this.conjugeNome = conjugeNome;
    }

    public String getConjugeProf() {
        return conjugeProf;
    }

    public DoenteSocioFamiliar conjugeProf(String conjugeProf) {
        this.conjugeProf = conjugeProf;
        return this;
    }

    public void setConjugeProf(String conjugeProf) {
        this.conjugeProf = conjugeProf;
    }

    public String getActivTempLiv() {
        return activTempLiv;
    }

    public DoenteSocioFamiliar activTempLiv(String activTempLiv) {
        this.activTempLiv = activTempLiv;
        return this;
    }

    public void setActivTempLiv(String activTempLiv) {
        this.activTempLiv = activTempLiv;
    }

    public String getHabitacaoObs() {
        return habitacaoObs;
    }

    public DoenteSocioFamiliar habitacaoObs(String habitacaoObs) {
        this.habitacaoObs = habitacaoObs;
        return this;
    }

    public void setHabitacaoObs(String habitacaoObs) {
        this.habitacaoObs = habitacaoObs;
    }

    public String getGrauConfortoObs() {
        return grauConfortoObs;
    }

    public DoenteSocioFamiliar grauConfortoObs(String grauConfortoObs) {
        this.grauConfortoObs = grauConfortoObs;
        return this;
    }

    public void setGrauConfortoObs(String grauConfortoObs) {
        this.grauConfortoObs = grauConfortoObs;
    }

    public String getRespostaSocial() {
        return respostaSocial;
    }

    public DoenteSocioFamiliar respostaSocial(String respostaSocial) {
        this.respostaSocial = respostaSocial;
        return this;
    }

    public void setRespostaSocial(String respostaSocial) {
        this.respostaSocial = respostaSocial;
    }

    public Doente getDoente() {
        return doente;
    }

    public DoenteSocioFamiliar doente(Doente doente) {
        this.doente = doente;
        return this;
    }

    public void setDoente(Doente doente) {
        this.doente = doente;
    }

    public Vitalidade getVitalidade() {
        return vitalidade;
    }

    public DoenteSocioFamiliar vitalidade(Vitalidade vitalidade) {
        this.vitalidade = vitalidade;
        return this;
    }

    public void setVitalidade(Vitalidade vitalidade) {
        this.vitalidade = vitalidade;
    }

    public Habit getHabit() {
        return habit;
    }

    public DoenteSocioFamiliar habit(Habit habit) {
        this.habit = habit;
        return this;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public GrauConf getGrauConf() {
        return grauConf;
    }

    public DoenteSocioFamiliar grauConf(GrauConf grauConf) {
        this.grauConf = grauConf;
        return this;
    }

    public void setGrauConf(GrauConf grauConf) {
        this.grauConf = grauConf;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public DoenteSocioFamiliar profissao(Profissao profissao) {
        this.profissao = profissao;
        return this;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public SitProf getSitProf() {
        return sitProf;
    }

    public DoenteSocioFamiliar sitProf(SitProf sitProf) {
        this.sitProf = sitProf;
        return this;
    }

    public void setSitProf(SitProf sitProf) {
        this.sitProf = sitProf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoenteSocioFamiliar)) {
            return false;
        }
        return id != null && id.equals(((DoenteSocioFamiliar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoenteSocioFamiliar{" +
            "id=" + getId() +
            ", habilitacoes='" + getHabilitacoes() + "'" +
            ", estCivil='" + getEstCivil() + "'" +
            ", conjugeNome='" + getConjugeNome() + "'" +
            ", conjugeProf='" + getConjugeProf() + "'" +
            ", activTempLiv='" + getActivTempLiv() + "'" +
            ", habitacaoObs='" + getHabitacaoObs() + "'" +
            ", grauConfortoObs='" + getGrauConfortoObs() + "'" +
            ", respostaSocial='" + getRespostaSocial() + "'" +
            "}";
    }
}
