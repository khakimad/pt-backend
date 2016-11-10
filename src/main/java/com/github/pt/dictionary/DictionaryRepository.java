package com.github.pt.dictionary;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DictionaryRepository extends JpaRepository<DictionaryData, Long> {
    String EXERCISE_NAME = "exercise_name";
    String EXERCISE_CATEGORY_NAME = "exercise_category_name";

    @Query("select d from DictionaryData d where d.dlanguage = 'en' and d.dname = ?1 and d.dkey = ?2 "
            + "and d.fromdate <= ?3 and (d.todate is null or d.todate >= ?3)")
    @Cacheable(value = "dictionaryData", key = "{'en', #p0, #p1}")
    List<DictionaryData> findDictionaryValue(
            String dname, String dkey, LocalDateTime currentdate);

    @Query("select d from DictionaryData d where d.dlanguage = ?1 and d.dname = ?2 and d.dkey = ?3 "
            + "and d.fromdate <= ?4 and (d.todate is null or d.todate >= ?4)")
    @Cacheable(value = "dictionaryData", key = "{#p0, #p1, #p2}")
    List<DictionaryData> findDictionaryValue(
            String dlanguage, String dname, String dkey, LocalDateTime currentdate);
}
