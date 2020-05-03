package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class ACESTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ACES.class);
        ACES aCES1 = new ACES();
        aCES1.setId(1L);
        ACES aCES2 = new ACES();
        aCES2.setId(aCES1.getId());
        assertThat(aCES1).isEqualTo(aCES2);
        aCES2.setId(2L);
        assertThat(aCES1).isNotEqualTo(aCES2);
        aCES1.setId(null);
        assertThat(aCES1).isNotEqualTo(aCES2);
    }
}
