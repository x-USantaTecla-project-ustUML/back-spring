package com.usantatecla.ustumlserver.domain.persistence;

import com.usantatecla.ustumlserver.domain.model.useCaseDiagram.UseCase;
import org.springframework.stereotype.Repository;

@Repository
public interface UseCasePersistence {
    UseCase read(String id);
}
