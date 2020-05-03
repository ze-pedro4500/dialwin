package dw.dw.dw.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A HorarioDoente.
 */
@Entity
@Table(name = "horario_doente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HorarioDoente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia")
    private String dia;

    @Column(name = "turno")
    private String turno;

    @Column(name = "sala")
    private Integer sala;

    @Column(name = "posto")
    private String posto;

    @Column(name = "duracao")
    private Integer duracao;

    @ManyToOne
    @JsonIgnoreProperties("horarioDoentes")
    private Doente doente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public HorarioDoente dia(String dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTurno() {
        return turno;
    }

    public HorarioDoente turno(String turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getSala() {
        return sala;
    }

    public HorarioDoente sala(Integer sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public String getPosto() {
        return posto;
    }

    public HorarioDoente posto(String posto) {
        this.posto = posto;
        return this;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public HorarioDoente duracao(Integer duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Doente getDoente() {
        return doente;
    }

    public HorarioDoente doente(Doente doente) {
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
        if (!(o instanceof HorarioDoente)) {
            return false;
        }
        return id != null && id.equals(((HorarioDoente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HorarioDoente{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", turno='" + getTurno() + "'" +
            ", sala=" + getSala() +
            ", posto='" + getPosto() + "'" +
            ", duracao=" + getDuracao() +
            "}";
    }
}
