package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CentroSaude.
 */
@Entity
@Table(name = "centro_saude")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentroSaude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "centroSaude")
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

    public CentroSaude nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<DoenteIdentidade> getDoenteIdentidades() {
        return doenteIdentidades;
    }

    public CentroSaude doenteIdentidades(Set<DoenteIdentidade> doenteIdentidades) {
        this.doenteIdentidades = doenteIdentidades;
        return this;
    }

    public CentroSaude addDoenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidades.add(doenteIdentidade);
        doenteIdentidade.setCentroSaude(this);
        return this;
    }

    public CentroSaude removeDoenteIdentidade(DoenteIdentidade doenteIdentidade) {
        this.doenteIdentidades.remove(doenteIdentidade);
        doenteIdentidade.setCentroSaude(null);
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
        if (!(o instanceof CentroSaude)) {
            return false;
        }
        return id != null && id.equals(((CentroSaude) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CentroSaude{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
