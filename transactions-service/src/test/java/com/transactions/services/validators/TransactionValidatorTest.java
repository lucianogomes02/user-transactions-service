//package com.transactions.services.validators;
//
//import com.transactions.application.transaction.exceptions.TransactionValidationException;
//import com.transactions.domain.strategies.transaction.SenderAndReceiverAreTheSame;
//import com.transactions.domain.strategies.transaction.TransactionAmountNotValid;
//import com.transactions.domain.strategies.transaction.TransactionIsNotProcessing;
//import com.transactions.domain.value_objects.TransactionStatus;
//import com.transactions.services.validators.transaction.TransactionValidator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import com.transactions.domain.aggregate.transaction.Transaction;
//import com.transactions.domain.specifications.transaction.TransactionSpecification;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@PrepareForTest(TransactionValidator.class)
//public class TransactionValidatorTest {
//    @Mock
//    private TransactionSpecification<Transaction> TransactionAmountIsValid;
//
//    @Mock
//    private TransactionSpecification<Transaction> TransactionIsProcessing;
//
//    @Mock
//    private TransactionSpecification<Transaction> SenderAndReceiverAreNotTheSame;
//
//    @InjectMocks
//    private TransactionValidator transactionValidator;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        List<TransactionSpecification<Transaction>> transactionSpecifications = new ArrayList<>();
//        transactionSpecifications.add(TransactionAmountIsValid);
//        transactionSpecifications.add(TransactionIsProcessing);
//        transactionSpecifications.add(SenderAndReceiverAreNotTheSame);
//        transactionValidator.transactionTransactionSpecifications = transactionSpecifications;
//
//        when(TransactionAmountIsValid.isSatisfiedBy(any(Transaction.class))).thenReturn(true);
//        when(TransactionIsProcessing.isSatisfiedBy(any(Transaction.class))).thenReturn(true);
//        when(SenderAndReceiverAreNotTheSame.isSatisfiedBy(any(Transaction.class))).thenReturn(true);
//    }
//
//    @Test
//    public void testValidateSuccess() {
//        Transaction transaction = new Transaction();
//        transaction.setAmount(100.0);
//        transaction.setSenderId(UUID.randomUUID());
//        transaction.setReceiverId(UUID.randomUUID());
//        transaction.setStatus(TransactionStatus.PROCESSING);
//
//        assertDoesNotThrow(() -> transactionValidator.validate(transaction));
//    }
//
//    @Test
//    public void testTransactionAmountIsNotValid() {
//        Transaction transaction = new Transaction();
//        transaction.setAmount(-100.0);
//        transaction.setSenderId(UUID.randomUUID());
//        transaction.setReceiverId(UUID.randomUUID());
//        transaction.setStatus(TransactionStatus.PROCESSING);
//
//        TransactionValidator spyValidator = spy(new TransactionValidator());
//        List<TransactionSpecification<Transaction>> transactionSpecifications = new ArrayList<>();
//        transactionSpecifications.add(TransactionAmountIsValid);
//        transactionSpecifications.add(TransactionIsProcessing);
//        transactionSpecifications.add(SenderAndReceiverAreNotTheSame);
//        spyValidator.transactionTransactionSpecifications = transactionSpecifications;
//
//        when(spyValidator.transactionTransactionSpecifications.get(0).isSatisfiedBy(transaction)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(TransactionAmountIsValid)).thenReturn(new TransactionAmountNotValid());
//
//        TransactionValidationException thrown = assertThrows(
//            TransactionValidationException.class,
//            () -> spyValidator.validate(transaction)
//        );
//
//        assertEquals("O valor da transação deve ser maior que zero", thrown.getMessage());
//    }
//
//    @Test
//    public void testTransactionIsNotProcessing() {
//        Transaction transaction = new Transaction();
//        transaction.setAmount(100.0);
//        transaction.setSenderId(UUID.randomUUID());
//        transaction.setReceiverId(UUID.randomUUID());
//        transaction.setStatus(TransactionStatus.SUCCEEDED);
//
//        TransactionValidator spyValidator = spy(new TransactionValidator());
//        List<TransactionSpecification<Transaction>> transactionSpecifications = new ArrayList<>();
//        transactionSpecifications.add(TransactionAmountIsValid);
//        transactionSpecifications.add(TransactionIsProcessing);
//        transactionSpecifications.add(SenderAndReceiverAreNotTheSame);
//        spyValidator.transactionTransactionSpecifications = transactionSpecifications;
//
//        when(spyValidator.transactionTransactionSpecifications.get(1).isSatisfiedBy(transaction)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(TransactionIsProcessing)).thenReturn(new TransactionIsNotProcessing());
//
//        TransactionValidationException thrown = assertThrows(
//                TransactionValidationException.class,
//                () -> spyValidator.validate(transaction)
//        );
//
//        assertEquals("Transação já foi concluída ou falhou", thrown.getMessage());
//    }
//
//    @Test
//    public void testSenderAndReceiverAreTheSame() {
//        Transaction transaction = new Transaction();
//        UUID senderId = UUID.randomUUID();
//        transaction.setAmount(100.0);
//        transaction.setSenderId(senderId);
//        transaction.setReceiverId(senderId);
//        transaction.setStatus(TransactionStatus.PROCESSING);
//
//        TransactionValidator spyValidator = spy(new TransactionValidator());
//        List<TransactionSpecification<Transaction>> transactionSpecifications = new ArrayList<>();
//        transactionSpecifications.add(TransactionAmountIsValid);
//        transactionSpecifications.add(TransactionIsProcessing);
//        transactionSpecifications.add(SenderAndReceiverAreNotTheSame);
//        spyValidator.transactionTransactionSpecifications = transactionSpecifications;
//
//        when(spyValidator.transactionTransactionSpecifications.get(2).isSatisfiedBy(transaction)).thenReturn(false);
//        when(spyValidator.getValidationMessageStrategy(SenderAndReceiverAreNotTheSame)).thenReturn(new SenderAndReceiverAreTheSame());
//
//        TransactionValidationException thrown = assertThrows(
//                TransactionValidationException.class,
//                () -> spyValidator.validate(transaction)
//        );
//
//        assertEquals("O remetente e o destinatário não podem ser a mesma pessoa", thrown.getMessage());
//    }
//}
