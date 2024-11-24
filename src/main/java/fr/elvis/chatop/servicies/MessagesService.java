package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.MessageDTO;
import fr.elvis.chatop.entities.MessagesEntity;

import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.repository.MessagesRepository;
import fr.elvis.chatop.repository.RentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MessageDTO> getAllMessages() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }

    public MessageDTO getMessageById(int id) {
        MessagesEntity messageEntity = messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        return modelMapper.map(messageEntity, MessageDTO.class);
    }

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        MessagesEntity messageEntity = modelMapper.map(messageDTO, MessagesEntity.class);
        messageEntity = messageRepository.save(messageEntity);
        return modelMapper.map(messageEntity, MessageDTO.class);
    }

    public MessageDTO updateMessage(int id, MessageDTO messageDTO) {
        if (messageRepository.existsById(id)) {
            MessagesEntity messageEntity = modelMapper.map(messageDTO, MessagesEntity.class);

            Rental rental = rentalRepository.findById(messageDTO.getRental().getId())
                    .orElseThrow(() -> new RuntimeException("Rental not found"));
            messageEntity.setRental(rental);

            messageEntity.setId(id);
            messageEntity = messageRepository.save(messageEntity);
            return modelMapper.map(messageEntity, MessageDTO.class);
        } else {
            throw new RuntimeException("Message not found");
        }
    }



    public void deleteMessage(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
        } else {
            throw new RuntimeException("Message not found");
        }
    }
}
