package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class GrauConfTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrauConf.class);
        GrauConf grauConf1 = new GrauConf();
        grauConf1.setId(1L);
        GrauConf grauConf2 = new GrauConf();
        grauConf2.setId(grauConf1.getId());
        assertThat(grauConf1).isEqualTo(grauConf2);
        grauConf2.setId(2L);
        assertThat(grauConf1).isNotEqualTo(grauConf2);
        grauConf1.setId(null);
        assertThat(grauConf1).isNotEqualTo(grauConf2);
    }
}
