package comexport.services;

import comexport.models.LancamentoContabil;

import java.util.List;
import java.util.Optional;

public interface LancamentoContabilService {

    LancamentoContabil create(LancamentoContabil lancamentoContabil);
    List<LancamentoContabil> list();
    Optional<LancamentoContabil> findById(String id);
    List<LancamentoContabil> findByContaContabil(Integer id);
    void deleteAll();

}
