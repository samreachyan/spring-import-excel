package com.sakcode.consumer.repository;

import com.sakcode.consumer.domain.BillerBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerBillDetailRepository extends JpaRepository<BillerBillDetail, String> {

}
