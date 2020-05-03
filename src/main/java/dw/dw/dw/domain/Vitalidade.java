package dw.dw.dw.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Vitalidade.
 */
@Entity
@Table(name = "vitalidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vitalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "percentagem")
    private Integer percentagem;

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

    public Vitalidade nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPercentagem() {
        return percentagem;
    }

    public Vitalidade percentagem(Integer percentagem) {
        this.percentagem = percentagem;
        return this;
    }

    public void setPercentagem(Integer percentagem) {
        this.percentagem = percentagem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vitalidade)) {
            return false;
        }
        return id != null && id.equals(((Vitalidade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vitalidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", percentagem=" + getPercentagem() +
            "}";
    }
}
