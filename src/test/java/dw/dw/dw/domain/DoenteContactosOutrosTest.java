package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteContactosOutrosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteContactosOutros.class);
        DoenteContactosOutros doenteContactosOutros1 = new DoenteContactosOutros();
        doenteContactosOutros1.setId(1L);
        DoenteContactosOutros doenteContactosOutros2 = new DoenteContactosOutros();
        doenteContactosOutros2.setId(doenteContactosOutros1.getId());
        assertThat(doenteContactosOutros1).isEqualTo(doenteContactosOutros2);
        doenteContactosOutros2.setId(2L);
        assertThat(doenteContactosOutros1).isNotEqualTo(doenteContactosOutros2);
        doenteContactosOutros1.setId(null);
        assertThat(doenteContactosOutros1).isNotEqualTo(doenteContactosOutros2);
    }
}
