package br.com.devpelinson.spring_boot_essentials.service;

import br.com.devpelinson.spring_boot_essentials.database.model.AlunosEntity;
import br.com.devpelinson.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.devpelinson.spring_boot_essentials.database.model.TreinosEntity;
import br.com.devpelinson.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.devpelinson.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.devpelinson.spring_boot_essentials.database.repository.ITreinosRepository;
import br.com.devpelinson.spring_boot_essentials.dto.AlunoDto;
import br.com.devpelinson.spring_boot_essentials.exception.BadRequestException;
import br.com.devpelinson.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunosService {

    private final IAlunosRepository alunosRepository;
    private final ITreinosRepository treinosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void criarAluno(AlunoDto alunoDTO) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(alunoDTO.getEmail())
                .orElse(null);

        if (aluno != null){
            throw new BadRequestException("Aluno ja cadastrado com este email");
        }

        alunosRepository.save(AlunosEntity.builder()
                        .nome(alunoDTO.getNome())
                        .email(alunoDTO.getEmail())
                .build());
    }

    public List<AlunosEntity> findAll(){
        return alunosRepository.findAll();
    }

    public AvaliacoesFisicasEntity getAlunoAvaliacao(Integer alunoId) throws NotFoundException{
        AlunosEntity aluno = alunosRepository.findByIdFetch(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno nao encontrado"));
        AvaliacoesFisicasEntity avaliacao = aluno.getAvaliacoesFisica();
        if(avaliacao == null){
            throw new NotFoundException("Avaliacao fisica nao encontrada");
        }

        return avaliacao;
    }

    @Transactional
    public void deletarAluno(Integer alunoId) throws NotFoundException{
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno nao encontrado"));
        //1. deletar treinos do aluno
        List<Integer> treinosAlunoIds = aluno.getTreinos().stream()
                .map(TreinosEntity::getId)
                        .toList();
        treinosRepository.deleteAllById(treinosAlunoIds);
        //2. deletar o aluno
        alunosRepository.deleteById(alunoId);

        //3. deletar avaliacao fisica
        avaliacoesFisicasRepository.deleteById(aluno.getAvaliacoesFisica().getId());
    }
}
