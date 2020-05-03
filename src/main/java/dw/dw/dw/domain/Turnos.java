package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Turnos.
 */
@Entity
@Table(name = "turnos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turnos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "turnos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Doente> doentes = new HashSet<>();

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

    public Turnos nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Doente> getDoentes() {
        return doentes;
    }

    public Turnos doentes(Set<Doente> doentes) {
        this.doentes = doentes;
        return this;
    }

    public Turnos addDoente(Doente doente) {
        this.doentes.add(doente);
        doente.setTurnos(this);
        return this;
    }

    public Turnos removeDoente(Doente doente) {
        this.doentes.remove(doente);
        doente.setTurnos(null);
        return this;
    }

    public void setDoentes(Set<Doente> doentes) {
        this.doentes = doentes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Turnos)) {
            return false;
        }
        return id != null && id.equals(((Turnos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Turnos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
