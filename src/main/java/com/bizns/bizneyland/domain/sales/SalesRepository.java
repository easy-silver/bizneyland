package com.bizns.bizneyland.domain.sales;

import com.bizns.bizneyland.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    List<Sales> findByClient(Client client);

    void deleteByClient(Client client);

}
