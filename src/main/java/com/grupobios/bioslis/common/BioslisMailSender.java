/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grupobios.bioslis.common;

import java.io.ByteArrayOutputStream;

import com.grupobios.bioslis.dto.NotificaResultadosDTO;

/**
 *
 * @author eric.nicholls
 */
public interface BioslisMailSender {

	void sendSimpleMessage(String to, String subject, String text);

	void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel);

	void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

	void enviarMail(String destinatario, String asunto, String cuerpo, ByteArrayOutputStream output)
			throws BiosLISException;

	void enviarMail(String destinatario, String asunto, String cuerpo) throws BiosLISException;

	void notificarResultadoCritico(NotificaResultadosDTO notificaResultadosDTO) throws BiosLISException;

	void notificarExamenAutorizado(NotificaResultadosDTO notificaResultadosDTO) throws BiosLISException;

}
