package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class SubSisGrupoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubSisGrupo.class);
        SubSisGrupo subSisGrupo1 = new SubSisGrupo();
        subSisGrupo1.setId(1L);
        SubSisGrupo subSisGrupo2 = new SubSisGrupo();
        subSisGrupo2.setId(subSisGrupo1.getId());
        assertThat(subSisGrupo1).isEqualTo(subSisGrupo2);
        subSisGrupo2.setId(2L);
        assertThat(subSisGrupo1).isNotEqualTo(subSisGrupo2);
        subSisGrupo1.setId(null);
        assertThat(subSisGrupo1).isNotEqualTo(subSisGrupo2);
    }
}
