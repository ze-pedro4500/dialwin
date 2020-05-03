package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SitProf.
 */
@Entity
@Table(name = "sit_prof")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SitProf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "sitProf")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DoenteSocioFamiliar> doenteSocioFamiliars = new HashSet<>();

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

    public SitProf nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<DoenteSocioFamiliar> getDoenteSocioFamiliars() {
        return doenteSocioFamiliars;
    }

    public SitProf doenteSocioFamiliars(Set<DoenteSocioFamiliar> doenteSocioFamiliars) {
        this.doenteSocioFamiliars = doenteSocioFamiliars;
        return this;
    }

    public SitProf addDoenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliars.add(doenteSocioFamiliar);
        doenteSocioFamiliar.setSitProf(this);
        return this;
    }

    public SitProf removeDoenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliars.remove(doenteSocioFamiliar);
        doenteSocioFamiliar.setSitProf(null);
        return this;
    }

    public void setDoenteSocioFamiliars(Set<DoenteSocioFamiliar> doenteSocioFamiliars) {
        this.doenteSocioFamiliars = doenteSocioFamiliars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SitProf)) {
            return false;
        }
        return id != null && id.equals(((SitProf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SitProf{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
