package com.ebanking.transaction;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction create(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.accountId(), request.type(), request.amount(), request.description());
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public Transaction update(Long id, TransactionRequest request) {
        Transaction transaction = findById(id);
        transaction.updateFrom(request);
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.delete(findById(id));
    }
}
