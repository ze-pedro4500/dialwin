package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteDiagnosticoSocialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteDiagnosticoSocial.class);
        DoenteDiagnosticoSocial doenteDiagnosticoSocial1 = new DoenteDiagnosticoSocial();
        doenteDiagnosticoSocial1.setId(1L);
        DoenteDiagnosticoSocial doenteDiagnosticoSocial2 = new DoenteDiagnosticoSocial();
        doenteDiagnosticoSocial2.setId(doenteDiagnosticoSocial1.getId());
        assertThat(doenteDiagnosticoSocial1).isEqualTo(doenteDiagnosticoSocial2);
        doenteDiagnosticoSocial2.setId(2L);
        assertThat(doenteDiagnosticoSocial1).isNotEqualTo(doenteDiagnosticoSocial2);
        doenteDiagnosticoSocial1.setId(null);
        assertThat(doenteDiagnosticoSocial1).isNotEqualTo(doenteDiagnosticoSocial2);
    }
}
