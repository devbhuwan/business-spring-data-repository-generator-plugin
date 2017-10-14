package io.github.devbhuwan.business.model.contracts;

import io.github.devbhuwan.business.model.DemoDomain;

public interface DemoRepository {

    DemoDomain findById(Long id);
}
