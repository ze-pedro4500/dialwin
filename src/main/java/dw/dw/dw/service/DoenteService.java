package dw.dw.dw.service;

import dw.dw.dw.domain.Doente;
import dw.dw.dw.domain.DoenteIdentidade;
import dw.dw.dw.domain.SubSistemas;
import dw.dw.dw.domain.Turnos;
import dw.dw.dw.domain.enumeration.Situacao;
import dw.dw.dw.repository.DoenteIdentidadeRepository;
import dw.dw.dw.repository.DoenteRepository;
import dw.dw.dw.web.rest.DoenteIdentidadeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoenteService {

    @Autowired
    private DoenteRepository doenteRepository;

    @Autowired
    private DoenteIdentidadeResource doenteIdentidadeResource;

    @Autowired
    private DoenteIdentidadeRepository doenteIdentidadeRepository;

    @Autowired
    private DoenteIdentidadeService doenteIdentidadeService;

    // to do Turnos e SubSistema
    public List<Doente> findBySitSubTur(Situacao situacao, Long subSistema, Long t) {
        List<Doente> doentes = doenteRepository.findAll();
        List<Doente> doentes1 = null;
        if (situacao == null && subSistema == null && t == null) {
            return doenteRepository.findAll();
        } else if (situacao != null && subSistema == null && t == null) {
            doentes = doenteRepository.findAllBySituacao(situacao);
        }
        else if(situacao == Situacao.StatusDP && subSistema==null && t != null){
            doentes1 = doenteRepository.findAllByTurnosId(t);
            doentes = doentes1
                .stream()
                .filter(doente -> doente.getSituacao().equals(Situacao.StatusDP)).collect(Collectors.toList());
            return doentes;
            }
        else if(situacao == null && subSistema != null && t == null){
            List<DoenteIdentidade> doenteIdentidades = doenteIdentidadeRepository.findAllBySubsistemasId(subSistema);
            List<Doente> doentes2 = new ArrayList<>();
            for (DoenteIdentidade doenteIdentidade: doenteIdentidades
                 ) {
                doentes2.add(doenteIdentidade.getDoente());
            }
            return doentes2;
        }
        else if(situacao != null && subSistema != null && t == null){
            List<DoenteIdentidade> doenteIdentidades = doenteIdentidadeRepository.findAllBySubsistemasId(subSistema);
            List<Doente> doentesSub = new ArrayList<>();
            List<Doente> response = new ArrayList<>();
            for (DoenteIdentidade doenteIdentidade: doenteIdentidades
            ) {
                doentesSub.add(doenteIdentidade.getDoente());
            }
            List<Doente> doentesSit = doenteRepository.findAllBySituacao(situacao);
            for (int i=0; i<doentesSub.size(); i++){
                for(int j=0; j<doentesSit.size(); j++){
                    if(doentesSub.get(i)==doentesSit.get(j)){
                        response.add(doentesSit.get(j));
                    }
                }
            }
            return response;
        }
        else if(situacao == Situacao.StatusDP && subSistema != null && t != null){
            //subSistema
            List<DoenteIdentidade> doenteIdentidades = doenteIdentidadeRepository.findAllBySubsistemasId(subSistema);
            List<Doente> doentesSub = new ArrayList<>();
            for (DoenteIdentidade doenteIdentidade: doenteIdentidades
            ) {
                doentesSub.add(doenteIdentidade.getDoente());
            }
            //turnos
            List<Doente> doentes2 = new ArrayList<>();
            doentes2 = doenteRepository.findAllByTurnosId(t);
            //situacao
            doentes = doentes2
                .stream()
                .filter(doente -> doente.getSituacao().equals(Situacao.StatusDP)).collect(Collectors.toList());
            //cruzamentos dos 3
            List<Doente> response = new ArrayList<>();
            for(int i = 0; i < doentesSub.size(); i++){
                for(int j = 0; j < doentes.size();j++){
                    if(doentesSub.get(i)==doentes.get(j)){
                        response.add(doentesSub.get(i));
                    }
                }
            }
            return response;
        }
        return doentes;
    }
}
