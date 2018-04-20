package comexport.services;

import comexport.models.LancamentoContabil;
import comexport.repositories.LancamentoContabilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoContabilServiceImpl implements LancamentoContabilService {

    private final LancamentoContabilRepository repository;

    @Autowired
    LancamentoContabilServiceImpl(LancamentoContabilRepository repository){
        this.repository = repository;
    }

    @Override
    public LancamentoContabil create(LancamentoContabil lancamentoContabil){
        return repository.save(lancamentoContabil.build(lancamentoContabil));
    }

    public List<LancamentoContabil> list(){
        return repository.findAll();
    }

    @Override
    public Optional<LancamentoContabil> findById(String id){
        return repository.findById(id);
    }

    @Override
    public List<LancamentoContabil> findByContaContabil(Integer contaContabil){
        return repository.findByContaContabil(contaContabil);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
