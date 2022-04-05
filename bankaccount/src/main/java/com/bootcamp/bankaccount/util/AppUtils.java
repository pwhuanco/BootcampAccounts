package com.bootcamp.bankaccount.util;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.bean.debitcard.DebitCard;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.debitcard.DebitCardDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static AccountDto entityToDto(Account account) {
        AccountDto accDto = new AccountDto();
        BeanUtils.copyProperties(account, accDto);
        return accDto;
    }
    public static DebitCardDto entityDebitToDto(DebitCard debit) {
        DebitCardDto cardDto = new DebitCardDto();
        BeanUtils.copyProperties(debit, cardDto);
        return cardDto;
    }

    public static Account dtoToEntity(AccountDto accDto) {
        Account account = new Account();
        BeanUtils.copyProperties(accDto, account);
        return account;
    }

    public static DebitCard dtoToEntityDebit(DebitCardDto accDto) {
        DebitCard account = new DebitCard();
        BeanUtils.copyProperties(accDto, account);
        return account;
    }
}
