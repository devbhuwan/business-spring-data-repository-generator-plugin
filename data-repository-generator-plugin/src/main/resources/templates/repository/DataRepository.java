package ${classPackage}.repositories;

import ${idClassPackage}.${idClassName};
import ${classPackage}.${className};
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ${className}DataRepository extends PagingAndSortingRepository<${className}, ${idClassName}>, QueryByExampleExecutor<${className}> {
}
