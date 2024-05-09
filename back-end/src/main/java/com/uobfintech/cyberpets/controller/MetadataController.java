package com.uobfintech.cyberpets.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.uobfintech.cyberpets.entity.Pet;
import com.uobfintech.cyberpets.result.Result;
import com.uobfintech.cyberpets.service.MetadataService;

@Slf4j
@RestController
@RequestMapping("/api/pets")

public class MetadataController {
    @Autowired
    private MetadataService metadataService;

    @GetMapping("all")
    public Result getPets(@RequestParam(required = false) String filter,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) Integer limit) {
        System.out.println("get Pets....");
        List<Pet> pets = metadataService.findAllPets();
        if (filter != null){

        }
        if (sort != null){

        }
        if (limit != null){
            List<Pet> petsLimit = pets.subList(0, Math.min(pets.size(), limit));
            return Result.success(petsLimit);
        }
        return Result.success(pets);
    }



    @GetMapping("{id}")
    public Result getPetById(@PathVariable Long id) {
        return Result.success(metadataService.findPetById(id));
    }


//    @GetMapping("/test/{id}")
//    public Result test(@PathVariable Long id){
//        return Result.success(metadataService.findById(id));
//    }
}
