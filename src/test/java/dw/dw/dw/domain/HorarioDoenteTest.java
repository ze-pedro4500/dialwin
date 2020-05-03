package dw.dw.dw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import dw.dw.dw.web.rest.TestUtil;

public class HorarioDoenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioDoente.class);
        HorarioDoente horarioDoente1 = new HorarioDoente();
        horarioDoente1.setId(1L);
        HorarioDoente horarioDoente2 = new HorarioDoente();
        horarioDoente2.setId(horarioDoente1.getId());
        assertThat(horarioDoente1).isEqualTo(horarioDoente2);
        horarioDoente2.setId(2L);
        assertThat(horarioDoente1).isNotEqualTo(horarioDoente2);
        horarioDoente1.setId(null);
        assertThat(horarioDoente1).isNotEqualTo(horarioDoente2);
    }
}
