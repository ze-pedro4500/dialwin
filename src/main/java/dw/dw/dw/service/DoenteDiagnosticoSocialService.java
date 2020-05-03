package dw.dw.dw.service;

import dw.dw.dw.domain.DoenteDiagnosticoSocial;
import dw.dw.dw.domain.DoenteIdentidade;
import dw.dw.dw.repository.DoenteContactosRepository;
import dw.dw.dw.repository.DoenteDiagnosticoSocialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoenteDiagnosticoSocialService {

    @Autowired
    private DoenteDiagnosticoSocialRepository doenteDiagnosticoSocialRepository;

    public DoenteDiagnosticoSocial findByDoente(Long doente) {
        List<DoenteDiagnosticoSocial> doenteDiagnosticoSocials;
        doenteDiagnosticoSocials = doenteDiagnosticoSocialRepository.findAllByDoenteId(doente);
        DoenteDiagnosticoSocial doenteDiagnostico = doenteDiagnosticoSocials.get(0);
        return doenteDiagnostico;
    }
}
