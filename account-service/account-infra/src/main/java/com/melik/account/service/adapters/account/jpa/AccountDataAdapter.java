package com.melik.account.service.adapters.account.jpa;

import com.melik.account.service.account.entity.Account;
import com.melik.account.service.account.port.AccountPort;
import com.melik.account.service.account.usecase.AccountRetrieve;
import com.melik.account.service.account.usecase.AccountRetrieveAll;
import com.melik.account.service.account.usecase.AccountSave;
import com.melik.account.service.adapters.account.jpa.entity.AccountEntity;
import com.melik.account.service.adapters.account.jpa.repository.AccountRepository;
import com.melik.account.service.common.exception.AccountDomainBusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.melik.account.service.common.util.Constants.DEFAULT_PAGE;
import static com.melik.account.service.common.util.Constants.DEFAULT_SIZE;


/**
 * @Author mselvi
 * @Created 03.01.2024
 */

@Service
@RequiredArgsConstructor
public class AccountDataAdapter implements AccountPort {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> retrieveAll(AccountRetrieveAll accountRetrieveAll) {
        PageRequest pageRequest = getPageRequest(accountRetrieveAll);
        Page<AccountEntity> allAccountEntity = accountRepository.findAll(pageRequest);
        return allAccountEntity.toList().stream().map(AccountEntity::toModel).toList();
    }

    @Override
    public Account retrieve(AccountRetrieve accountRetrieve) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(accountRetrieve.getAccountId());
        return accountEntity
                .map(AccountEntity::toModel)
                .orElseThrow(() -> new AccountDomainBusinessException("Account Not Found!"));
    }

    @Override
    public Account persist(AccountSave accountSave) {
        var accountEntity = new AccountEntity();
        accountEntity.setCustomerId(accountSave.getCustomerId());
        accountEntity.setCurrentBalance(accountSave.getCurrentBalance().getAmount());
        accountEntity.setCurrencyType(accountSave.getCurrencyType());
        accountEntity.setAccountType(accountSave.getAccountType());
        accountEntity.setIbanNo(createRandomIbanNo());
        return accountRepository.save(accountEntity).toModel();
    }

    @Override
    public void update(Account account) {
        var accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity);
        accountEntity.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(accountEntity);
    }

    private PageRequest getPageRequest(AccountRetrieveAll accountRetrieveAll) {
        return PageRequest.of(accountRetrieveAll.getPageOptional().orElse(DEFAULT_PAGE), accountRetrieveAll.getSizeOptional().orElse(DEFAULT_SIZE));
    }

    private String createRandomIbanNo() {
        return RandomStringUtils.randomNumeric(26);
    }
}
