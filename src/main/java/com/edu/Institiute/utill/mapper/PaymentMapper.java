package com.edu.Institiute.utill.mapper;

import com.edu.Institiute.dto.PaymentDto;
import com.edu.Institiute.entity.Payment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")

public interface PaymentMapper {

    Payment dtoToPaymentEntity(PaymentDto paymentDto);
}
