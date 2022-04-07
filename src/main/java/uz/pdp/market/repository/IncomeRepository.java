package uz.pdp.market.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.market.entity.market.Income;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long>, AbstractRepository {

    Optional<Income> findByIdAndDeletedFalse(Long id);

    List<Income> findAllByDeletedFalse();

    @Modifying
    @Transactional
    @Query(value = "select create_at from market.income where created_at",
    nativeQuery = true)
    List<Income> getDaily(
            @Param(value = "time")LocalDate date
            );
}
