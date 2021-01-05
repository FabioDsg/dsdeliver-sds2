package com.devsuperior.dsdeliver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;


@Service
public class OrderService {
	
	@Autowired
	private OrderRepository respository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){

		List<Order> list = respository.findOrderWithProducts();
		
		// Transforming the list of Product on an list of DTO
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
		
	}
	
}
