package ua.iamiluxa.timetrackingspringproject.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import ua.iamiluxa.timetrackingspringproject.entity.Request;
import ua.iamiluxa.timetrackingspringproject.repository.RequestRepo;

import javax.persistence.NoResultException;

@Log4j2
public class RequestService {
    private final RequestRepo requestRepo;

    @Autowired
    public RequestService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    public Request findRequestById(long id) {
        return requestRepo.findById(id).orElseThrow(() ->
                new NoResultException("id not finded: " + id));
    }

}
