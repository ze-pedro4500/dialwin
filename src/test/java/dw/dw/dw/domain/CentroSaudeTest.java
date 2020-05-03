package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class CentroSaudeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroSaude.class);
        CentroSaude centroSaude1 = new CentroSaude();
        centroSaude1.setId(1L);
        CentroSaude centroSaude2 = new CentroSaude();
        centroSaude2.setId(centroSaude1.getId());
        assertThat(centroSaude1).isEqualTo(centroSaude2);
        centroSaude2.setId(2L);
        assertThat(centroSaude1).isNotEqualTo(centroSaude2);
        centroSaude1.setId(null);
        assertThat(centroSaude1).isNotEqualTo(centroSaude2);
    }
}
