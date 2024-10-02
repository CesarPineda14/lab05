package org.escuelaIng.repository.propertie;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PropertieRepository  extends JpaRepository<Propertie, Long> {

        @Query("SELECT p FROM Propertie p WHERE " +
            "(:location IS NULL OR  p.address LIKE %:address%) OR " +
            "(:minPrice IS NULL OR p.price >= :minPrice) OR " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) OR " +
            "(:minSize IS NULL OR p.size >= :minSize) OR " +
            "(:maxSize IS NULL OR p.size <= :maxSize)")
    ArrayList<Propertie> searchProperties(
            @Param("location") String address,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minSize") Double minSize,
            @Param("maxSize") Double maxSize
    );
}
