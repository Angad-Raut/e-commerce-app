package com.projectx.ecommerce.setting.service;

import com.projectx.ecommerce.common.exceptions.AlreadyExistException;
import com.projectx.ecommerce.common.exceptions.InvalidDataException;
import com.projectx.ecommerce.common.exceptions.ResourceNotFoundException;
import com.projectx.ecommerce.common.payloads.EntityIdDto;
import com.projectx.ecommerce.common.payloads.EntityTypeAndValueDto;
import com.projectx.ecommerce.common.utils.MessageUtils;
import com.projectx.ecommerce.setting.entity.DiscountDetails;
import com.projectx.ecommerce.setting.payloads.DiscountDto;
import com.projectx.ecommerce.setting.payloads.ViewDiscountDto;
import com.projectx.ecommerce.setting.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Boolean insertOrUpdate(DiscountDto dto) throws InvalidDataException,
            AlreadyExistException, ResourceNotFoundException {
        DiscountDetails discountDetails = null;
        if (dto.getId()==null) {
            isDiscountExist(dto);
            discountDetails = DiscountDetails.builder()
                    .discountType(dto.getDiscountType())
                    .discount(dto.getDiscount())
                    .status(true)
                    .insertedTime(new Date())
                    .updatedTime(new Date())
                    .build();
        } else {
            discountDetails = discountRepository.getById(dto.getId());
            if (discountDetails!=null) {
                if (!dto.getDiscountType().equals(discountDetails.getDiscountType())) {
                    isDiscountExist(dto);
                    discountDetails.setDiscountType(dto.getDiscountType());
                    discountDetails.setDiscount(dto.getDiscount());
                }
                discountDetails.setUpdatedTime(new Date());
            } else {
                throw new ResourceNotFoundException(MessageUtils.DISCOUNT_NOT_FOUND);
            }
        }
        try {
            DiscountDetails details = discountRepository.save(discountDetails);
            return details!=null?true:false;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public DiscountDto getById(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            DiscountDetails details = discountRepository.getById(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.DISCOUNT_NOT_FOUND);
            }
            return DiscountDto.builder()
                    .id(details.getId())
                    .discount(details.getDiscount())
                    .discountType(details.getDiscountType())
                    .build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Boolean updateStatus(EntityIdDto dto) throws ResourceNotFoundException {
        try {
            DiscountDetails details = discountRepository.getById(dto.getEntityId());
            if (details==null) {
                throw new ResourceNotFoundException(MessageUtils.DISCOUNT_NOT_FOUND);
            }
            Integer count = discountRepository.updateStatus(dto.getEntityId(),false);
            return count==1?true:false;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<EntityTypeAndValueDto> getDropDown() {
        List<Object[]> fetchList = discountRepository.getDiscountsByStatus(true);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return EntityTypeAndValueDto.builder()
                            .entityType(data[0]!=null?Integer.parseInt(data[0].toString()):null)
                            .entityValue(data[1]!=null?Double.parseDouble(data[1].toString()):null)
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }

    @Override
    public List<ViewDiscountDto> getAllDiscountList() {
        List<DiscountDetails> fetchList = discountRepository.findAll();
        AtomicInteger index = new AtomicInteger(0);
        return !fetchList.isEmpty()?fetchList.stream()
                .map(data -> {
                    return ViewDiscountDto.builder()
                            .srNo(index.incrementAndGet())
                            .id(data.getId())
                            .discountType(data.getDiscountType())
                            .discount(data.getDiscount())
                            .status(data.getStatus()?"Enabled":"Disabled")
                            .build();
                }).collect(Collectors.toList()):new ArrayList<>();
    }
    private void isDiscountExist(DiscountDto dto) {
        Boolean result = discountRepository.existsByDiscountTypeAndDiscount(dto.getDiscountType(),dto.getDiscount());
        if (result) {
            throw new AlreadyExistException(MessageUtils.DISCOUNT_EXISTS);
        }
    }
}
