package io.github.devbhuwan.business.model;

import io.github.devbhuwan.data.definition.BusinessDomain;
import io.github.devbhuwan.data.definition.CoreDomain;

@BusinessDomain
@lombok.Getter
@lombok.Setter
public class DemoDomain extends CoreDomain<Long> {

    private String name;

}
