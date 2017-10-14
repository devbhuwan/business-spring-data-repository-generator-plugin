package java.io.github.devbhuwan.business.model.impl;


import io.github.devbhuwan.business.model.DemoDomain;
import io.github.devbhuwan.business.model.contracts.DemoRepository;
import io.github.devbhuwan.business.model.repositories.DemoDomainDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoRepositoryImpl implements DemoRepository {

    @Autowired
    private DemoDomainDataRepository demoDomainDataRepository;

    @Override
    public DemoDomain findById(Long id) {
        return demoDomainDataRepository.findOne(id);
    }
}
