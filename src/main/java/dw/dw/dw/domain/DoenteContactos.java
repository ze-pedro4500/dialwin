package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DoenteContactos.
 */
@Entity
@Table(name = "doente_contactos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DoenteContactos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transportador")
    private String transportador;

    @Column(name = "telef_transp")
    private Integer telefTransp;

    @OneToOne
    @JoinColumn(unique = true)
    private Doente doente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportador() {
        return transportador;
    }

    public DoenteContactos transportador(String transportador) {
        this.transportador = transportador;
        return this;
    }

    public void setTransportador(String transportador) {
        this.transportador = transportador;
    }

    public Integer getTelefTransp() {
        return telefTransp;
    }

    public DoenteContactos telefTransp(Integer telefTransp) {
        this.telefTransp = telefTransp;
        return this;
    }

    public void setTelefTransp(Integer telefTransp) {
        this.telefTransp = telefTransp;
    }

    public Doente getDoente() {
        return doente;
    }

    public DoenteContactos doente(Doente doente) {
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
        if (!(o instanceof DoenteContactos)) {
            return false;
        }
        return id != null && id.equals(((DoenteContactos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoenteContactos{" +
            "id=" + getId() +
            ", transportador='" + getTransportador() + "'" +
            ", telefTransp=" + getTelefTransp() +
            "}";
    }
}
