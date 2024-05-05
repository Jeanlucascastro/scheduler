package com.sociedade.scheduler.services;


import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.Type;
import com.sociedade.scheduler.model.dto.CreateTypeDTO;
import com.sociedade.scheduler.model.dto.UpdateTypeDTO;
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

    @Autowired
    private CompanyService companyService;

    public Type saveType(CreateTypeDTO typeDTO) {

        Company company = this.companyService.findById(typeDTO.companyId());

        Type type = new Type(
         typeDTO.name(),
                Duration.ofMinutes(typeDTO.duration()),
                company
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
