package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GrauConf.
 */
@Entity
@Table(name = "grau_conf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrauConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "grauConf")
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

    public GrauConf nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<DoenteSocioFamiliar> getDoenteSocioFamiliars() {
        return doenteSocioFamiliars;
    }

    public GrauConf doenteSocioFamiliars(Set<DoenteSocioFamiliar> doenteSocioFamiliars) {
        this.doenteSocioFamiliars = doenteSocioFamiliars;
        return this;
    }

    public GrauConf addDoenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliars.add(doenteSocioFamiliar);
        doenteSocioFamiliar.setGrauConf(this);
        return this;
    }

    public GrauConf removeDoenteSocioFamiliar(DoenteSocioFamiliar doenteSocioFamiliar) {
        this.doenteSocioFamiliars.remove(doenteSocioFamiliar);
        doenteSocioFamiliar.setGrauConf(null);
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
        if (!(o instanceof GrauConf)) {
            return false;
        }
        return id != null && id.equals(((GrauConf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GrauConf{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
