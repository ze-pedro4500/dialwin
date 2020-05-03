package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class VitalidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vitalidade.class);
        Vitalidade vitalidade1 = new Vitalidade();
        vitalidade1.setId(1L);
        Vitalidade vitalidade2 = new Vitalidade();
        vitalidade2.setId(vitalidade1.getId());
        assertThat(vitalidade1).isEqualTo(vitalidade2);
        vitalidade2.setId(2L);
        assertThat(vitalidade1).isNotEqualTo(vitalidade2);
        vitalidade1.setId(null);
        assertThat(vitalidade1).isNotEqualTo(vitalidade2);
    }
}
