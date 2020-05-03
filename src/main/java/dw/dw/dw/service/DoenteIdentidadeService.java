package dw.dw.dw.service;

import dw.dw.dw.domain.Doente;
import dw.dw.dw.domain.DoenteIdentidade;
import dw.dw.dw.repository.DoenteIdentidadeRepository;
import dw.dw.dw.repository.DoenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoenteIdentidadeService {

    @Autowired
    private DoenteIdentidadeRepository doenteIdentidadeRepository;

    public DoenteIdentidade findByDoente(Long doente, Integer utente) {
        if(doente != null) {
            List<DoenteIdentidade> doenteIdentidades;
            doenteIdentidades = doenteIdentidadeRepository.findAllByDoenteId(doente);
            DoenteIdentidade doenteIdentidade = doenteIdentidades.get(0);
            return doenteIdentidade;
        }
        if(utente != null){
            List<DoenteIdentidade> doenteIdentidades;
            doenteIdentidades = doenteIdentidadeRepository.findAllBynUtente(utente);
            DoenteIdentidade doenteIdentidade = doenteIdentidades.get(0);
            return doenteIdentidade;
        }
        return null;
    }
}
