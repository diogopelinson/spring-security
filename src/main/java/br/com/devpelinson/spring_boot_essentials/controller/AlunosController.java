package br.com.devpelinson.spring_boot_essentials.controller;

import br.com.devpelinson.spring_boot_essentials.database.model.AlunosEntity;
import br.com.devpelinson.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.devpelinson.spring_boot_essentials.dto.AlunoDto;
import br.com.devpelinson.spring_boot_essentials.exception.BadRequestException;
import br.com.devpelinson.spring_boot_essentials.exception.NotFoundException;
import br.com.devpelinson.spring_boot_essentials.service.AlunosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Validated
public class AlunosController {

    private final AlunosService alunosService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAluno(@Valid @RequestBody AlunoDto alunoDTO) throws BadRequestException {
        alunosService.criarAluno(alunoDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlunosEntity> findAll(){
        return alunosService.findAll();
    }

    @PreAuthorize("#alunoId == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/{alunoId}/avaliacao")
    @ResponseStatus(HttpStatus.OK)
    public AvaliacoesFisicasEntity getAvaliacaoFisica(@PathVariable Integer alunoId) throws NotFoundException {
        return alunosService.getAlunoAvaliacao(alunoId);
    }

    @DeleteMapping("/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerAluno(@PathVariable Integer alunoId) throws NotFoundException{
        alunosService.deletarAluno(alunoId);
    }
}
