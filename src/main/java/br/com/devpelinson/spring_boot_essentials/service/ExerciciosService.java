package br.com.devpelinson.spring_boot_essentials.service;

import br.com.devpelinson.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.devpelinson.spring_boot_essentials.database.repository.IExerciciosRepository;
import br.com.devpelinson.spring_boot_essentials.dto.ExercicioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciciosService {

    private final IExerciciosRepository exerciciosRepository;

    public List<ExerciciosEntity> findAll(){
        return exerciciosRepository.findAll();
    }

    public void save(ExercicioDto exercicioDto){
        exerciciosRepository.save(ExerciciosEntity.builder()
                        .nome(exercicioDto.getNome())
                        .grupoMuscular(exercicioDto.getGrupoMuscular())
                .build());
    }

    public List<ExerciciosEntity> getExerciciosByGrupoMuscular(String grupoMuscular){
        return exerciciosRepository.findAllByGrupoMuscular(grupoMuscular);
    }
}
