package com.sociedade.scheduler.services;


import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.Animal;
import com.sociedade.scheduler.model.dto.CreateAnimalDTO;
import com.sociedade.scheduler.model.dto.UpdateAnimalDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository AnimalRepository;

    @Autowired
    private CompanyService companyService;

    public Animal saveAnimal(CreateAnimalDTO animalDTO, User user) {

        Company company = this.companyService.findById(animalDTO.companyId());

        Animal Animal = new Animal(
            animalDTO.name(),
                animalDTO.age(),
                animalDTO.weight(),
                animalDTO.size(),
                animalDTO.animalType(),
                animalDTO.note(),
                user,
                company
        );

        return this.AnimalRepository.save(Animal);
    }

    public Animal getAnimalById(Long id) {
        return this.AnimalRepository.findById(id).orElse(null);
    }

    public Page<Animal> getAllAnimals(User user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return AnimalRepository.findByUser(user, pageRequest);
    }

    public List<Animal> getAllAnimals(User user) {
        return AnimalRepository.findByUser(user);
    }

    public Animal updateAnimal(Long id, UpdateAnimalDTO updatedAnimal) {
        Animal existingAnimal = this.AnimalRepository.findById(id).orElse(null);
        if (existingAnimal != null) {
            existingAnimal.setName(updatedAnimal.name());
            existingAnimal.setAge(updatedAnimal.age());
            existingAnimal.setAnimalType(updatedAnimal.animalType());
            existingAnimal.setWeight(updatedAnimal.weight());
            existingAnimal.setSize(updatedAnimal.size());
            existingAnimal.setNote(updatedAnimal.note());
            return this.AnimalRepository.save(existingAnimal);
        }
        return null;
    }

    public void deleteAnimal(Long id) {
        this.AnimalRepository.deleteById(id);
    }
}
