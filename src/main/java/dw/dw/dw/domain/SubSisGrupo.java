package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SubSisGrupo.
 */
@Entity
@Table(name = "sub_sis_grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubSisGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gid_designa")
    private String gidDesigna;

    @Column(name = "gid_grupo")
    private String gidGrupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGidDesigna() {
        return gidDesigna;
    }

    public SubSisGrupo gidDesigna(String gidDesigna) {
        this.gidDesigna = gidDesigna;
        return this;
    }

    public void setGidDesigna(String gidDesigna) {
        this.gidDesigna = gidDesigna;
    }

    public String getGidGrupo() {
        return gidGrupo;
    }

    public SubSisGrupo gidGrupo(String gidGrupo) {
        this.gidGrupo = gidGrupo;
        return this;
    }

    public void setGidGrupo(String gidGrupo) {
        this.gidGrupo = gidGrupo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubSisGrupo)) {
            return false;
        }
        return id != null && id.equals(((SubSisGrupo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubSisGrupo{" +
            "id=" + getId() +
            ", gidDesigna='" + getGidDesigna() + "'" +
            ", gidGrupo='" + getGidGrupo() + "'" +
            "}";
    }
}
