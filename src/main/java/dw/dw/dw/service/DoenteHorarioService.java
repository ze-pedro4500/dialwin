package dw.dw.dw.service;

import dw.dw.dw.domain.DoenteHistMovimentos;
import dw.dw.dw.domain.HorarioDoente;
import dw.dw.dw.repository.DoenteHistMovimentosRepository;
import dw.dw.dw.repository.HorarioDoenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoenteHorarioService {

    @Autowired
    private HorarioDoenteRepository horarioDoenteRepository;

    public List<HorarioDoente> getDoenteHor(Long doente){
        if(doente==null){
            return horarioDoenteRepository.findAll();
        }
        List<HorarioDoente> doenteHor = horarioDoenteRepository.findAllByDoenteId(doente);
        return doenteHor;
    }

}
