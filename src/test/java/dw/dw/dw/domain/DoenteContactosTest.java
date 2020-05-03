package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteContactosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteContactos.class);
        DoenteContactos doenteContactos1 = new DoenteContactos();
        doenteContactos1.setId(1L);
        DoenteContactos doenteContactos2 = new DoenteContactos();
        doenteContactos2.setId(doenteContactos1.getId());
        assertThat(doenteContactos1).isEqualTo(doenteContactos2);
        doenteContactos2.setId(2L);
        assertThat(doenteContactos1).isNotEqualTo(doenteContactos2);
        doenteContactos1.setId(null);
        assertThat(doenteContactos1).isNotEqualTo(doenteContactos2);
    }
}
