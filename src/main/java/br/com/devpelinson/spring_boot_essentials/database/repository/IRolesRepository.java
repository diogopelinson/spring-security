package br.com.devpelinson.spring_boot_essentials.database.repository;

import br.com.devpelinson.spring_boot_essentials.database.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolesRepository extends JpaRepository<RolesEntity, Integer> {
}
