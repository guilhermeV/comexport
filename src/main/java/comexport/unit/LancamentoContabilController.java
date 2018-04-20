package comexport.unit;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import comexport.models.LancamentoContabil;
import comexport.models.LancamentoContabilIdView;
import comexport.models.MessageView;
import comexport.models.Stats;
import comexport.services.LancamentoContabilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LancamentoContabilController {

    private final LancamentoContabilService service;

    private static final String INVALID_DATA = "Invalid data, acceptable format: contaContabil:Numeric, data:String(yyyyMMdd), valor:Numeric(20,2)";
    private static final String INVALID_DATA_GET = "Invalid data, acceptable format: contaContabil:Numeric";

    @Autowired
    LancamentoContabilController(LancamentoContabilService service) {
        this.service = service;
    }

    @RequestMapping(value = "/lancamentos-contabeis", method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid @RequestBody LancamentoContabil lancamentoContabil, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(new MessageView(errors.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new LancamentoContabilIdView(service.create(lancamentoContabil)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lancamentos-contabeis/list", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @RequestMapping(value = "/lancamentos-contabeis/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return service.findById(id).isPresent() ? new ResponseEntity(service.findById(id), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/lancamentos-contabeis/", params = "contaContabil", method = RequestMethod.GET)
    public ResponseEntity<?> findByContaContabil(@RequestParam(value = "contaContabil") Integer contaContabil) {
        return new ResponseEntity(service.findByContaContabil(contaContabil), HttpStatus.OK);
    }

    @RequestMapping(value = "/lancamentos-contabeis/del/", method = RequestMethod.GET)
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return new ResponseEntity("!", HttpStatus.OK);
    }

    @RequestMapping(value = "/lancamentos-contabeis/_stats/", method = RequestMethod.GET)
    public ResponseEntity<?> stats(@RequestParam(value = "contaContabil") Optional<Integer> contaContabil) {
        List<LancamentoContabil> listaLancamentosContabeis = service.list();
        Stats stats = new Stats(listaLancamentosContabeis, contaContabil);
        return new ResponseEntity(stats, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageView handleException(InvalidFormatException e) {
        return new MessageView(INVALID_DATA);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageView handleException(NumberFormatException e) {
        return new MessageView(INVALID_DATA_GET);
    }


}
