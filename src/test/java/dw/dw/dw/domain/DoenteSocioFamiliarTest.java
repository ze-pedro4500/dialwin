package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteSocioFamiliarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteSocioFamiliar.class);
        DoenteSocioFamiliar doenteSocioFamiliar1 = new DoenteSocioFamiliar();
        doenteSocioFamiliar1.setId(1L);
        DoenteSocioFamiliar doenteSocioFamiliar2 = new DoenteSocioFamiliar();
        doenteSocioFamiliar2.setId(doenteSocioFamiliar1.getId());
        assertThat(doenteSocioFamiliar1).isEqualTo(doenteSocioFamiliar2);
        doenteSocioFamiliar2.setId(2L);
        assertThat(doenteSocioFamiliar1).isNotEqualTo(doenteSocioFamiliar2);
        doenteSocioFamiliar1.setId(null);
        assertThat(doenteSocioFamiliar1).isNotEqualTo(doenteSocioFamiliar2);
    }
}
