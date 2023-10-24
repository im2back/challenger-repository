package github.io.im2back.challenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import github.io.im2back.challenger.model.user.Usuario;
import github.io.im2back.challenger.model.util.NotificationDTO;
import github.io.im2back.challenger.model.util.ValidacaoException;

public class NotificationService {

	@Autowired
	private RestTemplate restTemplate;
	
	public void enviarNotificacao(Usuario usuario, String message) {
		String email = usuario.getEmail();
		
		NotificationDTO notificationRequest = new NotificationDTO(email,message);
		 ResponseEntity<String> notificationResponse = restTemplate.postForEntity("url", notificationRequest, String.class);
		 
	if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
		throw new ValidacaoException("serviço de notificação indisponivel");
	}
	}
}
