package org.escuelaIng.Service.propertie;


import org.escuelaIng.repository.propertie.Propertie;
import org.escuelaIng.repository.propertie.PropertieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertieServiceSQL implements PropertieService {
    private final PropertieRepository propertieRepository;


    @Autowired
    public PropertieServiceSQL (PropertieRepository propertieRepository){
        this.propertieRepository = propertieRepository;
    }


    @Override
    public Propertie save(Propertie propertie) {
        return propertieRepository.save(propertie);
    }

    @Override
    public Optional<Propertie> findById(long propertyID) {
        return propertieRepository.findById(propertyID);
    }

    @Override
    public List<Propertie> all() {

        return propertieRepository.findAll();
    }

    @Override
    public Propertie deleteById(long propertyID) {
        Optional<Propertie> propertie = propertieRepository.findById(propertyID);

        if (propertie.isPresent()){
            propertieRepository.deleteById(propertyID);
        }else{
            throw new RuntimeException("Propertie with ID "+ propertyID + " Not found");
        }
        return null;
    }

    @Override
    public Propertie update(Propertie propertie, long propertyID) {
        return  propertieRepository.findById(propertyID)
                .map(existingPropertie ->{
                    existingPropertie.setAddress(propertie.getAddress());
                    existingPropertie.setPrice(propertie.getPrice());
                    existingPropertie.setSize(propertie.getSize());
                    existingPropertie.setDescription(propertie.getDescription());
                    return propertieRepository.save(existingPropertie);
                }).orElse(null);
    }


    @Override
    public ArrayList<Propertie> searchProperties(String location, Double minPrice, Double maxPrice, Double minSize,
            Double maxSize) {
                return propertieRepository.searchProperties(location, minPrice, maxPrice, minSize, maxSize);
    }
}
