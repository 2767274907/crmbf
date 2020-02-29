package com.t248.lmf.crm.service;

import org.springframework.mail.MailException;

import javax.mail.MessagingException;

public interface MailService {


    /**
     * 发送普通文本邮箱
     * @param to
     * @param subject
     * @param content
     * @throws MailException
     */
    public void sendSimpleMail(String to, String subject, String content) throws MailException;

    /**
     * 发送带图片的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException;

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException;

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException;

}
