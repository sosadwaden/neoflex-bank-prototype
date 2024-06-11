package com.sosadwaden.deal.service.impl;

import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.repository.ClientRepository;
import com.sosadwaden.deal.service.ClientMapper;
import com.sosadwaden.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public DealServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public void createClient(LoanStatementRequestDto request) {
        Client client = clientMapper.mapToClient(request);
    }
}
