package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteIdentidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteIdentidade.class);
        DoenteIdentidade doenteIdentidade1 = new DoenteIdentidade();
        doenteIdentidade1.setId(1L);
        DoenteIdentidade doenteIdentidade2 = new DoenteIdentidade();
        doenteIdentidade2.setId(doenteIdentidade1.getId());
        assertThat(doenteIdentidade1).isEqualTo(doenteIdentidade2);
        doenteIdentidade2.setId(2L);
        assertThat(doenteIdentidade1).isNotEqualTo(doenteIdentidade2);
        doenteIdentidade1.setId(null);
        assertThat(doenteIdentidade1).isNotEqualTo(doenteIdentidade2);
    }
}
