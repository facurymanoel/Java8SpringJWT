package br.com.dalmofacuri.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dalmofacuri.dto.ClienteDTO;
import br.com.dalmofacuri.exception.ClienteNotFoundException;
import br.com.dalmofacuri.mapper.ClienteMapper;
import br.com.dalmofacuri.model.Cliente;
import br.com.dalmofacuri.repository.ClienteRepository;
 

@Service
public class ClienteService implements UserDetailsService{
	
	private static final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;
	
	private ClienteRepository clienteRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		 this.clienteRepository = clienteRepository;
	}
	
	public List<ClienteDTO> findAll(){
		return clienteRepository.findAll()
				                .stream()
				                //.map((n) -> clienteMapper.toDTO(n)) 
				                .map(clienteMapper::toDTO)//referencia
				                .collect(Collectors.toList());
	}
	
	public ClienteDTO findById(Long id) {
		return clienteRepository.findById(id)
			   //.map((n) -> clienteMapper.toDTO(n))
				 .map(clienteMapper::toDTO)
			   .orElseThrow(() -> new ClienteNotFoundException(id));
	}
	
	 
	 @Transactional
	public ClienteDTO create(ClienteDTO clienteDTO) throws Exception  {
		
		Cliente clienteToCreate = clienteMapper.toModel(clienteDTO);
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(clienteToCreate.getSenha());
		clienteToCreate.setSenha(senhaCriptografada);
		Cliente createCliente = clienteRepository.save(clienteToCreate);
		
		insereAcessoPadrao(createCliente.getId());
		
		return clienteMapper.toDTO(createCliente) ;
	}
	 
	@Transactional
	public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
	    verifyExists(id);
		Cliente cli = new Cliente();
		cli.setId(id);
		clienteDTO.setId(cli.getId());
		Cliente clienteToCreate = clienteMapper.toModel(clienteDTO);
		Cliente clienteTemporario = clienteRepository.findById(clienteDTO.getId()).get();
		
		 
		if(!clienteTemporario.getSenha().equals(clienteToCreate.getSenha())) {//senhas diferentes
			String senhaCriptografada = new BCryptPasswordEncoder().encode(clienteToCreate.getSenha());
	        clienteToCreate.setSenha(senhaCriptografada);
		}
		  
		Cliente createCliente = clienteRepository.save(clienteToCreate);
		
		return clienteMapper.toDTO(createCliente) ;
	}
	
	 @Transactional
	public void delete(Long id) {
		verifyExists(id);
		clienteRepository.deleteById(id);
	}
	
	private void verifyExists(Long id) {
		 clienteRepository.findById(id)
		                  .orElseThrow(() -> new ClienteNotFoundException(id));
	 
	  }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   
		   //Consultar no banco o Cliente
		  Cliente cliente = clienteRepository.findUserByLogin(username);
		  
		  if(cliente == null) {
			  throw new UsernameNotFoundException("Usuário não foi encontrado!");
		  }
		return new User(cliente.getLogin(), 
				cliente.getPassword(), 
				cliente.getAuthorities());
	}
	
	
	private void insereAcessoPadrao(Long id) {
		 //Descobre qual a Constraint de restrição
		 String constraint = clienteRepository.consultaConstraintRole();
		 
		 if(constraint != null) {
			 //Remove a Constraint
		     jdbcTemplate.execute(" alter table cli_usuarios_role DROP CONSTRAINT " + constraint);
		 }
		 //Insere os acessos padrão
		 clienteRepository.insereAcessoRolePadrao(id);
		
	}
	
	 

}
