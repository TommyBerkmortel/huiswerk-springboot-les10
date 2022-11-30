package nl.novi.TechItEasy.Repositories;

import nl.novi.TechItEasy.models.Television;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelevisionRepository extends JpaRepository<Television, Integer> {
    Optional <Television> findByBrand(String brand);
    Optional <Television> findByName(String name);
    Boolean existsByName(String name);
}
