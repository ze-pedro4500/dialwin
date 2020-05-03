package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class HospRefTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospRef.class);
        HospRef hospRef1 = new HospRef();
        hospRef1.setId(1L);
        HospRef hospRef2 = new HospRef();
        hospRef2.setId(hospRef1.getId());
        assertThat(hospRef1).isEqualTo(hospRef2);
        hospRef2.setId(2L);
        assertThat(hospRef1).isNotEqualTo(hospRef2);
        hospRef1.setId(null);
        assertThat(hospRef1).isNotEqualTo(hospRef2);
    }
}
