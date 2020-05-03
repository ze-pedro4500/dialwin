package dw.dw.dw.service;

import dw.dw.dw.domain.DoenteContactos;
import dw.dw.dw.domain.DoenteContactosOutros;
import dw.dw.dw.repository.DoenteContactosOutrosRepository;
import dw.dw.dw.repository.DoenteContactosRepository;
import dw.dw.dw.repository.DoenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.event.HierarchyBoundsListener;
import java.util.List;

@Service
@Transactional
public class DoenteContactosService {

    @Autowired
    private DoenteContactosRepository doenteContactosRepository;

    @Autowired
    private DoenteContactosOutrosRepository doenteContactosOutrosRepository;

    public List<DoenteContactos> getDoenteContactos(Long doente){
        List<DoenteContactos> doenteContactos = doenteContactosRepository.findAllByDoenteId(doente);
        return doenteContactos;
    }

    public List<DoenteContactosOutros> getDoenteContactosOutros(Long doente){
        List<DoenteContactosOutros> doenteContactosOutros = doenteContactosOutrosRepository.findAllByDoenteId(doente);
        return doenteContactosOutros;
    }

}
