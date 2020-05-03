package dw.dw.dw.service;

import dw.dw.dw.domain.DoenteContactos;
import dw.dw.dw.domain.DoenteHistMovimentos;
import dw.dw.dw.repository.DoenteContactosRepository;
import dw.dw.dw.repository.DoenteHistMovimentosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoenteHistoricoMovimentosService {

    @Autowired
    private DoenteHistMovimentosRepository doenteHistMovimentosRepository;

    public List<DoenteHistMovimentos> getDoenteHistMov(Long doente){
        if(doente==null){
            return doenteHistMovimentosRepository.findAll();
        }
        List<DoenteHistMovimentos> doenteHistMov = doenteHistMovimentosRepository.findAllByDoenteId(doente);
        return doenteHistMov;
    }

}
