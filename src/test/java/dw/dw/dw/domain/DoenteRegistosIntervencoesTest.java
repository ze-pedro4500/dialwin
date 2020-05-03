package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteRegistosIntervencoesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoenteRegistosIntervencoes.class);
        DoenteRegistosIntervencoes doenteRegistosIntervencoes1 = new DoenteRegistosIntervencoes();
        doenteRegistosIntervencoes1.setId(1L);
        DoenteRegistosIntervencoes doenteRegistosIntervencoes2 = new DoenteRegistosIntervencoes();
        doenteRegistosIntervencoes2.setId(doenteRegistosIntervencoes1.getId());
        assertThat(doenteRegistosIntervencoes1).isEqualTo(doenteRegistosIntervencoes2);
        doenteRegistosIntervencoes2.setId(2L);
        assertThat(doenteRegistosIntervencoes1).isNotEqualTo(doenteRegistosIntervencoes2);
        doenteRegistosIntervencoes1.setId(null);
        assertThat(doenteRegistosIntervencoes1).isNotEqualTo(doenteRegistosIntervencoes2);
    }
}
