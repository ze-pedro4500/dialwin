package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class SubSistemasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubSistemas.class);
        SubSistemas subSistemas1 = new SubSistemas();
        subSistemas1.setId(1L);
        SubSistemas subSistemas2 = new SubSistemas();
        subSistemas2.setId(subSistemas1.getId());
        assertThat(subSistemas1).isEqualTo(subSistemas2);
        subSistemas2.setId(2L);
        assertThat(subSistemas1).isNotEqualTo(subSistemas2);
        subSistemas1.setId(null);
        assertThat(subSistemas1).isNotEqualTo(subSistemas2);
    }
}
