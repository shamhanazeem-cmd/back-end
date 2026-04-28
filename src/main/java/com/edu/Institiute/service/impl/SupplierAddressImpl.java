package com.edu.Institiute.service.impl;
import com.edu.Institiute.config.SecurityUtil;
import com.edu.Institiute.dto.SupplierAddressDTO;
import com.edu.Institiute.dto.requestDto.RequestRegistryDto;
import com.edu.Institiute.dto.responseDto.CommonResponseDto;
import com.edu.Institiute.dto.responseDto.SupplierAddressResponseDTO;
import com.edu.Institiute.dto.responseDto.paginated.PaginatedResponseSupplierAddressDTO;
import com.edu.Institiute.entity.Supplier;
import com.edu.Institiute.entity.SupplierAddress;
import com.edu.Institiute.exception.EntryNotFoundException;
import com.edu.Institiute.repo.SupplierAddressRepo;
import com.edu.Institiute.repo.SupplierRepo;
import com.edu.Institiute.service.SupplierAddressService;
import com.edu.Institiute.utill.Generator;
import com.edu.Institiute.utill.mapper.SupplierAddressMapper;
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
public class SupplierAddressImpl implements SupplierAddressService {
    @Autowired
    private Generator generator;

    @Autowired
    private SupplierAddressRepo addressRepo;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private SupplierAddressMapper addressMapper;

    @Override
    public CommonResponseDto saveAddress(RequestRegistryDto dto) {
        try {
            int adressId = generator.generateFourNumNumbers();
            String loggedUser = SecurityUtil.getLoggedUser();
            String createdBy = (loggedUser != null) ? loggedUser : "SYSTEM";

            Optional<Supplier> supplier = supplierRepo.findById(dto.getSupplier());
            if (supplier.isEmpty()) {
                return new CommonResponseDto(400, "Invalid supplier ID", null, null);
            }

            SupplierAddressDTO supplierAddressDTO = new SupplierAddressDTO(
                    adressId,
                    supplierMapper.toSupplierDto(supplier.get()),
                    dto.getAddressType(),
                    dto.getStreetLine1(),
                    dto.getStreetLine2(),
                    dto.getCity(),
                    dto.getState(),
                    dto.getPostalCode(),
                    dto.getCurrencyCode(),
                    dto.getIsPrimary()
            );

            addressRepo.save(addressMapper.dtoToEntity(supplierAddressDTO));

            return new CommonResponseDto(201, "Supplier saved!", supplierAddressDTO.getAddressId(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Save because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto updateAddress(RequestRegistryDto dto, String addressId) {
        try {
            Integer id = Integer.parseInt(addressId);

            SupplierAddress address = addressRepo.findById(id)
                    .orElseThrow(() -> new EntryNotFoundException("Supplier not found"));

            address.setAddressId(dto.getId());
            address.setAddressType(dto.getAddressType());
            address.setCity(dto.getCity());
            address.setCountryCode(dto.getCountryCode());
            address.setState(dto.getState());
            address.setPostalCode(dto.getPostalCode());
            address.setStreetLine1(dto.getStreetLine1());
            address.setStreetLine2(dto.getStreetLine2());

            Optional<Supplier> supplier = supplierRepo.findById(dto.getSupplier());
            supplier.ifPresent(address::setSupplier);

            addressRepo.save(address);
            return new CommonResponseDto(201, "Supplier Updated!", address.getAddressId(), new ArrayList<>());
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't Update because of this Error --> " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDto removeAddress(String addressId) {
        Optional<SupplierAddress> address = addressRepo.getSupplierAddressById(addressId);

        if (address.isPresent()) {
            addressRepo.delete(address.get());
            return new CommonResponseDto(201, "Supplier Address was deleted!", true, new ArrayList<>());
        } else {
            throw new EntryNotFoundException("Can't find any Address...!");
        }
    }

    @Override
    public PaginatedResponseSupplierAddressDTO getAddressById(String supplierId) {
        try {
            List<SupplierAddress> allAddressForProvidedId = addressRepo.getSupplierAddressDetailById(supplierId);
            List<SupplierAddressResponseDTO> list = new ArrayList<>();

            for (SupplierAddress r :allAddressForProvidedId) {
                list.add(
                        new SupplierAddressResponseDTO(
                                r.getAddressId(),
                                supplierMapper.toSupplierDto(r.getSupplier()),
                                r.getAddressType(),
                                r.getStreetLine1(),
                                r.getStreetLine2(),
                                r.getCity(),
                                r.getState(),
                                r.getPostalCode(),
                                r.getCountryCode(),
                                r.getIsPrimary()

                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierAddressDTO(
                    addressRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }
    }

    @Override
    public PaginatedResponseSupplierAddressDTO getAllAddresses() {
        try {
            List<SupplierAddress> allAddressForProvidedId = addressRepo.findAll();
            List<SupplierAddressResponseDTO> list = new ArrayList<>();

            for (SupplierAddress r :allAddressForProvidedId) {
                list.add(
                        new SupplierAddressResponseDTO(
                                r.getAddressId(),
                                supplierMapper.toSupplierDto(r.getSupplier()),
                                r.getAddressType(),
                                r.getStreetLine1(),
                                r.getStreetLine2(),
                                r.getCity(),
                                r.getState(),
                                r.getPostalCode(),
                                r.getCountryCode(),
                                r.getIsPrimary()

                        )
                );
            }
            System.out.println(list);
            return new PaginatedResponseSupplierAddressDTO(
                    addressRepo.count(),
                    list
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data for provided ID...!");
        }

    }

    @Override
    public PaginatedResponseSupplierAddressDTO getAllPagedAddresses(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<SupplierAddress> AddressPage = addressRepo.findAll(pageable);

            List<SupplierAddressResponseDTO> dtos = AddressPage.getContent()
                    .stream()
                    .map( r -> new SupplierAddressResponseDTO(
                            r.getAddressId(),
                            supplierMapper.toSupplierDto(r.getSupplier()),
                            r.getAddressType(),
                            r.getStreetLine1(),
                            r.getStreetLine2(),
                            r.getCity(),
                            r.getState(),
                            r.getPostalCode(),
                            r.getCountryCode(),
                            r.getIsPrimary()
                            )
                    )
                    .collect(Collectors.toList());

            return new PaginatedResponseSupplierAddressDTO(
                    AddressPage.getNumberOfElements(),
                    dtos,
                    AddressPage.getTotalPages(),
                    AddressPage.getTotalElements(),
                    AddressPage.getNumber(),
                    AddressPage.getSize(),
                    AddressPage.hasNext(),
                    AddressPage.hasPrevious()
            );
        } catch (Exception e) {
            throw new EntryNotFoundException("Can't find any data...!");
        }
    }

}
