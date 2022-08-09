package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.HawkerCentre;
import com.example.demo.models.HawkerCentreDTO;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface LocationRepository extends JpaRepository<HawkerCentre, Integer> {

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *" +
            " cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude))))";

    @Query("SELECT new com.example.demo.models.HawkerCentreDTO (name, photoUrl) FROM HawkerCentre s ORDER BY "
            + HAVERSINE_FORMULA)
    public List<HawkerCentreDTO> findNearestCentres(@Param("latitude") double latitude,
            @Param("longitude") double longitude);
}
