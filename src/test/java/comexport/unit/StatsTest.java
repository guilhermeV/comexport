package comexport.unit;

import comexport.models.LancamentoContabil;
import comexport.models.Stats;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class StatsTest {

    public List<LancamentoContabil> listDummyThreeElements() {
        ArrayList list = new ArrayList<>();
        list.addAll(listDummyTwoElements());
        LancamentoContabil lc3 = new LancamentoContabil(2, "20170130", new BigDecimal("20.00"));
        list.add(lc3);
        return list;
    }

    public List<LancamentoContabil> listDummyTwoElements() {
        LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("25000.17"));
        LancamentoContabil lc2 = new LancamentoContabil(1, "20170130", new BigDecimal("150.74"));
        ArrayList list = new ArrayList<>();
        list.add(lc);
        list.add(lc2);
        return list;
    }

    @Test
    public void shouldCalculateCorrectly() throws ParseException {
        Stats stats = new Stats(listDummyTwoElements(), Optional.empty());
        assertEquals(Long.valueOf(2), stats.qtde);
        assertEquals(new BigDecimal("25150.91"), stats.soma);
        assertEquals(new BigDecimal("25000.17"), stats.max);
        assertEquals(new BigDecimal("150.74"), stats.min);
        assertEquals(new BigDecimal("12575.46"), stats.media);
    }

    @Test
    public void shouldCalculateCorrectlySecondExample() throws ParseException {
        Stats stats = new Stats(listDummyThreeElements(), Optional.empty());
        assertEquals(Long.valueOf(3), stats.qtde);
        assertEquals(new BigDecimal("25170.91"), stats.soma);
        assertEquals(new BigDecimal("25000.17"), stats.max);
        assertEquals(new BigDecimal("20.00"), stats.min);
        assertEquals(new BigDecimal("8390.30"), stats.media);
    }

}
