package com.muni.app.repositories;

import com.muni.app.models.MunicipalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity, Long> {
    @Query(nativeQuery = true, value = "select * from municipalities where upper(name) =?1 and upper(province) =?2")
    MunicipalityEntity findByNameAndProvince(String municipality, String province);
}
