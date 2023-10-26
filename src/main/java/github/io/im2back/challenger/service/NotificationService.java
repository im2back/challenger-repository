package github.io.im2back.challenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import github.io.im2back.challenger.infra.util.NotificationDTO;
import github.io.im2back.challenger.model.user.Usuario;

@Service
public class NotificationService {

	@Autowired
	private RestTemplate restTemplate;
	// 200 - https://run.mocky.io/v3/354b74d7-d52a-4902-82e8-90a03dd3ac4e
	//204 - https://run.mocky.io/v3/72bcd6b9-4933-45c2-a468-802cdd3b8302
	public String enviarNotificacao(Usuario usuario, String message) {
		String email = usuario.getEmail();
		
		NotificationDTO notificationRequest = new NotificationDTO(email,message);
		 ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/354b74d7-d52a-4902-82e8-90a03dd3ac4e", notificationRequest, String.class);
		 
	if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
		String msg = "serviço de notificação indisponivel";
		return msg;
	}
	return "menssagem enviada!";
	}
}
