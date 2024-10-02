package org.escuelaIng.Controller.propertie;


import org.escuelaIng.Service.propertie.PropertieService;
import org.escuelaIng.repository.propertie.Propertie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/propertie")
public class PropertieController {
    private final PropertieService propertieService;


    public PropertieController (PropertieService propertieService){
        this.propertieService = propertieService;
    }

    @GetMapping()
    public ArrayList<Propertie> getProperties() {return (ArrayList<Propertie>) this.propertieService.all();}

    @PostMapping()
    public Propertie savePropertie(@RequestBody Propertie propertie){return this.propertieService.save(propertie);}

    @GetMapping("/{id}")
    public Optional<Propertie> getPropertyById(@PathVariable Long id) {
        return propertieService.findById(id);
    }

    @PutMapping("/{id}")
    public Propertie updateProperty(@PathVariable Long id, @RequestBody Propertie propertyDetails) {
        return propertieService.update(propertyDetails, id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ArrayList<Propertie> searchProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minSize,
            @RequestParam(required = false) Double maxSize
    ) {
        return propertieService.searchProperties(location, minPrice, maxPrice, minSize, maxSize);
    }
}
