package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ACES.
 */
@Entity
@Table(name = "aces")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ACES implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "aCES")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteIdentidade> doenteIdentidades = new HashSet<>();

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

    public ACES nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<DoenteIdentidade> getDoenteIdentidades() {
        return doenteIdentidades;
    }

    public ACES doenteIdentidades(Set<DoenteIdentidade> doenteIdentidades) {
        this.doenteIdentidades = doenteIdentidades;
        return this;
    }

    public ACES addDoenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidades.add(doenteIdentidade);
        doenteIdentidade.setACES(this);
        return this;
    }

    public ACES removeDoenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidades.remove(doenteIdentidade);
        doenteIdentidade.setACES(null);
        return this;
    }

    public void setDoenteIdentidades(Set<DoenteIdentidade> doenteIdentidades) {
        this.doenteIdentidades = doenteIdentidades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ACES)) {
            return false;
        }
        return id != null && id.equals(((ACES) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ACES{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
