package br.com.devpelinson.spring_boot_essentials.service;

import br.com.devpelinson.spring_boot_essentials.database.model.AlunosEntity;
import br.com.devpelinson.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.devpelinson.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.devpelinson.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.devpelinson.spring_boot_essentials.dto.AvaliacaoFisicaDto;
import br.com.devpelinson.spring_boot_essentials.dto.AvaliacoesFisicasProjection;
import br.com.devpelinson.spring_boot_essentials.exception.BadRequestException;
import br.com.devpelinson.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {

    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void criarAvalicaoFisica(AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDto.getAlunoId())
                .orElseThrow(() -> new NotFoundException("Aluno nao encontrado"));
        AvaliacoesFisicasEntity avaliacaoFisica = aluno.getAvaliacoesFisica();
        if (avaliacaoFisica != null){
            throw new BadRequestException("Avalaicao Fisica ja cadastrada para este Aluno");
        }

        avaliacaoFisica = AvaliacoesFisicasEntity.builder()
                .peso(avaliacaoFisicaDto.getPeso())
                .altura(avaliacaoFisicaDto.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDto.getPorcentagemGorduraCorporal())
                .build();

        aluno.setAvaliacoesFisica(avaliacaoFisica);
        alunosRepository.save(aluno);
    }

    public List<AvaliacoesFisicasProjection> getAllAvaliacoes(){
        return avaliacoesFisicasRepository.getAllAvaliacoes();
    }

    public Page<AvaliacoesFisicasProjection> getAllAvaliacoesPage(Integer page, Integer size){
        return avaliacoesFisicasRepository.getAllAvaliacoesPage(PageRequest.of(page, size));
    }
}
