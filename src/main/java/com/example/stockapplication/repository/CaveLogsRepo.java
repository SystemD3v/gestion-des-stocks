package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CaveLogsRepo extends JpaRepository<CaveLogs, Long> {

    /**
     * Get most recent X logs ordered by log_id descending
     * @param limit number of logs to retrieve
     * @return list of recent logs
     */
    @Query(value = "SELECT * FROM cave_logs ORDER BY log_id DESC LIMIT :limit", nativeQuery = true)
    List<CaveLogs> findRecentLogs(@Param("limit") int limit);

    /**
     * Insert a new log entry
     * @param operation operation type
     * @param description log description
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cave_logs (operation, description) VALUES (:operation, :description)", nativeQuery = true)
    void insertLog(@Param("operation") String operation, @Param("description") String description);
}