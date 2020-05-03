package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class DoenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doente.class);
        Doente doente1 = new Doente();
        doente1.setId(1L);
        Doente doente2 = new Doente();
        doente2.setId(doente1.getId());
        assertThat(doente1).isEqualTo(doente2);
        doente2.setId(2L);
        assertThat(doente1).isNotEqualTo(doente2);
        doente1.setId(null);
        assertThat(doente1).isNotEqualTo(doente2);
    }
}
