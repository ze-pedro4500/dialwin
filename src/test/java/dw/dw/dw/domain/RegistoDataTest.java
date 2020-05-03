package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class RegistoDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistoData.class);
        RegistoData registoData1 = new RegistoData();
        registoData1.setId(1L);
        RegistoData registoData2 = new RegistoData();
        registoData2.setId(registoData1.getId());
        assertThat(registoData1).isEqualTo(registoData2);
        registoData2.setId(2L);
        assertThat(registoData1).isNotEqualTo(registoData2);
        registoData1.setId(null);
        assertThat(registoData1).isNotEqualTo(registoData2);
    }
}
