package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteHistMovimentosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteHistMovimentos.class);
        DoenteHistMovimentos doenteHistMovimentos1 = new DoenteHistMovimentos();
        doenteHistMovimentos1.setId(1L);
        DoenteHistMovimentos doenteHistMovimentos2 = new DoenteHistMovimentos();
        doenteHistMovimentos2.setId(doenteHistMovimentos1.getId());
        assertThat(doenteHistMovimentos1).isEqualTo(doenteHistMovimentos2);
        doenteHistMovimentos2.setId(2L);
        assertThat(doenteHistMovimentos1).isNotEqualTo(doenteHistMovimentos2);
        doenteHistMovimentos1.setId(null);
        assertThat(doenteHistMovimentos1).isNotEqualTo(doenteHistMovimentos2);
    }
}
