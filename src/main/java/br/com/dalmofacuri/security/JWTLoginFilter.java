package br.com.dalmofacuri.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dalmofacuri.model.Cliente;

//Estabelece o nosso gerenciador de Token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	//Configurando o gerenciador de autenticação
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		 
		//Obriga a autenticar a URL
		super(new AntPathRequestMatcher(url));
		
		//Gerenciador de autenticação
		setAuthenticationManager(authenticationManager);
	}
    //Retorna o usuário ao processar a autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		   //Está pegando o Token para validar
		   Cliente user  = new ObjectMapper()
				   .readValue(request.getInputStream(), Cliente.class);
		   //Retorna o usuário login, senha e acesso
		   return getAuthenticationManager()
				   .authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		 
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
	}

}
