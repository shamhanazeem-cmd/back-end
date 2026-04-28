package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.SupplierAddressDTO;
import com.edu.Institiute.dto.SupplierBankAccountDTO;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.SupplierAddressResponseDTO;
import com.edu.Institiute.dto.responseDto.SupplierBankAccountResponseDTO;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierAddressDTO;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierBankAccountDTO;
import com.edu.Institiute.entity.Supplier;
import com.edu.Institiute.entity.SupplierAddress;
import com.edu.Institiute.entity.SupplierBankAccount;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.SupplierBankAccountRepo;
import com.edu.Institiute.repo.SupplierRepo;
import com.edu.Institiute.service.SupplierBankAccountService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.SupplierBankAccountMapper;
import com.edu.Institiute.utill.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierBankAccountImpl implements SupplierBankAccountService {

    @Autowired
    private Generator generator;

    @Autowired
    private SupplierBankAccountRepo accountRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierBankAccountMapper accountMapper;

    @Override
    public CommonResponseDto saveBankAccount(RequestRegistryDto dto) {
        try {
            int bankAccountId = generator.generateFourNumNumbers();
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : "SYSTEM";

            Optional<Supplier> supplier = supplierRepo.findById(dto.getSupplier());
            if (supplier.isEmpty()) {
                return new CommonResponseDto(400, "Invalid supplier ID", null, null);
            }

            SupplierBankAccountDTO supplierBankAccountDTO = new SupplierBankAccountDTO(
                    bankAccountId,
                    supplierMapper.toSupplierDto(supplier.get()),
                    dto.getBankName(),
                    dto.getAccountNumber(),
                    dto.getAccountName(),
                    dto.getIban(),
                    dto.getSwiftCode(),
                    dto.getCurrencyCode(),
                    dto.getIsDefault()
            );

            accountRepo.save(accountMapper.dtoToEntity(supplierBankAccountDTO));

            return new CommonResponseDto(201, "Supplier saved!", supplierBankAccountDTO.getAccountName(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Save because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto updateBankAccount(RequestRegistryDto dto, String accountId) {
        try {
            Integer id = Integer.parseInt(accountId);

            SupplierBankAccount bankAccount = accountRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("Supplier not found"));

            bankAccount.setBankAccountId(dto.getId());
            bankAccount.setAccountName(dto.getAccountName());
            bankAccount.setAccountNumber(dto.getAccountNumber());
            bankAccount.setBankName(dto.getBankName());
            bankAccount.setIban(dto.getIban());
            bankAccount.setCurrencyCode(dto.getCurrencyCode());
            bankAccount.setSwiftCode(dto.getSwiftCode());

            Optional<Supplier> supplier = supplierRepo.findById(dto.getSupplier());
            supplier.ifPresent(bankAccount::setSupplier);

            accountRepo.save(bankAccount);
            return new CommonResponseDto(201, "Supplier Account Updated!", bankAccount.getAccountName(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removeBankAccount(String accountId) {
        Optional<SupplierBankAccount> bankAccount = accountRepo.getSupplierAccountById(accountId);

        if (bankAccount.isPresent()) {
            accountRepo.delete(bankAccount.get());
            return new CommonResponseDto(201, "Supplier Bank Account was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any Address...!");
        }
    }

    @Override
    public PaginatedResponseSupplierBankAccountDTO getBankAccountById(String accountId) {
        try {
            List<SupplierBankAccount> allAccountsForProvidedId = accountRepo.getSupplierBankAccountDetailById(accountId);
            List<SupplierBankAccountResponseDTO> list = new ArrayList<>();

            for (SupplierBankAccount r :allAccountsForProvidedId) {
                list.add(
                        new SupplierBankAccountResponseDTO(
                                r.getBankAccountId(),
                                supplierMapper.toSupplierDto(r.getSupplier()),
                                r.getAccountName(),
                                r.getAccountNumber(),
                                r.getCurrencyCode(),
                                r.getIban(),
                                r.getSwiftCode(),
                                r.getBankName(),
                                r.getIsDefault()

                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierBankAccountDTO(
                    accountRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseSupplierBankAccountDTO allBankAccounts() {
        try {
            List<SupplierBankAccount> allAccountsForProvidedId = accountRepo.findAll();
            List<SupplierBankAccountResponseDTO> list = new ArrayList<>();


            for (SupplierBankAccount r :allAccountsForProvidedId) {
                list.add(
                        new SupplierBankAccountResponseDTO(
                                r.getBankAccountId(),
                                supplierMapper.toSupplierDto(r.getSupplier()),
                                r.getAccountName(),
                                r.getAccountNumber(),
                                r.getCurrencyCode(),
                                r.getIban(),
                                r.getSwiftCode(),
                                r.getBankName(),
                                r.getIsDefault()

                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierBankAccountDTO(
                    accountRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }

    }

    @Override
    public PaginatedResponseSupplierBankAccountDTO getAllPagedBankAccounts(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SupplierBankAccount> AccountPage = accountRepo.findAll(pageable);

            List<SupplierBankAccountResponseDTO> dtos = AccountPage.getContent()
                    .stream()
                    .map( r -> new SupplierBankAccountResponseDTO(
                            r.getBankAccountId(),
                            supplierMapper.toSupplierDto(r.getSupplier()),
                            r.getAccountName(),
                            r.getAccountNumber(),
                            r.getCurrencyCode(),
                            r.getIban(),
                            r.getSwiftCode(),
                            r.getBankName(),
                            r.getIsDefault()
                            )
                    )
                    .collect(Collectors.toList());

            return new PaginatedResponseSupplierBankAccountDTO(
                    AccountPage.getNumberOfElements(),
                    dtos,
                    AccountPage.getTotalPages(),
                    AccountPage.getTotalElements(),
                    AccountPage.getNumber(),
                    AccountPage.getSize(),
                    AccountPage.hasNext(),
                    AccountPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }



}
