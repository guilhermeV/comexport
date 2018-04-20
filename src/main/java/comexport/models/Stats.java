package comexport.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Stats {

    public final BigDecimal soma;
    public final BigDecimal min;
    public final BigDecimal max;
    public final BigDecimal media;
    public final Long qtde;

    public Stats(List<LancamentoContabil> lancamentosContabeis, Optional<Integer> contaContabil){
        this.soma =  getStream(lancamentosContabeis, contaContabil).map(lc -> lc.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);

        Optional<BigDecimal> max = getStream(lancamentosContabeis, contaContabil).map(lc -> lc.getValor()).max(BigDecimal::compareTo);
        this.max = max.isPresent() ? max.get() : BigDecimal.ZERO;

        Optional<BigDecimal> min = getStream(lancamentosContabeis, contaContabil).map(lc -> lc.getValor()).min(BigDecimal::compareTo);
        this.min = min.isPresent() ? min.get() : BigDecimal.ZERO;

        this.qtde = getStream(lancamentosContabeis, contaContabil).count();
        this.media = soma.divide(BigDecimal.valueOf(qtde), RoundingMode.HALF_UP);
    }

    public static Stream<LancamentoContabil> getStream(List<LancamentoContabil> lancamentosContabeis, Optional<Integer> contaContabil){
        return contaContabil.isPresent() ? lancamentosContabeis.stream().filter(lc -> lc.getContaContabil().equals(contaContabil.get())) : lancamentosContabeis.stream();
    }

}



