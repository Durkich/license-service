package com.example.license.service;

import com.example.license.config.RabbitMQConfig;
import com.example.license.event.LicenseEvent;
import com.example.license.model.LicenseEventEntity;
import com.example.license.repository.LicenseEventRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private LicenseEventRepository eventRepository;

    @Autowired
    private RabbitMQConfig config;

    @Transactional
    public void publish(LicenseEvent event) {
        LicenseEventEntity entity = new LicenseEventEntity();
        entity.setEventType(event.getEventType());
        entity.setLicenseId(event.getLicenseId());
        entity.setSoftwareName(event.getSoftwareName());
        entity.setSoftwareVersion(event.getSoftwareVersion());
        entity.setIsLicensed(event.getIsLicensed());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setSent(false);
        eventRepository.save(entity);

        sendToRabbit(entity, event);
    }

    private void sendToRabbit(LicenseEventEntity entity, LicenseEvent event) {
        try {
            rabbitTemplate.convertAndSend(config.EXCHANGE, config.ROUTING_KEY, event);
            entity.setSent(true);
            eventRepository.save(entity);
            System.out.println("Event sent: " + event.getEventType() + " for " + event.getSoftwareName());
        } catch (Exception e) {
            System.err.println("Failed to send event: " + e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void retryUnsentEvents() {
        List<LicenseEventEntity> unsentEvents = eventRepository.findBySentFalse();
        for (LicenseEventEntity entity : unsentEvents) {
            LicenseEvent event = new LicenseEvent(
                    entity.getEventType(),
                    entity.getLicenseId(),
                    entity.getSoftwareName(),
                    entity.getSoftwareVersion(),
                    entity.getIsLicensed()
            );
            sendToRabbit(entity, event);
        }
    }
}