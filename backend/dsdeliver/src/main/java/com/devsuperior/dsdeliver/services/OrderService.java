package com.devsuperior.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsdeliver.dto.OrderDTO;
import com.devsuperior.dsdeliver.dto.ProductDTO;
import com.devsuperior.dsdeliver.entities.Order;
import com.devsuperior.dsdeliver.entities.OrderStatus;
import com.devsuperior.dsdeliver.entities.Product;
import com.devsuperior.dsdeliver.repositories.OrderRepository;
import com.devsuperior.dsdeliver.repositories.ProductRepository;


@Service
public class OrderService {
	
	@Autowired
	private OrderRepository respository;
	
	@Autowired
	private ProductRepository productRespository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){

		List<Order> list = respository.findOrderWithProducts();
		
		// Transforming the list of Product on an list of DTO
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
		
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto){

		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(),
				Instant.now(), OrderStatus.PENDING);
	for(ProductDTO p : dto.getProducts()) {
			
			Product product = productRespository.getOne(p.getId());
			order.getProducts().add(product);
			
		}
		
		order = respository.save(order);
		return new OrderDTO(order);
		
	}
	
	@Transactional
	public OrderDTO setDelivered(Long id){
		
		Order order = respository.getOne(id);
		order.setStatus(OrderStatus.DELIVERED);
		return new OrderDTO(order);
		
	}

	
}
