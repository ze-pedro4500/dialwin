package dw.dw.dw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A RegistoData.
 */
@Entity
@Table(name = "registo_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistoData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registo")
    private Long registo;

    @Column(name = "data")
    private LocalDate data;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegisto() {
        return registo;
    }

    public RegistoData registo(Long registo) {
        this.registo = registo;
        return this;
    }

    public void setRegisto(Long registo) {
        this.registo = registo;
    }

    public LocalDate getData() {
        return data;
    }

    public RegistoData data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistoData)) {
            return false;
        }
        return id != null && id.equals(((RegistoData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegistoData{" +
            "id=" + getId() +
            ", registo=" + getRegisto() +
            ", data='" + getData() + "'" +
            "}";
    }
}
