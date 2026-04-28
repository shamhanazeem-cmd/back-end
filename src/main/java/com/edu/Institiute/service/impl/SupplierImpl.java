package com.edu.Institiute.service.impl;

import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.SupplierAddressDTO;
import com.edu.Institiute.dto.SupplierBankAccountDTO;
import com.edu.Institiute.dto.SupplierDTO;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.SupplierResponseDTO;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierDTO;
import com.edu.Institiute.entity.*;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.*;
import com.edu.Institiute.service.SupplierService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierImpl implements SupplierService {

    @Autowired
    private Generator generator;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private SupplierAddressRepo addressRepo;

    @Autowired
    private SupplierAddressMapper addressMapper;

    @Autowired
    private SupplierBankAccountRepo accountRepo;

    @Autowired
    private SupplierBankAccountMapper accountMapper;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public CommonResponseDto saveSupplier(RequestRegistryDto dto) {
        try {
            int supplierId =  generator.generateFourNumNumbers();
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : "SYSTEM";

            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());
            if (status.isEmpty()) {
                return new CommonResponseDto(400, "Invalid status ID", null, null);
            }

            Optional<Payment> payment = paymentRepo.findById(dto.getPayment());
            if (payment.isEmpty()) {
                return new CommonResponseDto(400, "Invalid payment ID", null, null);
            }

            SupplierDTO supplierDto = new SupplierDTO();
                    supplierDto.setSupplierId(supplierId);
                    supplierDto.setSupplierCode(dto.getSupplierCode());
                    supplierDto.setSupplierName(dto.getSupplierName());
                    supplierDto.setSupplierType(dto.getSupplierType());
                    supplierDto.setCurrencyCode(dto.getCurrencyCode());
                    supplierDto.setTaxNumber(dto.getTaxNumber());
                    supplierDto.setContactName(dto.getContactName());
                    supplierDto.setPhone(dto.getPhone());
                    supplierDto.setCreatedBy(createdBy);
                    supplierDto.setCreatedDate(new Date());
                    supplierDto.setStatus(statusMapper.toStatusDto(status.get()));
                    supplierDto.setPaymentTermsId(paymentMapper.toPaymentDto(payment.get()));

            Supplier SupplierEntity = supplierMapper.dtoToEntity(supplierDto);
            Supplier savedSupplier = supplierRepo.save(SupplierEntity);

            if (dto.getAddresses() != null && !dto.getAddress().isEmpty()) {
                for (SupplierAddressDTO addressDTO : dto.getAddresses()) {
                    addressDTO.setSupplier(addressMapper.toSupplierAddressDto(savedSupplier));
                    SupplierAddress addressEntity = addressMapper.dtoToEntity(addressDTO);
                    addressRepo.save(addressEntity);
                }
            }

            if (dto.getBankAccounts() != null && !dto.getBankAccounts().isEmpty()) {
                for (SupplierBankAccountDTO bankAccountDTO : dto.getBankAccounts()) {
                    bankAccountDTO.setSupplier(accountMapper.toSupplierAccountDto(savedSupplier));
                    SupplierBankAccount accountEntity = accountMapper.dtoToEntity(bankAccountDTO);
                    accountRepo.save(accountEntity);
                }
            }

            return new CommonResponseDto(201, "Supplier saved!", supplierDto.getSupplierName(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Save because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto updateSupplier(RequestRegistryDto dto, String supplierId) {
        try {
            Integer id = Integer.parseInt(supplierId);
            Supplier supplier = supplierRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("Supplier not found"));

            supplier.setSupplierCode(dto.getSupplierCode());
            supplier.setSupplierName(dto.getSupplierName());
            supplier.setSupplierType(dto.getSupplierType());
            supplier.setContactName(dto.getContactName());
            supplier.setCurrencyCode(dto.getCurrencyCode());
            supplier.setTaxNumber(dto.getTaxNumber());
            supplier.setPhone(dto.getPhone());

            Optional<Status> status = statusRepo.findStatusById(dto.getStatus());
            status.ifPresent(supplier::setStatus);

            Optional<Payment> payment = paymentRepo.findById(dto.getPayment());
            payment.ifPresent(supplier::setPaymentTermsId);

            List<SupplierAddress> existingAddresses = supplier.getAddresses();
            if (existingAddresses == null) {
                existingAddresses = new ArrayList<>();
                supplier.setAddresses(existingAddresses);
            } else {
                existingAddresses.clear(); // JPA orphans will be removed if orphanRemoval=true
            }

            if (dto.getAddresses() != null) {
                for (SupplierAddressDTO addrDto : dto.getAddresses()) {
                    SupplierAddress addr = new SupplierAddress();

                    addr.setAddressId(dto.getId());
                    addr.setAddressType(dto.getAddressType());
                    addr.setCity(dto.getCity());
                    addr.setCountryCode(dto.getCountryCode());
                    addr.setState(dto.getState());
                    addr.setPostalCode(dto.getPostalCode());
                    addr.setStreetLine1(dto.getStreetLine1());
                    addr.setStreetLine2(dto.getStreetLine2());

                    addr.setSupplier(supplier);
                    existingAddresses.add(addr);
                }
            }

            // 4. Update Bank Accounts (Clear & Re-populate)
            List<SupplierBankAccount> existingBanks = supplier.getBankAccounts();
            if (existingBanks == null) {
                existingBanks = new ArrayList<>();
                supplier.setBankAccounts(existingBanks);
            } else {
                existingBanks.clear();
            }

            if (dto.getBankAccounts() != null) {
                for (SupplierBankAccountDTO bankDto : dto.getBankAccounts()) {
                    SupplierBankAccount bank = new SupplierBankAccount();
                    bank.setBankAccountId(bankDto.getBankAccountId());
                    bank.setAccountName(bankDto.getAccountName());
                    bank.setAccountNumber(bankDto.getAccountNumber());
                    bank.setBankName(bankDto.getBankName());
                    bank.setIban(bankDto.getIban());
                    bank.setCurrencyCode(bankDto.getCurrencyCode());
                    bank.setSwiftCode(bankDto.getSwiftCode());

                    bank.setSupplier(supplier);
                    existingBanks.add(bank);
                }
            }


            supplierRepo.save(supplier);
            return new CommonResponseDto(201, "Supplier Updated!", supplier.getSupplierName(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removeSupplier(String supplierId) {
        Optional<Supplier> supplier = supplierRepo.getSupplierById(supplierId);

        if (supplier.isPresent()) {
            supplierRepo.delete(supplier.get());
            return new CommonResponseDto(201, "supplier was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any supplier...!");
        }
    }

    @Override
    public PaginatedResponseSupplierDTO SupplierById(String supplierId) {
        try {
            List<Supplier> allSuppliersForProvidedId = supplierRepo.getSupplierDetailById(supplierId);
            List<SupplierResponseDTO> list = new ArrayList<>();

            for (Supplier r :allSuppliersForProvidedId) {

                List<SupplierAddressDTO> addressDtos = new ArrayList<>();
                if (r.getAddresses() != null) {
                    for (SupplierAddress addr : r.getAddresses()) {
                        addressDtos.add(new SupplierAddressDTO(
                                addr.getAddressId(),
                                null,
                                addr.getAddressType(),
                                addr.getStreetLine1(),
                                addr.getStreetLine2(),
                                addr.getCity(),
                                addr.getState(),
                                addr.getPostalCode(),
                                addr.getCountryCode(),
                                addr.getIsPrimary()
                        ));
                    }
                }


                List<SupplierBankAccountDTO> bankDtos = new ArrayList<>();
                if (r.getBankAccounts() != null) {
                    for (SupplierBankAccount bank : r.getBankAccounts()) {
                        bankDtos.add(new SupplierBankAccountDTO(
                                bank.getBankAccountId(),
                                null,
                                bank.getBankName(),
                                bank.getAccountNumber(),
                                bank.getAccountName(),
                                bank.getIban(),
                                bank.getSwiftCode(),
                                bank.getCurrencyCode(),
                                bank.getIsDefault()
                        ));
                    }
                }

                list.add(
                        new SupplierResponseDTO(
                                r.getSupplierId(),
                                r.getSupplierCode(),
                                r.getSupplierName(),
                                r.getSupplierType(),
                                statusMapper.toStatusDto(r.getStatus()),
                                r.getCurrencyCode(),
                                paymentMapper.toPaymentDto(r.getPaymentTermsId()),
                                r.getTaxNumber(),
                                r.getCreatedDate(),
                                r.getCreatedBy(),
                                r.getContactName(),
                                r.getPhone(),
                                addressDtos,
                                bankDtos

                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierDTO(
                    supplierRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseSupplierDTO AllSuppliers() {
        try {
            List<Supplier> allSuppliersForProvidedId = supplierRepo.findAll();
            List<SupplierResponseDTO> list = new ArrayList<>();

            for (Supplier r :allSuppliersForProvidedId) {

                List<SupplierAddressDTO> addressDtos = new ArrayList<>();
                if (r.getAddresses() != null) {
                    for (SupplierAddress addr : r.getAddresses()) {
                        addressDtos.add(new SupplierAddressDTO(
                                addr.getAddressId(),
                                null,
                                addr.getAddressType(),
                                addr.getStreetLine1(),
                                addr.getStreetLine2(),
                                addr.getCity(),
                                addr.getState(),
                                addr.getPostalCode(),
                                addr.getCountryCode(),
                                addr.getIsPrimary()
                        ));
                    }
                }


                List<SupplierBankAccountDTO> bankDtos = new ArrayList<>();
                if (r.getBankAccounts() != null) {
                    for (SupplierBankAccount bank : r.getBankAccounts()) {
                        bankDtos.add(new SupplierBankAccountDTO(
                                bank.getBankAccountId(),
                                null,
                                bank.getBankName(),
                                bank.getAccountNumber(),
                                bank.getAccountName(),
                                bank.getIban(),
                                bank.getSwiftCode(),
                                bank.getCurrencyCode(),
                                bank.getIsDefault()
                        ));
                    }
                }

                list.add(
                        new SupplierResponseDTO(
                                r.getSupplierId(),
                                r.getSupplierCode(),
                                r.getSupplierName(),
                                r.getSupplierType(),
                                statusMapper.toStatusDto(r.getStatus()),
                                r.getCurrencyCode(),
                                paymentMapper.toPaymentDto(r.getPaymentTermsId()),
                                r.getTaxNumber(),
                                r.getCreatedDate(),
                                r.getCreatedBy(),
                                r.getContactName(),
                                r.getPhone(),
                                addressDtos,
                                bankDtos



                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierDTO(
                    supplierRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }

    }

    @Override
    public PaginatedResponseSupplierDTO getAllPagedSuppliers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Supplier> supplierPage = supplierRepo.findAll(pageable);

            List<SupplierResponseDTO> dtos = supplierPage.getContent()
                    .stream()
                    .map( supplier -> {
                        List<SupplierAddressDTO> addressDtos = supplier.getAddresses().stream()
                                .map(addr -> new SupplierAddressDTO(
                                        addr.getAddressId(),
                                        null,
                                        addr.getAddressType(),
                                        addr.getStreetLine1(),
                                        addr.getStreetLine2(),
                                        addr.getCity(),
                                        addr.getState(),
                                        addr.getPostalCode(),
                                        addr.getCountryCode(),
                                        addr.getIsPrimary()
                                ))
                                .collect(Collectors.toList());

                        List<SupplierBankAccountDTO> bankDtos = supplier.getBankAccounts().stream()
                                .map(bank -> new SupplierBankAccountDTO(
                                        bank.getBankAccountId(),
                                        null,
                                        bank.getBankName(),
                                        bank.getAccountNumber(),
                                        bank.getAccountName(),
                                        bank.getIban(),
                                        bank.getSwiftCode(),
                                        bank.getCurrencyCode(),
                                        bank.getIsDefault()
                                ))
                                .collect(Collectors.toList());

                       return new SupplierResponseDTO(
                                supplier.getSupplierId(),
                                supplier.getSupplierCode(),
                                supplier.getSupplierName(),
                                supplier.getSupplierType(),
                                statusMapper.toStatusDto(supplier.getStatus()),
                                supplier.getCurrencyCode(),
                                paymentMapper.toPaymentDto(supplier.getPaymentTermsId()),
                                supplier.getTaxNumber(),
                                supplier.getCreatedDate(),
                                supplier.getCreatedBy(),
                                supplier.getContactName(),
                                supplier.getPhone(),
                                addressDtos,
                                bankDtos
                        );
                    })
                    .collect(Collectors.toList());

            return new PaginatedResponseSupplierDTO(
                    supplierPage.getNumberOfElements(),
                    dtos,
                    supplierPage.getTotalPages(),
                    supplierPage.getTotalElements(),
                    supplierPage.getNumber(),
                    supplierPage.getSize(),
                    supplierPage.hasNext(),
                    supplierPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }
}