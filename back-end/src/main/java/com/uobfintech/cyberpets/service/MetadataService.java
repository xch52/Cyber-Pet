package com.uobfintech.cyberpets.service;

import java.util.List;

import com.uobfintech.cyberpets.entity.Pet;

public interface MetadataService {

    Pet findPetById(Long id);


    List<Pet> findAllPets();

    //Pet findById(Long id);
}
