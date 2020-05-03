package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class SitProfTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SitProf.class);
        SitProf sitProf1 = new SitProf();
        sitProf1.setId(1L);
        SitProf sitProf2 = new SitProf();
        sitProf2.setId(sitProf1.getId());
        assertThat(sitProf1).isEqualTo(sitProf2);
        sitProf2.setId(2L);
        assertThat(sitProf1).isNotEqualTo(sitProf2);
        sitProf1.setId(null);
        assertThat(sitProf1).isNotEqualTo(sitProf2);
    }
}
