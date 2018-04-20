package comexport.repositories;

import comexport.models.LancamentoContabil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "lancamentoContabil", path = "lancamentoContabil")
public interface LancamentoContabilRepository extends MongoRepository<LancamentoContabil, String> {
    LancamentoContabil save(LancamentoContabil saved);
    List<LancamentoContabil> findByContaContabil(Integer contaContabil);
    void deleteAll();
}
