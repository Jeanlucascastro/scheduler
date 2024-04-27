package com.sociedade.scheduler.services;


import com.sociedade.scheduler.doman.Type;
import com.sociedade.scheduler.doman.dto.CreateTypeDTO;
import com.sociedade.scheduler.doman.dto.UpdateTypeDTO;
import com.sociedade.scheduler.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public Type saveType(CreateTypeDTO typeDTO) {
        Type type = new Type(
         typeDTO.name(),
                Duration.ofMinutes(typeDTO.duration()),
                typeDTO.companyId()
        );
        return this.typeRepository.save(type);
    }

    public Type getTypeById(Long id) {
        return this.typeRepository.findById(id).orElse(null);
    }

    public Page<Type> getAllTypes(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return typeRepository.findAll(pageRequest);
    }

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Type updateType(Long id, UpdateTypeDTO updatedType) {
        Type existingType = this.typeRepository.findById(id).orElse(null);
        if (existingType != null) {
            existingType.setName(updatedType.name());
            existingType.setTime(Duration.ofMinutes(updatedType.duration()));
            return this.typeRepository.save(existingType);
        }
        return null;
    }

    public void deleteType(Long id) {
        this.typeRepository.deleteById(id);
    }
}
