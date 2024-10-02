package org.escuelaIng.Service.propertie;

import org.escuelaIng.repository.propertie.Propertie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PropertieService {
    Propertie save(Propertie propertie);
    Optional<Propertie> findById(long propertyID);
    List<Propertie> all();
    Propertie deleteById(long propertyID);
    Propertie update(Propertie propertie, long propertyID);
    public ArrayList<Propertie> searchProperties(String location, Double minPrice, Double maxPrice, Double minSize, Double maxSize);

}
